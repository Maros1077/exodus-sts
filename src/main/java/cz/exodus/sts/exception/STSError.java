package cz.exodus.sts.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import cz.exodus.jsend.network.exception.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Optional;

import static cz.exodus.jsend.network.exception.ErrorType.ERROR;
import static cz.exodus.jsend.network.exception.ErrorType.FAIL;

@Getter
@AllArgsConstructor
public enum STSError {

    REQUEST_INVALID(1000, FAIL, HttpStatus.BAD_REQUEST),
    CLIENT_DOES_NOT_EXISTS(1001, FAIL, HttpStatus.BAD_REQUEST),
    TOKEN_DOES_NOT_EXISTS(1002, FAIL, HttpStatus.UNAUTHORIZED),
    CLIENT_SCOPE_DOES_NOT_EXISTS(1003, FAIL, HttpStatus.BAD_REQUEST),
    CLIENT_TOKEN_TYPE_DOES_NOT_EXISTS(1004, FAIL, HttpStatus.BAD_REQUEST),
    INCORRECT_TOKEN(1005, FAIL, HttpStatus.BAD_REQUEST),
    CRYPTO_ERROR(2000, ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final ErrorType errorType;
    private final HttpStatus httpStatus;

    @JsonValue
    public int getJsonValue() {
        return code;
    }

    @JsonCreator
    public static STSError fromJsonValue(Integer jsonValue) {
        return Optional.ofNullable(jsonValue)
                .flatMap(STSError::fromCode)
                .orElse(null);
    }

    public static Optional<STSError> fromCode(int code) {
        for (STSError e : values()) {
            if (e.getCode() == code) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

}

