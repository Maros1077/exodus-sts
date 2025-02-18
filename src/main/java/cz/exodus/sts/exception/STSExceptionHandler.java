package cz.exodus.sts.exception;

import cz.exodus.jsend.network.exception.ErrorDetails;
import cz.exodus.jsend.network.exception.GlobalExceptionHandler;
import cz.exodus.jsend.network.rest.JSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;

@ControllerAdvice
@Slf4j
public class STSExceptionHandler extends GlobalExceptionHandler {

    private final String COMPONENT_NAME = "exodus-sts";

    @ExceptionHandler(STSException.class)
    public ResponseEntity<JSendResponse> handleExodusException(STSException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getStsError().getCode(), ex.errorInstanceId(), COMPONENT_NAME);
        log.error("Exception: {}", ex.stsError);
        return ResponseEntity.status(ex.getStsError().getHttpStatus()).body(new JSendResponse(ex.getStsError().getErrorType().getJsendStatus(), errorDetails));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<JSendResponse> handleBindException(BindException e) {
        STSException ex = new BadRequestException();
        log.error("Exception: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getStsError().getCode(), ex.errorInstanceId(), COMPONENT_NAME);
        return ResponseEntity.status(ex.getStsError().getHttpStatus()).body(new JSendResponse(ex.getStsError().getErrorType().getJsendStatus(), errorDetails));
    }
}

