package features.steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.energyos.espi.common.test.BaseStepUtils;

import static org.junit.Assert.assertTrue;

public class OAuthSteps extends BaseStepUtils {

    @Before
    public void before() {
        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/j_spring_security_logout");
        driver.get(StepUtils.THIRD_PARTY_BASE_URL + "/j_spring_security_logout");
    }

    @Given("^a logged in Retail Customer$")
    public void a_logged_in_Retail_Customer() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, StepUtils.USERNAME, StepUtils.PASSWORD);
    }

    @When("^I navigate to Data Custodian list$")
    public void I_navigate_to_Data_Custodian_list() throws Throwable {
        clickLink("Data Custodians");
    }

    @Then("^I should see a list of Data Custodians$")
    public void I_should_see_a_list_of_Data_Custodians() throws Throwable {
        assertContains("ConEdison");
    }

    @Then("^I should see the Data Custodian login screen$")
    public void I_should_see_the_Data_Custodian_login_screen() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("/DataCustodian"));
        assertContains("Login");
    }
}
