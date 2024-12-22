package miniproject.star_two_three.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;

    public CustomException(Exceptions exceptions) {
        this.httpStatus = exceptions.getHttpStatus();
        this.message = exceptions.getMessage();
    }
}
