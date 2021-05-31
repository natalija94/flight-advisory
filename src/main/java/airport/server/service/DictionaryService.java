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
    InputStream getDictionaryStream(DictionaryType dictionaryType);

    default InputStream getDictionaryStreamFromProject(String path) throws IOException {
        InputStream stream = null;
        ClassPathResource pathResource = new ClassPathResource(path);
        stream = pathResource.getInputStream();
        return stream;
    }

    default InputStream getDictionaryStreamFromFileSystem(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
