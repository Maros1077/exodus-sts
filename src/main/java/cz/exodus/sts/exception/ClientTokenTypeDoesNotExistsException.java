package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class ClientTokenTypeDoesNotExistsException extends STSException {

    public ClientTokenTypeDoesNotExistsException(String type) {
        super(STSError.CLIENT_TOKEN_TYPE_DOES_NOT_EXISTS, String.format("Client token type %s is not defined", type));
    }

}
