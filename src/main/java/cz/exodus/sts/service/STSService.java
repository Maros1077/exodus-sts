package cz.exodus.sts.service;

import com.fasterxml.jackson.databind.JsonNode;
import cz.exodus.sts.db.entity.ClientConfigEntity;
import cz.exodus.sts.db.entity.ClientEntity;
import cz.exodus.sts.db.entity.TokenEntity;
import cz.exodus.sts.db.repository.ClientConfigRepository;
import cz.exodus.sts.db.repository.ClientRepository;
import cz.exodus.sts.db.repository.TokenRepository;
import cz.exodus.sts.exception.ClientDoesNotExistsException;
import cz.exodus.sts.exception.ClientScopeDoesNotExistsException;
import cz.exodus.sts.exception.CryptoException;
import cz.exodus.sts.exception.STSException;
import cz.exodus.sts.rest.IssueResponse;
import cz.exodus.sts.utils.CryptoUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class STSService {

    private final String issuerName = "https://github.com/Maros1077/exodus-project";

    private final ClientRepository clientRepository;
    private final ClientConfigRepository clientConfigRepository;
    private final TokenRepository tokenRepository;

    public IssueResponse issueToken(String clientId, String scope, String grantType, String sub, JsonNode metadata) throws STSException {
        log.debug("META: " + metadata);
        ClientEntity clientEntity = getClient(clientId);
        ClientConfigEntity clientConfigEntity = getClientConfig(clientEntity, scope);

        PrivateKey privateKey;
        try {
            privateKey = CryptoUtils.loadPrivateKey(clientConfigEntity.getPrivateKey());
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
        tokenRepository.save(new TokenEntity(null, clientEntity, scope, expirationDate, type, jti, metadata.toString()));
        return new IssueResponse(token, type, clientConfigEntity.getExpiration());
    }

    private ClientEntity getClient(String clientId) throws STSException {
        Optional<ClientEntity> entity = Optional.ofNullable(clientRepository.findByName(clientId));
        if (entity.isEmpty()) {
            throw new ClientDoesNotExistsException(clientId);
        }
        return entity.get();
    }

    private ClientConfigEntity getClientConfig(ClientEntity client, String scope) throws STSException {
        Optional<ClientConfigEntity> entity = Optional.ofNullable(clientConfigRepository.findByClientAndScopes(client, scope));
        if (entity.isEmpty()) {
            throw new ClientScopeDoesNotExistsException(scope);
        }
        return entity.get();
    }
}
