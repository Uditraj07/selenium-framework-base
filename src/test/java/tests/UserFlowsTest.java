package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddRemoveElementsPage;
import pages.CheckboxesPage;
import pages.DropdownPage;
import pages.LoginPage;
import pages.SecureAreaPage;

public class UserFlowsTest extends BaseTest {

    @Test
    public void shouldLoginAndLogoutSuccessfully() {
        open("/login");
        LoginPage loginPage = new LoginPage(getDriver());
        SecureAreaPage secureAreaPage = new SecureAreaPage(getDriver());

        loginPage.login("tomsmith", "SuperSecretPassword!");
        Assert.assertTrue(
                secureAreaPage.getFlashMessage().contains("You logged into a secure area!"),
                "Successful login message should be displayed"
        );

        secureAreaPage.logout();
        Assert.assertEquals(loginPage.getPageHeader(), "Login Page", "User should return to the login page after logout");
        Assert.assertTrue(
                loginPage.getFlashMessage().contains("You logged out of the secure area!"),
                "Logout message should be displayed"
        );
    }

    @Test
    public void shouldAddAndRemoveElements() {
        open("/add_remove_elements/");
        AddRemoveElementsPage page = new AddRemoveElementsPage(getDriver());

        page.addElement();
        page.addElement();
        Assert.assertEquals(page.getDeleteButtonCount(), 2, "Two delete buttons should appear after adding twice");

        page.removeElement();
        Assert.assertEquals(page.getDeleteButtonCount(), 1, "One delete button should remain after removing one");
    }

    @Test
    public void shouldUpdateCheckboxSelections() {
        open("/checkboxes");
        CheckboxesPage page = new CheckboxesPage(getDriver());

        page.setCheckboxState(0, true);
        page.setCheckboxState(1, false);

        Assert.assertTrue(page.isCheckboxSelected(0), "First checkbox should be selected");
        Assert.assertFalse(page.isCheckboxSelected(1), "Second checkbox should be unselected");
    }

    @Test
    public void shouldSelectValueFromDropdown() {
        open("/dropdown");
        DropdownPage page = new DropdownPage(getDriver());

        page.selectByVisibleText("Option 2");
        Assert.assertEquals(page.getSelectedOption(), "Option 2", "Dropdown should keep the selected option");
    }
}
