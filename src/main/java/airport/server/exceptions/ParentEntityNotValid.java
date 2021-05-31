package airport.server.exceptions;

/**
 * @author natalija
 */
public class ParentEntityNotValid extends Exception {
    public ParentEntityNotValid(String message) {
        super(message);
    }

    public ParentEntityNotValid() {
        this("Not possible to save entity. Parent object value is not valid.");
    }
}
