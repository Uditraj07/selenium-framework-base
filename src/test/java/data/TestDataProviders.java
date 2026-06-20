//package data;
//
//import constants.TestDataConstant;
//import org.testng.annotations.DataProvider;
//import utils.CsvDataReader;
//
//import java.util.List;
//
//public final class TestDataProviders {
//    private TestDataProviders() {
//    }
//
//    @DataProvider(name = "loginData", parallel = true)
//    public static Object[][] loginData() {
//        CsvDataReader csvDataReader = new CsvDataReader(TestDataConstant.LOGIN_DATA_CSV);
//        List<LoginTestData> records = csvDataReader.readLoginData();
//        Object[][] data = new Object[records.size()][1];
//
//        for (int index = 0; index < records.size(); index++) {
//            data[index][0] = records.get(index);
//        }
//
//        return data;
//    }
//}
