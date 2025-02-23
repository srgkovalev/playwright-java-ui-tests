package com.pages;

import com.components.Header;
import com.components.Menu;
import com.microsoft.playwright.Locator;
import com.utils.BasePageFactory;

import static com.config.ConfigurationManager.config;

public class ProductsPage extends BasePage {

    private Header header;
    private Menu menu;

    @Override
    public void initComponents() {
        header = new Header(page);
        menu = new Menu(page);
    }

    public ProductsPage open() {
        page.navigate(config().baseUrl()+config().productPath());
        return BasePageFactory.createInstance(page, ProductsPage.class);
    }

    public Locator getTitle() {
        return page.locator(".title");
    }

    public Locator getProductNames() {
        return page.locator("[data-test='inventory-item-name']");
    }

    public Locator getProductPrices() {
        return page.locator("[data-test='inventory-item-price']");
    }

    public Locator getAddToCartButtonTexts() {
        return page.locator("button.btn_inventory");
    }

    public ProductsPage setSortFilter(String sortName) {
        page.locator("[data-test='product-sort-container']").selectOption(sortName);
        return this;
    }

    public ProductsPage addItemToCartByName(String itemName) {
        page.locator(String.format("//div[text()='%s']//following::button[1]", itemName)).click();
        return this;
    }

    public CartPage clickOnCart() {
        header.clickOnCart();
        return BasePageFactory.createInstance(page, CartPage.class);
    }

    public CartPage addItemToCartAndOpenCart(String productName) {
        addItemToCartByName(productName);
        return clickOnCart();
    }

    public LoginPage clickOnLogout() {
        header.clickOnHamburgerIcon();
        menu.clickOnLogout();

        return BasePageFactory.createInstance(page, LoginPage.class);
    }
}
