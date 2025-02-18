package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class ClientScopeDoesNotExistsException extends STSException {

    public ClientScopeDoesNotExistsException(String scope) {
        super(STSError.CLIENT_SCOPE_DOES_NOT_EXISTS, String.format("Client scope %s is not defined", scope));
    }

}
