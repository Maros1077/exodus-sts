package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class ClientDoesNotExistsException extends STSException {

    public ClientDoesNotExistsException(String client) {
        super(STSError.CLIENT_DOES_NOT_EXISTS, String.format("Client %s is not defined", client));
    }

}
