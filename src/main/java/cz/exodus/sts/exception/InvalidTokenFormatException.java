package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class InvalidTokenFormatException extends STSException {

    public InvalidTokenFormatException() {
        super(STSError.INCORRECT_TOKEN, "Incorrect token format.");
    }

}
