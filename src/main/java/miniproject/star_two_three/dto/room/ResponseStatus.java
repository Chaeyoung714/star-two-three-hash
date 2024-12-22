package miniproject.star_two_three.dto.room;

public enum ResponseStatus {
    SUCCESS("success"),
    FAIL("fail"),
    ;

    private final String message;

    ResponseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
