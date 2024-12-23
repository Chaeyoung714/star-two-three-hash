package miniproject.star_two_three.exception;

import org.springframework.http.HttpStatus;

public enum Exceptions {
    EXPIRED_TOKEN("expired token", HttpStatus.UNAUTHORIZED),
    NOT_ACCESS_TOKEN("not access token", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_TYPE("invalid token type", HttpStatus.UNAUTHORIZED),
    NO_TOKEN("no token", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("invalid token", HttpStatus.UNAUTHORIZED),

    NOT_REFRESH_TOKEN("not refresh token", HttpStatus.UNAUTHORIZED),
    NO_READ_AUTHORITY("not have read authority", HttpStatus.UNAUTHORIZED),
    MESSAGE_NOT_FOUND("message of such id not found", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    Exceptions(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
