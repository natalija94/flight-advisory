package airport.server.enums;

/**
 * @author natalija
 */
public enum DictionaryType {
    AIRPORTS("AIRPORTS"),
    ROUTES("ROUTES");

    String value;

    DictionaryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
