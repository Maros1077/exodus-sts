package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class TokenDoesNotExistsException extends STSException {

    public TokenDoesNotExistsException() {
        super(STSError.TOKEN_DOES_NOT_EXISTS, "Token does not exist, or is revoked.");
    }

}
