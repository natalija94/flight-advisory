package airport.server.enums;

/**
 * @author natalija
 */
public enum ResponseStatus {
    ERROR("ERROR"),
    SUCCESS("SUCCESS");

    private String value;

    ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
