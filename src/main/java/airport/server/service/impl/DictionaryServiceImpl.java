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

import java.io.*;
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

    public List<String[]> parseDictionary(DictionaryType dictionaryType) {
        InputStream inputStream = getDictionaryStream(dictionaryType);
        return parseDictionary(inputStream);
    }

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

    public InputStream findDictionary(String path) throws Exception {
        if (dictionariesFromFileSystem) {
            return getDictionaryStreamFromFileSystem(path);
        } else {
            return getDictionaryStreamFromProject(path);
        }
    }
}
