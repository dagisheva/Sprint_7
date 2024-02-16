package helpers;

public enum ResponseCodes {
    OK(200),
    CREATED(201),
    DENIED(400),
    NOT_FOUND(404),
    ALREADY_CREATED(409);

    private final int value;

    ResponseCodes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
