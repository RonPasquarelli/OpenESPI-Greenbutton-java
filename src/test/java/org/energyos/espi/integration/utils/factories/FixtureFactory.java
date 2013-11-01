package org.energyos.espi.integration.utils.factories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

public class FixtureFactory {
    private FixtureFactory() {}

    public static InputStream newUsagePointInputStream(UUID uuid) throws IOException {
        return new ByteArrayInputStream(newUsagePointXML(uuid, "Front Electric Meter").getBytes());
    }

    public static String newUsagePointXML(UUID uuid, String name) throws IOException {
        InputStream in = FixtureFactory.class.getClassLoader().getResourceAsStream("fixtures/test_usage_data.xml");

        String xml = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
        xml = xml.replaceFirst("48C2A019-5598-4E16-B0F9-49E4FF27F5FB", uuid.toString());
        xml = xml.replaceFirst("Front Electric Meter", name);

        return xml;
    }
}
