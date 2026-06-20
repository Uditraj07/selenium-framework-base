package utils;

import model.FieldTestCaseData;
import model.UiTestCaseData;
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

public class CsvDataReader {
    private final String filePath;

    public CsvDataReader(String filePath) {
        this.filePath = filePath;
    }

//    public List<LoginTestData> readLoginData() {
//        try (InputStream inputStream = getInputStream();
//             CSVParser parser = createParser(inputStream)) {
//            List<LoginTestData> testData = new ArrayList<>();
//            for (CSVRecord record : parser) {
//                testData.add(new LoginTestData(
//                        record.get("testName"),
//                        record.get("username"),
//                        record.get("password"),
//                        record.get("expectedMessage"),
//                        Boolean.parseBoolean(record.get("validLogin"))
//                ));
//            }
//            return testData;
//        } catch (IOException exception) {
//            throw buildReadException(exception);
//        }
//    }

    public List<UiTestCaseData> readUiTestCaseData(String scenarioName) {
        try (InputStream inputStream = getInputStream();
             CSVParser parser = createParser(inputStream)) {
            List<UiTestCaseData> testData = new ArrayList<>();
            String currentScenario = "";
            String currentFieldName = "";
            for (CSVRecord record : parser) {
                currentScenario = resolveRepeatedValue(record, "Scenario", currentScenario);
                currentFieldName = resolveRepeatedValue(record, "FiledName", currentFieldName);

                if (scenarioName.equalsIgnoreCase(currentScenario)) {
                    UiTestCaseData uiTestCaseData = new UiTestCaseData();
                    uiTestCaseData.setScenario(currentScenario);
                    uiTestCaseData.setFieldName(currentFieldName);
                    uiTestCaseData.setFieldType(getValue(record, "FiledType"));
                    uiTestCaseData.setKeyword(getValue(record, "Keyword"));
                    uiTestCaseData.setFieldValue(getValue(record, "FieldVlaue"));
                    uiTestCaseData.setExpectedResult(getValue(record, "Expected Result"));
                    testData.add(uiTestCaseData);
                }
            }
            return testData;
        } catch (IOException exception) {
            throw buildReadException(exception);
        }
    }

    public List<FieldTestCaseData> readFieldTestCaseData(String scenarioName) {
        try (InputStream inputStream = getInputStream();
             CSVParser parser = createParser(inputStream)) {
            List<FieldTestCaseData> testData = new ArrayList<>();
            String currentScenario = "";
            String currentFieldName = "";
            for (CSVRecord record : parser) {
                currentScenario = resolveRepeatedValue(record, "Scenario", currentScenario);
                currentFieldName = resolveRepeatedValue(record, "FiledName", currentFieldName);

                if (scenarioName.equalsIgnoreCase(currentScenario)) {
                    FieldTestCaseData fieldTestCaseData = new FieldTestCaseData();
                    fieldTestCaseData.setScenario(currentScenario);
                    fieldTestCaseData.setFieldName(currentFieldName);
                    fieldTestCaseData.setFieldType(getValue(record, "FiledType"));
                    fieldTestCaseData.setKeyword(getValue(record, "Keyword"));
                    fieldTestCaseData.setFieldValue(getValue(record, "FieldVlaue"));
                    fieldTestCaseData.setExpectedResult(getValue(record, "Expected Result"));
                    testData.add(fieldTestCaseData);
                }
            }
            return testData;
        } catch (IOException exception) {
            throw buildReadException(exception);
        }
    }

    private InputStream getInputStream() {
        InputStream inputStream = CsvDataReader.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalStateException("CSV test data file not found: " + filePath);
        }
        return inputStream;
    }

    private CSVParser createParser(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);
    }

    private String getValue(CSVRecord record, String columnName) {
        return record.isMapped(columnName) ? record.get(columnName).trim() : "";
    }

    private String resolveRepeatedValue(CSVRecord record, String columnName, String currentValue) {
        String value = getValue(record, columnName);
        return value.isEmpty() ? currentValue : value;
    }

    private IllegalStateException buildReadException(IOException exception) {
        return new IllegalStateException("Unable to read CSV test data from: " + filePath, exception);
    }
}
