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

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static features.steps.StepUtils.*;

public class NotificationSteps {

    @Given("^a Third Party with an updated subscription$")
    public void a_Third_Party_with_an_updated_subscription() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, StepUtils.USERNAME, StepUtils.PASSWORD);
        clickLinkByText("Data Custodians");
        clickByName("Data_custodian");
        clickByName("next");
        submitLoginForm(StepUtils.USERNAME, StepUtils.PASSWORD);
        clickByName("scope");
        clickByName("next");
        clickByName("authorize");
        clickLinkByText("Logout");

        
        StepUtils.login(StepUtils.DATA_CUSTODIAN_CONTEXT, "grace", "koala");
        uploadUsagePoints("Sweet Usage Point");
        addUsagePoint("alan", CucumberSession.getUUID().toString());
    }

    @When("^I notify the Third Party$")
    public void I_notify_the_Third_Party() throws Throwable {
        notifyThirdParty();
    }

    @Then("^the Third Party should be notified of the update$")
    public void the_Third_Party_should_be_notified_of_the_update() throws Throwable {
        StepUtils.login(StepUtils.THIRD_PARTY_CONTEXT, "alan", "koala");
        navigateTo(StepUtils.THIRD_PARTY_CONTEXT, "/batchLists");
        assertContains("RetailCustomer/1/UsagePoint/6");
    }
}
