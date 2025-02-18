package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends STSException {

    public BadRequestException() {
        super(STSError.REQUEST_INVALID, "Bad request");
    }

    public BadRequestException(String message) {
        super(STSError.REQUEST_INVALID, "Bad request: " + message);
    }
}
