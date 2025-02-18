package cz.exodus.sts.exception;

import cz.exodus.jsend.network.exception.BaseException;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class STSException extends BaseException {

    final STSError stsError;

    public STSException(STSError stsError, String message) {
        this(stsError, message, null);
    }

    public STSException(STSError stsError, String message, Throwable cause) {
        super(message, cause, UUID.randomUUID().toString());
        this.stsError = stsError;
    }

}
