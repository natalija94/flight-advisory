package airport.server.service;

import java.io.InputStream;
import java.util.List;

/**
 * @author natalija
 */
public interface DictionaryReader {
    /**
     * Parse dict as list params passed in array.
     * @param inputStream concrete dictionary
     * @return rows of file
     */
    List<String[]> parseDictionary(InputStream inputStream);
}
