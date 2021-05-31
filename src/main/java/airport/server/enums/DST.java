package airport.server.enums;

/**
 * @author natalija
 */
public enum DST {
    E("Europe"),
    A("US/Canada"),
    S("South America"),
    O("Australia"),
    Z("New Zealand"),
    N("None"),
    U("Unknown");

    private String value;

    DST(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
