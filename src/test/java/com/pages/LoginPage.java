package com.pages;

import com.microsoft.playwright.Locator;
import com.utils.BasePageFactory;

import static com.config.ConfigurationManager.config;

public class LoginPage extends BasePage {

    private static final String USERNAME_INPUT = "id=user-name";
    private static final String PASSWORD_INPUT = "id=password";
    private static final String LOGIN_BUTTON = "id=login-button";
    private static final String ERROR_MESSAGE_CONTAINER = ".error-message-container";

    public LoginPage open() {
        page.navigate(config().baseUrl());
        return this;
    }

    public LoginPage fillUsername(final String userName) {
        page.fill(USERNAME_INPUT, userName);
        return this;
    }

    public LoginPage fillPassword(final String pass) {
        page.fill(PASSWORD_INPUT, pass);
        return this;
    }

    public Locator getInputFieldUserName() {
        return page.locator(USERNAME_INPUT);
    }

    public Locator getLoginButton() {
        return page.locator(LOGIN_BUTTON);
    }

    public Locator getInputFieldPassword() {
        return page.locator(PASSWORD_INPUT);
    }

    public Locator getErrMsgContainer() {
        return page.locator(ERROR_MESSAGE_CONTAINER);
    }

    public ProductsPage submitLogin() {
        getLoginButton().click();
        return BasePageFactory.createInstance(page, ProductsPage.class);
    }

    public ProductsPage loginAs(final String username, final String password) {
        fillUsername(username);
        fillPassword(password);
        return submitLogin();
    }
}
