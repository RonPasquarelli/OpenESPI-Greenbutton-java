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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class StepUtils {

    public final static String BASE_URL = "http://localhost:8080/";
    public final static String THIRD_PARTY_CONTEXT = "ThirdParty";
    public final static String DATA_CUSTODIAN_CONTEXT = "DataCustodian";
    public final static String DATA_CUSTODIAN_BASE_URL = BASE_URL + DATA_CUSTODIAN_CONTEXT;
    public final static String THIRD_PARTY_BASE_URL = BASE_URL + THIRD_PARTY_CONTEXT;
    public static final String USERNAME = "alan";
    public static final String PASSWORD = "koala";


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
        WebElement loginLink = driver.findElement(By.id("login"));
        loginLink.click();
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
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
        assertTrue(driver.getPageSource().contains(text));
    }
}
