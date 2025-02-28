package com.utils;

import com.config.ConfigurationManager;
import com.microsoft.playwright.*;
import java.util.Objects;

public class BrowserManager {
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static void initialize() {
        try {
            if (browser == null) {
                Playwright playwright = Playwright.create();
                String browserType = ConfigurationManager.getBrowser();

                if (Objects.equals(browserType, "chromium")) {
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(ConfigurationManager.isHeadless()));
                } else if (Objects.equals(browserType, "firefox")) {
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(ConfigurationManager.isHeadless()));
                } else if (Objects.equals(browserType, "webkit")) {
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(ConfigurationManager.isHeadless()));
                } else {
                    throw new IllegalArgumentException("Unsupported browser: " + browserType);
                }

                context = browser.newContext();
                page = context.newPage();
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize BrowserManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Page getPage() {
        if (page == null) {
            throw new IllegalStateException("BrowserManager is not initialized. Call initialize() first.");
        }
        return page;
    }

    public static void close() {
        try {
            if (context != null) {
                context.close();
                context = null;
            }
            if (browser != null) {
                browser.close();
                browser = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to close BrowserManager: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
