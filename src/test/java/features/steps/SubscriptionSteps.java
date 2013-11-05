package features.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static features.steps.StepUtils.*;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class SubscriptionSteps {
    private WebDriver driver = WebDriverSingleton.getInstance();

    @Given("^an authorized Third Party$")
    public void an_authorized_Third_Party() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, StepUtils.USERNAME, StepUtils.PASSWORD);
        clickLinkByText("Data Custodians");
        clickByName("Data_custodian");
        clickByName("next");
        submitLoginForm(StepUtils.USERNAME, StepUtils.PASSWORD);
        clickByName("scope");
        clickByName("next");
        clickByName("authorize");
        clickLinkByText("Logout");
    }

    @When("^I log in as Alan Turing$")
    public void I_log_in_as_Alan_Turing() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, StepUtils.USERNAME, StepUtils.PASSWORD);
    }

    @When("^I request a Subscription using the REST API$")
    public void I_request_a_Subscription_using_the_REST_API() throws Throwable {
        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/espi/1_1/resource/Subscription/1");
    }

    @Then("^I should receive XML for that Subscription$")
    public void I_should_receive_XML_for_that_Subscription() throws Throwable {
        assertXpathExists("/:feed", driver.getPageSource());
    }

    @And("^I navigate to the Authorizations page$")
    public void I_navigate_to_the_Authorizations_page() throws Throwable {
        clickLinkByText("Authorizations");
    }

    @Then("^I should see a list of Authorizations$")
    public void I_should_see_a_list_of_Authorizations() throws Throwable {
        assertContains("Authorizations");
        assertThat(driver.findElement(By.cssSelector("#authorizations tr td.access_token")).getText(), not(isEmptyString()));

    }
}
