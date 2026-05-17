package model;

public class LoginTestData {
    private final String testName;
    private final String username;
    private final String password;
    private final String expectedMessage;
    private final boolean validLogin;

    public LoginTestData(String testName, String username, String password, String expectedMessage, boolean validLogin) {
        this.testName = testName;
        this.username = username;
        this.password = password;
        this.expectedMessage = expectedMessage;
        this.validLogin = validLogin;
    }

    public String getTestName() {
        return testName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedMessage() {
        return expectedMessage;
    }

    public boolean isValidLogin() {
        return validLogin;
    }

    @Override
    public String toString() {
        return testName;
    }
}
