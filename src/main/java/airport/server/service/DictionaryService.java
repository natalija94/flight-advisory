package airport.server.service;

import airport.server.enums.DictionaryType;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author natalija
 */
public interface DictionaryService {
    /**
     * Get input stream for concrete dictionary type.
     * @param dictionaryType
     * @return
     */
    InputStream getDictionaryStream(DictionaryType dictionaryType);

    /**
     * Return input stream for complete project.
     * @param path as file path
     * @return input stream
     * @throws FileNotFoundException
     */
    default InputStream getDictionaryStreamFromProject(String path) throws IOException {
        InputStream stream = null;
        ClassPathResource pathResource = new ClassPathResource(path);
        stream = pathResource.getInputStream();
        return stream;
    }

    /**
     * Return input stream for exact dictionary.
     * @param path as file path
     * @return input stream
     * @throws FileNotFoundException
     */
    default InputStream getDictionaryStreamFromFileSystem(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
