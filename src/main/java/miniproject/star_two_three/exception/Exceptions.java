package miniproject.star_two_three.exception;

import org.springframework.http.HttpStatus;

public enum Exceptions {
    EXPIRED_TOKEN("Expired token.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_TYPE("Invalid Token Type.", HttpStatus.UNAUTHORIZED),
    NO_TOKEN("Cannot Find Token.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid Token Format.", HttpStatus.UNAUTHORIZED),
    NOT_ACCESS_TOKEN("Not Access Token.", HttpStatus.UNAUTHORIZED),
    NOT_REFRESH_TOKEN("Not Refresh Token.", HttpStatus.UNAUTHORIZED),
    NO_READ_AUTHORITY("Doesn't Have Read Authority.", HttpStatus.UNAUTHORIZED),
    BLACKLISTED_TOKEN("Blacklisted Token.", HttpStatus.UNAUTHORIZED),

    MESSAGE_NOT_FOUND("Message of Such Id Not Found.", HttpStatus.BAD_REQUEST),
    ROOM_NOT_FOUND("Room of Such Id Not Found.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Invalid Password Format.", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
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
