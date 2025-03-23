package com.tests;

import com.microsoft.playwright.Locator;
import com.models.ShipInfo;
import com.pages.CartPage;
import com.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartTest extends BaseTest {

    @BeforeEach
    public void setUpCookies() {
        setUpWithCookies();
    }

    @Test
    public void cartPageWithItemsAttributes() {
        ProductsPage productsPage = getProductsPage().open();
        Locator items = productsPage.getProductNames();
        String productName = "";
        String secondProductName = "";

        for (int i = 0; i < items.count(); i++) {
            productName = items.nth(0).innerText();
            if (i > 0) {
                secondProductName = items.nth(1).innerText();
                break;
            }
        }

        productsPage.addItemToCartByName(productName);
        CartPage cartPage = productsPage.addItemToCartAndOpenCart(secondProductName);
        assertThat(cartPage.getTitle()).hasText("Your Cart");
        assertThat(cartPage.getItems().nth(0)).hasText(productName);
        assertThat(cartPage.getItems().nth(1)).hasText(secondProductName);
        cartPage.checkItemAttributes();
    }

    @Test
    public void addItemToCardAndRemove() {
        getProductsPage().open();
        String productName = getProductsPage().getProductNames().first().innerText();

        CartPage cartPage = getProductsPage().addItemToCartAndOpenCart(productName);
        cartPage.clickOnRemove();
        assertThat(cartPage.getItems()).not().isVisible();
    }

    @Disabled("Нужно расставить правильно таймауты")
    @Test
    public void itemBuy() {
        getProductsPage().open();
        String productName = getProductsPage().getProductNames().first().innerText();

        CartPage cartPage = getProductsPage().addItemToCartAndOpenCart(productName);
        assertThat(cartPage.getItems()).containsText(productName);

        cartPage.clickOnCheckout();

        ShipInfo shipInfo = ShipInfo.builder()
                .firstName("User")
                .lastName("LastName")
                .postCode("123456")
                .build();

        cartPage.fillInfo(shipInfo).clickOnContinue().clickOnFinish();
        assertThat(cartPage.getCompleteHeader()).hasText("Thank you for your order!");
    }
}
