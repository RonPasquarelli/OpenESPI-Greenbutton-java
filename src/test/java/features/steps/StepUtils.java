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

import org.energyos.espi.integration.utils.factories.FixtureFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertTrue;

public class StepUtils {

    public final static String BASE_URL = "http://localhost:8080/";
    public final static String THIRD_PARTY_CONTEXT = "ThirdParty";
    public final static String DATA_CUSTODIAN_CONTEXT = "DataCustodian";
    public final static String DATA_CUSTODIAN_BASE_URL = BASE_URL + DATA_CUSTODIAN_CONTEXT;
    public final static String THIRD_PARTY_BASE_URL = BASE_URL + THIRD_PARTY_CONTEXT;
    public static final String USERNAME = "alan";
    public static final String PASSWORD = "koala";

    public static final String CUSTODIAN_USERNAME = "grace";
    public static final String CUSTODIAN_PASSWORD = "koala";

    private static WebDriver driver = WebDriverSingleton.getInstance();;

    public static void navigateTo(String context, String path) {
        driver.get(StepUtils.BASE_URL + context + path);
    }

    public static void clickLinkByText(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();
    }

    public static void login(String context, String username, String password) {
        navigateTo(context, "/");
        if (driver.findElements(By.id("logout")).size() > 0) {
            logout();
        }
        WebElement loginLink = driver.findElement(By.id("login"));
        loginLink.click();
        submitLoginForm(username, password);
    }

    public static void logout() {
        driver.findElement(By.id("logout")).click();
    }

    public static void selectRadioByLabel(String labelText) {
        driver.findElement(By.xpath("//label[contains(.,'" + labelText + "')]/input")).click();
    }

    protected static void clickByName(String name) {
        WebElement submit = driver.findElement(By.name(name));
        submit.click();
    }

    protected static void clickByClass(String className) {
        WebElement radio = driver.findElement(By.className(className));
        radio.click();
    }

    protected static void fillInByName(String name, String text) {
        WebElement usernameInput = driver.findElement(By.name(name));
        usernameInput.clear();
        usernameInput.sendKeys(text);
    }

    protected static void assertContains(String text) {
        assertTrue("Page source did not contain '" + text + "'", driver.getPageSource().contains(text));
    }

    public static void associate(String uuid, String description) {
        clickLinkByPartialText("Add Usage Point");

        WebElement uuidElement = driver.findElement(By.name("UUID"));
        uuidElement.clear();
        uuidElement.sendKeys(uuid);

        WebElement descriptionElement = driver.findElement(By.id("description"));
        descriptionElement.sendKeys(description);

        WebElement create = driver.findElement(By.name("create"));
        create.click();
    }

    public static void clickLinkByPartialText(String linkText) {
        WebElement link = driver.findElement(By.partialLinkText(linkText));
        link.click();
    }

    public static String newUsername() {
        return "User" + System.currentTimeMillis();
    }

    public static void registerUser(String username, String firstName, String lastName, String password) {
        StepUtils.login(StepUtils.DATA_CUSTODIAN_CONTEXT, StepUtils.CUSTODIAN_USERNAME, StepUtils.CUSTODIAN_PASSWORD);

        clickLinkByText("Customer List");
        clickLinkByPartialText("Add new customer");

        assertTrue(driver.getPageSource().contains("New Retail Customer"));

        WebElement form = driver.findElement(By.name("new_customer"));

        WebElement usernameField = form.findElement(By.name("username"));
        usernameField.sendKeys(username);

        WebElement firstNameField = form.findElement(By.name("firstName"));
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = form.findElement(By.name("lastName"));
        lastNameField.sendKeys(lastName);

        WebElement passwordField = form.findElement(By.name("password"));
        passwordField.sendKeys(password);

        WebElement create = form.findElement(By.name("create"));
        create.click();

        assertTrue(driver.getPageSource().contains("Retail Customers"));
    }

    public static String newLastName() {
        return "Doe" + System.currentTimeMillis();
    }

    public static String newFirstName() {
        return "John" + System.currentTimeMillis();
    }

    public static void importUsagePoint() throws IOException {
        navigateTo(StepUtils.DATA_CUSTODIAN_CONTEXT, "/custodian/upload");
        uploadUsagePoints("Front Electric Meter");
    }

    public static void addUsagePoint(String username, String mrid) throws IOException {
        navigateTo(StepUtils.DATA_CUSTODIAN_CONTEXT, "/custodian/retailcustomers");
        clickLinkByText(username);
        associate(mrid, "Front Electric Meter");
    }

    public static void uploadUsagePoints(String usagePointName) throws IOException {
        String xml = FixtureFactory.newUsagePointXML(CucumberSession.getUUID(), usagePointName);
        File tmpFile = File.createTempFile("usage_point", ".xml");
        Files.copy(new ByteArrayInputStream(xml.getBytes()), Paths.get(tmpFile.getAbsolutePath()), REPLACE_EXISTING);

        clickLinkByText("Upload");
        WebElement file = driver.findElement(By.name("file"));
        file.sendKeys(tmpFile.getAbsolutePath());
        WebElement upload = driver.findElement(By.name("upload"));
        upload.click();
    }

    public static void submitLoginForm(String username, String password) {
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }

    static void notifyThirdParty() {
        login(DATA_CUSTODIAN_CONTEXT,"grace", "koala");
        clickLinkByText("Notify Third Party");
    }
}
