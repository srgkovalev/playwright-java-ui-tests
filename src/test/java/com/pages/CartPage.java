package com.pages;

import com.components.Header;
import com.microsoft.playwright.Locator;
import com.models.ShipInfo;

import java.util.regex.Pattern;

import static com.config.ConfigurationManager.config;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.utils.BasePageFactory.createInstance;

public final class CartPage extends BasePage {

    private Header header;

    @Override
    public void initComponents() {
        header = new Header(page);
    }

    public CartPage open() {
        page.navigate(config().baseUrl()+config().cartPath());
        return createInstance(page, CartPage.class);
    }

    public Locator getTitle() {
        return page.locator(".title");
    }

    public Locator getItems() {
        return page.locator("//div[@class='cart_list']//div[@class='inventory_item_name']");
    }

    public Locator getItemPrices() {
        return page.locator("//div[@class='cart_list']//div[@class='inventory_item_price']");
    }

    public Locator getItemQuantity() {
        return page.locator("//div[@class='cart_list']//div[@class='cart_quantity']");
    }

    public Locator getRemoveButtons() {
        return page.locator("//button[contains(@data-test, 'remove')]");
    }

    public void checkItemAttributes() {
        Locator items = getItems();
        Locator prices = getItemPrices();
        Locator quantity = getItemQuantity();
        Locator removeButtons = getRemoveButtons();

        for (int i = 0; i < items.count(); i++) {
            // nth позволяет пробегаться по элементам в локаторе
            assertThat(items.nth(i)).isVisible();
            assertThat(items.nth(i)).not().hasText("");

            assertThat(prices.nth(i)).isVisible();
            // Чтобы сократить несколько проверок, решил применить регулярку
            assertThat(prices.nth(i)).hasText(Pattern.compile("^(?!0$).+$"));

            assertThat(quantity.nth(i)).isVisible();
            assertThat(quantity.nth(i)).hasText(Pattern.compile("^(?!0$).+$"));

            assertThat(removeButtons.nth(i)).isVisible();
            assertThat(removeButtons.nth(i)).hasText("Remove");
        }
    }

    public CartPage clickOnCheckout(){
        page.locator("//button[@data-test='checkout']").click();
        return this;
    }

    public CartPage fillInfo(ShipInfo shipInfo){
        page.fill("//input[@data-test='firstName']", shipInfo.getFirstName());
        page.fill("//input[@data-test='lastName']", shipInfo.getLastName());
        page.fill("//input[@data-test='postalCode']", shipInfo.getPostCode());
        return this;
    }

    public CartPage clickOnContinue(){
        page.locator("//input[@data-test='continue']").click();
        return this;
    }

    public CartPage clickOnFinish(){
        page.locator("//button[@data-test='finish']").click();
        return this;
    }

    public CartPage clickOnRemove(){
        page.locator("//button[@data-test='remove-sauce-labs-backpack']").click();
        return this;
    }

    public Locator getCompleteHeader() {
        return page.locator("//h2[@data-test='complete-header']");
    }

    public CartPage clickOnBackHome() {
        page.locator("//button[@data-test='back-to-products']").click();
        return this;
    }
}