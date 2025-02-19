package cz.exodus.sts.service;

import com.fasterxml.jackson.databind.JsonNode;
import cz.exodus.sts.db.entity.ClientConfigEntity;
import cz.exodus.sts.db.entity.ClientEntity;
import cz.exodus.sts.db.entity.TokenEntity;
import cz.exodus.sts.db.repository.ClientConfigRepository;
import cz.exodus.sts.db.repository.ClientRepository;
import cz.exodus.sts.db.repository.TokenRepository;
import cz.exodus.sts.exception.*;
import cz.exodus.sts.rest.IssueResponse;
import cz.exodus.sts.rest.ValidateResponse;
import cz.exodus.sts.utils.CryptoUtils;
import cz.exodus.sts.utils.DateUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class STSService {

    private final String issuerName = "https://github.com/Maros1077/exodus-project";

    private final ClientRepository clientRepository;
    private final ClientConfigRepository clientConfigRepository;
    private final TokenRepository tokenRepository;

    public IssueResponse issueToken(String clientId, String scope, String grantType, String sub, JsonNode metadata) throws STSException {
        ClientEntity clientEntity = getClient(clientId);
        ClientConfigEntity clientConfigEntity = getClientConfig(clientEntity, scope);

        PrivateKey privateKey;
        try {
            privateKey = CryptoUtils.loadPrivateKey(clientEntity.getPrivateKey());
        } catch (Exception e) {
            log.debug(Arrays.toString(e.getStackTrace()));
            throw new CryptoException();
        }

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + clientConfigEntity.getExpiration() * 1000L);
        String jti = UUID.randomUUID().toString();
        String type = clientConfigEntity.getType();

        String token = Jwts.builder()
                .issuer(issuerName)
                .subject(sub)
                .claim("scopes", scope)
                .claim("type", type)
                .claim("jti", jti)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(privateKey, SignatureAlgorithm.ES256)
                .compact();
        tokenRepository.save(new TokenEntity(null, clientEntity, sub, scope, expirationDate, type, jti, metadata.toString()));
        return new IssueResponse(token, type, clientConfigEntity.getExpiration());
    }

    public ValidateResponse verifyToken(String token) throws STSException {
        TokenEntity tokenEntity = getToken(extractJti(token));
        try {
            Jwt<JwsHeader, Claims> jwt = Jwts.parser()
                    .verifyWith(CryptoUtils.loadPublicKey(tokenEntity.getClient().getPublicKey()))
                    .build()
                    .parseSignedClaims(token);
            log.debug(jwt.toString());
        } catch (Exception e) {
            throw new CryptoException();
        }
        if (DateUtils.isInPast(tokenEntity.getExpiration())) {
            tokenRepository.delete(tokenEntity);
            throw new TokenDoesNotExistsException();
        }
        return new ValidateResponse(tokenEntity.getClient().getName(), tokenEntity.getType(), tokenEntity.getSub(), tokenEntity.getScopes(), tokenEntity.getExpiration(), DateUtils.getSecondsBetweenDates(tokenEntity.getExpiration()), tokenEntity.getMetadata());

    }

    public Void revokeToken(String token) throws STSException {
        TokenEntity tokenEntity = getToken(extractJti(token));
        tokenRepository.delete(tokenEntity);
        return null;
    }

    private ClientEntity getClient(String clientId) throws STSException {
        Optional<ClientEntity> entity = Optional.ofNullable(clientRepository.findByName(clientId));
        if (entity.isEmpty()) {
            throw new ClientDoesNotExistsException(clientId);
        }
        return entity.get();
    }

    private TokenEntity getToken(String jti) throws STSException {
        Optional<TokenEntity> entity = Optional.ofNullable(tokenRepository.findByJti(jti));
        if (entity.isEmpty()) {
            throw new TokenDoesNotExistsException();
        }
        return entity.get();
    }

    private String extractJti(String token) throws STSException {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new InvalidTokenFormatException();
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        JSONObject payload;
        try {
            payload = new JSONObject(payloadJson);
        } catch (JSONException e) {
            throw new InvalidTokenFormatException();
        }
        String jti = payload.optString("jti");
        if (jti == null) {
            throw new InvalidTokenFormatException();
        }
        log.debug("Token JTI: " + jti);
        return jti;
    }

    private ClientConfigEntity getClientConfig(ClientEntity client, String scope) throws STSException {
        Optional<ClientConfigEntity> entity = Optional.ofNullable(clientConfigRepository.findByClientAndScopes(client, scope));
        if (entity.isEmpty()) {
            throw new ClientScopeDoesNotExistsException(scope);
        }
        return entity.get();
    }
}