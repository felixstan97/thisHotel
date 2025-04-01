package com.thishotel.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtil {

    public static void setEnv() {
    Dotenv dotenv = Dotenv.load();

    String env = dotenv.get("SWITCH_ENV", "local"); // Default "local"
    System.out.println("Environment: " + env);

    String urlKey = env.equals("aws") ? "DB_URL" : "DB_URL_LOCAL";
    String usernameKey = env.equals("aws") ? "DB_USERNAME" : "DB_USERNAME_LOCAL";
    String passwordKey = env.equals("aws") ? "DB_PASSWORD" : "DB_PASSWORD_LOCAL";

    System.setProperty("spring.datasource.url", dotenv.get(urlKey));
    System.setProperty("spring.datasource.username", dotenv.get(usernameKey));
    System.setProperty("spring.datasource.password", dotenv.get(passwordKey));
    }
}
