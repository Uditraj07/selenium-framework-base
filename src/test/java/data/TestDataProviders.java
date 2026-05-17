package data;

import model.LoginTestData;
import org.testng.annotations.DataProvider;
import utils.CsvDataReader;

import java.util.List;

public final class TestDataProviders {
    private TestDataProviders() {
    }

    @DataProvider(name = "loginData", parallel = true)
    public static Object[][] loginData() {
        List<LoginTestData> records = CsvDataReader.readLoginData("testdata/login-data.csv");
        Object[][] data = new Object[records.size()][1];

        for (int index = 0; index < records.size(); index++) {
            data[index][0] = records.get(index);
        }

        return data;
    }
}
