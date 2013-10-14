/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package features.steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.custommonkey.xmlunit.XMLUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.UUID;

import static features.steps.StepUtils.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class RetailCustomersSteps {

    private WebDriver driver = WebDriverSingleton.getInstance();

    @Before
    public void setup() {
        XMLUnit.getControlDocumentBuilderFactory().setNamespaceAware(false);
        driver.get(StepUtils.THIRD_PARTY_BASE_URL + "/j_spring_security_logout");
        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/logout.do");

        StepUtils.login(StepUtils.DATA_CUSTODIAN_CONTEXT, "grace", "koala");
        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/custodian/removealltokens");

        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/logout.do");
    }

    @Given("^a Retail Customer with Usage Points$")
    public void a_Retail_Customer_with_Usage_Points() throws Throwable {
        CucumberSession.setUsername(StepUtils.newUsername());

        StepUtils.registerUser(CucumberSession.getUsername(), StepUtils.newFirstName(), StepUtils.newLastName(), StepUtils.PASSWORD);
        CucumberSession.setUUID(UUID.fromString("48C2A019-5598-4E16-B0F9-49E4FF27F5FB"));
        StepUtils.addUsagePoint(CucumberSession.getUsername(), CucumberSession.getUUID().toString());
        StepUtils.importUsagePoint();
        StepUtils.logout();
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, "alan", StepUtils.PASSWORD);
    }

    @Given("^I have a Retail Customer account$")
    public void I_have_a_Retail_Customer_account() throws Throwable {
        // Seed Data
    }

    @Given("^There is a Third Party$")
    public void There_is_a_Third_Party() throws Throwable {
       // Defined in seed data
    }

    @When("^I log in as Alan Turing into Data Custodian$")
    public void I_log_in_as_Alan_Turing_Data_Custodian() throws Throwable {
        StepUtils.login(StepUtils.DATA_CUSTODIAN_CONTEXT, "alan", "koala");
    }

    @When("^I log in as Alan Turing into Third Party$")
    public void I_log_in_as_Alan_Turing_Third_Party() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, "alan", "koala");
    }

    @When("^I click on the Select Third Party link$")
    public void I_click_on_the_Select_Third_Party_link() throws Throwable {
        clickLinkByText("Third Parties");
    }

    @When("^I select the Third Party$")
    public void I_select_the_Third_Party() throws Throwable {
        selectRadioByLabel("Pivotal Energy");
        driver.findElement(By.name("next")).click();
    }

    @When("^I look at my Usage Points page$")
    public void I_look_at_my_usage_page() throws Throwable {
        WebElement usagePointLink = driver.findElement(By.linkText("Usage Points"));
        usagePointLink.click();

        StepUtils.submitLoginForm(CucumberSession.getUsername(), StepUtils.PASSWORD);
    }

    @When("^I select a Data Custodian from the list$")
    public void I_select_a_Data_Custodian_from_the_list() throws Throwable {
        clickLinkByText("Data Custodians");
        clickByClass("data-custodian");
        clickByName("next");
    }

    @When("^I log into Data Custodian$")
    public void I_log_into_Data_Custodian() throws Throwable {
        fillInByName("j_username", StepUtils.USERNAME);
        fillInByName("j_password", StepUtils.PASSWORD);
        clickByName("submit");
    }

    @When("^I select Scopes$")
    public void I_select_Scope() throws Throwable {
        clickByClass("scope");
        clickByName("next");
    }

    @When("^I log into Third Party$")
    public void I_log_into_Third_Party() throws Throwable {
        fillInByName("j_username", StepUtils.USERNAME);
        fillInByName("j_password", StepUtils.PASSWORD);
        clickByName("submit");
    }

    @When("^I log in as a Data Custodian$")
    public void I_login_as_a_Data_Custodian() throws Throwable {
        StepUtils.login(StepUtils.DATA_CUSTODIAN_CONTEXT, StepUtils.CUSTODIAN_USERNAME, StepUtils.CUSTODIAN_PASSWORD);
    }

    @When("^I select Usage Point$")
    public void I_select_Usage_Point() throws Throwable {
        WebElement usagePointLink = driver.findElement(By.linkText("Front Electric Meter"));
        usagePointLink.click();
    }

    @When("^I select Meter Reading$")
    public void I_select_Meter_Reading() throws Throwable {
        WebElement usagePointLink = driver.findElement(By.linkText("Fifteen Minute Electricity Consumption"));
        usagePointLink.click();
    }

    @And("^I enter my username and password$")
    public void I_enter_my_username_and_password() throws Throwable {
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys("alan");
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(StepUtils.PASSWORD);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }

    @When("^I authorize Third Party$")
    public void I_authorize_Third_Party() throws Throwable {
        clickByName("authorize");
    }

    @And("^I deny Third Party$")
    public void I_deny_Third_Party() throws Throwable {
        clickByName("deny");
    }

    @And("^I select a Third Party from the list$")
    public void I_select_a_Third_Party_from_the_list() throws Throwable {
        clickLinkByText("Third Parties");
        clickByClass("third-party");
        clickByName("next");
    }

    @Then("^I should be taken to the Third Party login page$")
    public void I_should_be_taken_to_the_Third_Party_login_page() throws Throwable {
        assertNotNull("Login field missing", driver.findElement(By.name("j_username")));
        assertNotNull("Password field missing", driver.findElement(By.name("j_password")));
    }

    @Then("^I should see a Usage Point$")
    public void I_should_see_a_Usage_Point() throws Throwable {
        assertContains("Front Electric Meter");
    }

    @Then("^I should be redirected to the home page$")
    public void I_should_be_redirected_to_the_home_page() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("RetailCustomer"));
        assertTrue(driver.getCurrentUrl().endsWith("/home"));
    }

    @Then("^I should see Scope selection screen$")
    public void I_should_see_Scope_selection_screen() throws Throwable {
        assertContains("Select Scope");
        assertContains("FB_4_5_15_IntervalDuration_3600_BlockDuration_monthly_HistoryLength_13");
    }

    @Then("^I should see authorization screen$")
    public void I_should_see_authorization_screen() throws Throwable {
        assertContains("Please Confirm");
    }

    @Then("^I should see all my authorizations$")
    public void I_should_see_all_my_authorizations() throws Throwable {
        assertContains("Authorizations");
        assertContains("ConEdison");
    }

    @Then("^I should see Meter Reading Details$")
    public void I_should_see_Meter_Reading_Details() throws Throwable {
        assertTrue("MeterReading title missing", driver.getPageSource().contains("Fifteen Minute Electricity Consumption"));
        assertTrue("ReadingType title missing", driver.getPageSource().contains("Type of Meter Reading Data"));
        assertTrue("Interval blocks missing", driver.getPageSource().contains("86400"));
        assertTrue(driver.getPageSource().contains("974"));
        assertTrue(driver.getPageSource().contains("900"));
        assertTrue(driver.getPageSource().contains("965"));

        WebElement element = driver.findElement(By.cssSelector(".reading-qualities"));

        assertThat(element.getText(), containsString("8"));
    }

    @Then("^I should see Usage Point details$")
    public void I_should_see_Usage_Point_details() throws Throwable {
        assertTrue(driver.getPageSource().contains("Usage Summary"));
        assertTrue(driver.getPageSource().contains("Quality Summary"));
        assertContains("Local time zone offset from UTCTime");
        assertContains("-18000");
    }

    @Then("^I should be logged in$")
    public void I_should_be_logged_in() throws Throwable {
        assertTrue(driver.getPageSource().contains("logout"));
        assertFalse(driver.getPageSource().contains("login"));
    }

    @Then("^I should see the login form$")
    public void I_should_see_the_login_form() throws Throwable {
        assertTrue(driver.getPageSource().contains("Login"));
    }
}
