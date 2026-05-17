package utils;

import model.LoginTestData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class CsvDataReader {
    private CsvDataReader() {
    }

    public static List<LoginTestData> readLoginData(String resourcePath) {
        List<LoginTestData> testData = new ArrayList<>();

        try (InputStream inputStream = CsvDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("CSV test data file not found: " + resourcePath);
            }

            try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 CSVParser parser = CSVFormat.DEFAULT.builder()
                         .setHeader()
                         .setSkipHeaderRecord(true)
                         .build()
                         .parse(reader)) {

                for (CSVRecord record : parser) {
                    testData.add(new LoginTestData(
                            record.get("testName"),
                            record.get("username"),
                            record.get("password"),
                            record.get("expectedMessage"),
                            Boolean.parseBoolean(record.get("validLogin"))
                    ));
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read CSV test data from: " + resourcePath, exception);
        }

        return testData;
    }
}
