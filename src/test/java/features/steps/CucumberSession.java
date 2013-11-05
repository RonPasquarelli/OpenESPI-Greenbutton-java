package features.steps;

import java.util.UUID;

public class CucumberSession {
    private static String username;
    private static UUID uuid;
    private static int numberOfSubscriptions;

    private CucumberSession() {}

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CucumberSession.username = username;
    }

    public static UUID getUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    public static void setUUID(UUID UUID) {
        CucumberSession.uuid = UUID;
    }

    public static int getNumberOfSubscriptions() {
        return numberOfSubscriptions;
    }

    public static void setNumberOfSubscriptions(int numberOfSubscriptions) {
        CucumberSession.numberOfSubscriptions = numberOfSubscriptions;
    }
}
