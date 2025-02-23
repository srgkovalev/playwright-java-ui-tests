package com.tests;

import com.config.Configuration;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;
import com.pages.BasePage;
import com.pages.CartPage;
import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.utils.BasePageFactory;
import com.utils.BrowserManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.config.ConfigurationManager.config;

// Данная аннотации позволяет создавать экземпляр класса лишь 1 раз при запуске множества тестов
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private static final Configuration secrets = ConfigFactory.create(Configuration.class);

    private String userName = secrets.userName();
    private String userPass = secrets.userPass();
    private String userNameSession = secrets.userNameSession();

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUserNameSession() {
        return userNameSession;
    }

    @BeforeAll
    public void createContext() {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    // Ленивая инициализация страниц (создается только если вызвали)
    protected LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = createInstance(LoginPage.class);
        }
        return loginPage;
    }

    protected ProductsPage getProductsPage() {
        if (productsPage == null) {
            productsPage = createInstance(ProductsPage.class);
        }
        return productsPage;
    }

    public void setUpWithCookies() {
        Cookie sessionCookie = new Cookie(getUserNameSession(), getUserName());
        sessionCookie.domain = config().domain();
        sessionCookie.path = "/";

        browserContext.addCookies(List.of(sessionCookie));
    }

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @AfterAll
    public void close() {
        browser.close();
        playwright.close();
    }
}