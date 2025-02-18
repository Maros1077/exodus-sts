package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class RequestInvalidException extends STSException {

    public RequestInvalidException(String message) {
        this(message, null);
    }

    public RequestInvalidException(String message, Throwable cause) {
        super(STSError.REQUEST_INVALID, message, cause);
    }

}
