package helpers;

public enum ResponseFields {
    OK("ok"),
    MESSAGE("message"),
    ID("id"),
    TRACK("track");

    private final String value;

    ResponseFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
