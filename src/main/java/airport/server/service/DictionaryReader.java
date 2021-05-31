package airport.server.service;

import java.io.InputStream;
import java.util.List;

/**
 * @author natalija
 */
public interface DictionaryReader {
    List<String[]> parseDictionary(InputStream inputStream);
}
