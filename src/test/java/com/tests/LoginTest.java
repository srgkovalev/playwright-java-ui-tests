package com.tests;

import com.pages.ProductsPage;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.config.ConfigurationManager.config;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void loginPageElements() {
        getLoginPage().open();
        Map<String, String> expectedAttributes = Map.of(
                "placeholder", "Username",
                "type", "text",
                "name", "user-name",
                "autocorrect", "off",
                "autocapitalize", "none",
                "value", ""
        );
        expectedAttributes.forEach((key, value) ->
                assertThat(getLoginPage().getInputFieldUserName()).hasAttribute(key, value)
        );

        // Создаем копию, перезаписываем значения
        Map<String, String> expectedAttributesPassword = new HashMap<>(expectedAttributes);
        expectedAttributesPassword.put("placeholder", "Password");
        expectedAttributesPassword.put("name", "password");
        expectedAttributesPassword.put("type", "password");

        expectedAttributesPassword.forEach((key, value) ->
                assertThat(getLoginPage().getInputFieldPassword()).hasAttribute(key, value)
        );

        Map<String, String> expectedAttributesLoginButton = Map.of(
                "type", "submit",
                "class", "submit-button btn_action",
                "value", "Login"
        );
        expectedAttributesLoginButton.forEach((key, value) ->
                assertThat(getLoginPage().getLoginButton()).hasAttribute(key, value)
                );

        assertThat(getLoginPage().getErrMsgContainer()).isEmpty();
    }

    @Test
    public void correctLoginCredentials() {
        getLoginPage().open();
        ProductsPage productsPage = getLoginPage().loginAs(getUserName(), getUserPass());
        assertThat(productsPage.getTitle()).hasText("Products");
        assertThat(page).hasURL(config().baseUrl() + config().productPath());
    }

    @Test
    public void successfulLogout() {
        getLoginPage().open();
        getLoginPage().loginAs(getUserName(), getUserPass())
                .clickOnLogout();
        assertThat(page).hasURL(config().baseUrl());
    }

    @Test
    public void invalidLoginCredentials() {
        getLoginPage().open();
        getLoginPage().loginAs("fake", "fake");
        assertThat(getLoginPage().getErrMsgContainer())
                .hasText("Epic sadface: Username and password do not match any user in this service");
        assertThat(page).hasURL(config().baseUrl());
    }
}