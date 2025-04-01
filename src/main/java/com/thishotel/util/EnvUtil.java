package com.thishotel.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtil {

    public static void setEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String env = dotenv.get("SWITCH_ENV", "local"); // Default "local"
        System.out.println("Environment: " + env);

        String urlKey = env.equals("aws") ? "DB_URL" : "DB_URL_LOCAL";
        String usernameKey = env.equals("aws") ? "DB_USERNAME" : "DB_USERNAME_LOCAL";
        String passwordKey = env.equals("aws") ? "DB_PASSWORD" : "DB_PASSWORD_LOCAL";

        System.setProperty("spring.datasource.url", dotenv.get(urlKey));
        System.setProperty("spring.datasource.username", dotenv.get(usernameKey));
        System.setProperty("spring.datasource.password", dotenv.get(passwordKey));
    }

    public static void setEnv2() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String env = dotenv.get("SWITCH_ENV", System.getenv("SWITCH_ENV") != null ? System.getenv("SWITCH_ENV") : "local");
        System.out.println("Environment: " + env);

        String urlKey = env.equals("aws") ? "DB_URL" : "DB_URL_LOCAL";
        String usernameKey = env.equals("aws") ? "DB_USERNAME" : "DB_USERNAME_LOCAL";
        String passwordKey = env.equals("aws") ? "DB_PASSWORD" : "DB_PASSWORD_LOCAL";

        String dbUrl = dotenv.get(urlKey, System.getenv(urlKey));
        String dbUsername = dotenv.get(usernameKey, System.getenv(usernameKey));
        String dbPassword = dotenv.get(passwordKey, System.getenv(passwordKey));

        System.setProperty("spring.datasource.url", dbUrl);
        System.setProperty("spring.datasource.username", dbUsername);
        System.setProperty("spring.datasource.password", dbPassword);
    }
}

