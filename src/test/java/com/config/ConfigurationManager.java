package com.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {
    private static final Configuration CONFIG = ConfigCache.getOrCreate(Configuration.class);

    public static Configuration config() {
        return CONFIG;
    }

    public static String getBrowser() {
        return config().browser();
    }

    public static boolean isHeadless() {
        return config().headless();
    }
}