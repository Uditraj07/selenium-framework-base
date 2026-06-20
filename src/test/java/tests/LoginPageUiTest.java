package tests;

import base.BaseTest;
import constants.TestDataConstant;
import functionalflow.LoginPage_functional_Flow;
import model.UiTestCaseData;
import org.testng.annotations.Test;
import utils.CsvDataReader;

import java.util.List;

public class LoginPageUiTest extends BaseTest {
    private CsvDataReader csvDataReader;
    private LoginPage_functional_Flow loginPageFunctionalFlow;

    public LoginPageUiTest() {
        csvDataReader=new CsvDataReader(TestDataConstant.UI_TEST_CASES_CSV);

    }

    @Test
    public void verify_Login_Page_Ui_Test() throws InterruptedException {
        String scenarioName="Loged_In_Validation_Scenario";
        loginPageFunctionalFlow = new LoginPage_functional_Flow();
        Thread.sleep(5000);
        List<UiTestCaseData>testData=csvDataReader.readUiTestCaseData(scenarioName);
        loginPageFunctionalFlow.verify_LoginPage_Functional_Flow(testData);


    }
}
