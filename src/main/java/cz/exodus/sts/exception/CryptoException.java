package cz.exodus.sts.exception;

import lombok.Getter;

@Getter
public class CryptoException extends STSException {

    public CryptoException() {
        super(STSError.CRYPTO_ERROR, "Unexpected error.");
    }

}
