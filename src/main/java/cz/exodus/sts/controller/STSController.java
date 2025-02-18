package cz.exodus.sts.controller;

import cz.exodus.sts.exception.STSException;
import cz.exodus.sts.rest.*;
import cz.exodus.sts.service.STSService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.exodus.sts.controller.STSEndpoints.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(STS_INT_PATH)
public class STSController {

    private final STSService STSService;

    @PostMapping(ISSUE_PATH)
    public IssueResponse issueToken(@RequestBody IssueRequest request, HttpServletResponse response) throws STSException {
        log.debug("ISSUE TOKEN START");
        try {
            IssueResponse result = STSService.issueToken(request.getClientId(), request.getScope(), request.getGrantType(), request.getSub(), request.getMetadata());
            log.info("ISSUE TOKEN SUCCESS for: " + request.getSub());
            return result;
        } catch (STSException e) {
            log.info("ISSUE TOKEN FAILED for " + request.getSub());
            throw e;
        }
    }

/*    @PostMapping(VALIDATE_PATH)
    public RetrieveIdentityResponse retrieveIdentity(@RequestBody RetrieveIdentityRequest request, HttpServletResponse response) throws STSException {
        log.debug("RETRIEVE IDENTITY START");
        try {
            RetrieveIdentityResponse result = STSService.retrieveIdentity(request.getApplication(), request.getIdentificationTag(), request.isRetrieveTags(), request.isRetrieveAuthPoints());
            log.info("RETRIEVED IDENTITY for " + result.getIdid());
            return result;
        } catch (STSException e) {
            log.info("RETRIEVE IDENTITY FAILED for " + request.getIdentificationTag().getValue());
            throw e;
        }
    }

    @PostMapping(REVOKE_PATH)
    public UpdateIdentityResponse updateIdentity(@RequestBody UpdateIdentityRequest request, HttpServletResponse response) throws STSException {
        log.debug("UPDATE IDENTITY START");
        try {
            UpdateIdentityResponse result = STSService.updateIdentity(request.getApplication(), request.getIdentificationTag(), request.getTags(), request.getAuthPoints());
            log.info("UPDATED IDENTITY for " + request.getIdentificationTag().getValue());
            return result;
        } catch (STSException e) {
            log.info("UPDATE IDENTITY FAILED for " + request.getIdentificationTag().getValue());
            throw e;
        }
    }*/
}
