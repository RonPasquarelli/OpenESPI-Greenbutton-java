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

import org.energyos.espi.common.test.BaseStepUtils;
import org.energyos.espi.common.test.CucumberSession;
import org.energyos.espi.integration.utils.factories.FixtureFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class StepUtils extends BaseStepUtils {
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


    static void notifyThirdParty() {
        login(DATA_CUSTODIAN_CONTEXT, CUSTODIAN_USERNAME, CUSTODIAN_PASSWORD);
        clickLinkByText("Notify Third Party");
    }
}
