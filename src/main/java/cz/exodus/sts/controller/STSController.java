package cz.exodus.sts.controller;

import cz.exodus.sts.exception.STSException;
import cz.exodus.sts.rest.*;
import cz.exodus.sts.service.STSService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(VALIDATE_PATH)
    public ValidateResponse validateToken(@RequestParam String token, HttpServletResponse response) throws STSException {
        log.debug("VERIFY TOKEN START");
        try {
            ValidateResponse result = STSService.verifyToken(token);
            log.info("VERIFY TOKEN SUCCESS");
            return result;
        } catch (STSException e) {
            log.info("VERIFY TOKEN FAILED");
            throw e;
        }
    }

    @GetMapping(REVOKE_PATH)
    public Void revokeToken(@RequestParam String token, HttpServletResponse response) throws STSException {
        log.debug("REVOKE TOKEN START");
        try {
            Void result = STSService.revokeToken(token);
            log.info("REVOKE TOKEN SUCCESS");
            return result;
        } catch (STSException e) {
            log.info("REVOKE TOKEN FAILED");
            throw e;
        }
    }
}
