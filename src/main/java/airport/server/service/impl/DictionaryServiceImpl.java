package airport.server.service.impl;

import airport.server.enums.DictionaryType;
import airport.server.service.DictionaryReader;
import airport.server.service.DictionaryService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author natalija
 */
@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService, DictionaryReader {

    @Value("${dictionariesFromFileSystem:false}")
    private boolean dictionariesFromFileSystem;

    @Value("${airports.path:dictionaries/airports.txt}")
    private String airportsPath;

    @Value("${routes.path:dictionaries/routes.txt}")
    private String routesPath;

    @Override
    public InputStream getDictionaryStream(DictionaryType dictionaryType) {
        String route = resolveRoute(dictionaryType);
        try {
            return findDictionary(route);
        } catch (Exception e) {
            log.error("Not possible to load dictionary data.{}", route);
        }
        return null;
    }

    @Override
    public List<String[]> parseDictionary(InputStream inputStream) {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).withFieldAsNull(CSVReaderNullFieldIndicator.BOTH).build();
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(csvParser).build();
            return csvReader.readAll();
        } catch (Exception e) {
            log.error("Not possible to parse dictionary.{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses concrete dictionary.
     * @param dictionaryType as tye of dictionary
     * @return list of lines from file.
     */
    public List<String[]> parseDictionary(DictionaryType dictionaryType) {
        InputStream inputStream = getDictionaryStream(dictionaryType);
        return parseDictionary(inputStream);
    }

    /**
     * Resolves route from dictionary type
     * @param dictionaryType
     * @return route
     */
    public String resolveRoute(DictionaryType dictionaryType) {
        switch (dictionaryType) {
            case ROUTES:
                return routesPath;
            case AIRPORTS:
                return airportsPath;
            default:
                return null;
        }
    }

    /**
     * Takes dictionary from file system or from project.
     * @param path concrete path.
     * @return Input stream
     * @throws Exception
     */
    public InputStream findDictionary(String path) throws Exception {
        if (dictionariesFromFileSystem) {
            return getDictionaryStreamFromFileSystem(path);
        } else {
            return getDictionaryStreamFromProject(path);
        }
    }
}
