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
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.custommonkey.xmlunit.XMLUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static features.steps.StepUtils.clickLinkByText;
import static features.steps.StepUtils.selectRadioByLabel;
import static org.junit.Assert.assertNotNull;

public class RetailCustomerSteps {

    private WebDriver driver = WebDriverSingleton.getInstance();

    @Before
    public void setup() {
        XMLUnit.getControlDocumentBuilderFactory().setNamespaceAware(false);
    }

    @Given("^I have a Retail Customer account$")
    public void I_have_a_Retail_Customer_account() throws Throwable {
    }

    @When("^I log in as Alan Turing$")
    public void I_log_in_as_Alan_Turing() throws Throwable {
        StepUtils.login("DataCustodian", "alan", "koala");
    }

    @When("^I click on the Select Third Party link$")
    public void I_click_on_the_Select_Third_Party_link() throws Throwable {
        clickLinkByText("Select Authorized Third Party");
    }

    @Given("^There is a Third Party$")
    public void There_is_a_Third_Party() throws Throwable {
       // Defined in seed data
    }

    @When("^I select the Third Party$")
    public void I_select_the_Third_Party() throws Throwable {
        selectRadioByLabel("Pivotal Energy");
        driver.findElement(By.name("next")).click();
    }

    @Then("^I should be taken to the Third Party login page$")
    public void I_should_be_taken_to_the_Third_Party_login_page() throws Throwable {
        assertNotNull("Login field missing", driver.findElement(By.name("j_username")));
        assertNotNull("Password field missing", driver.findElement(By.name("j_password")));
    }
}
