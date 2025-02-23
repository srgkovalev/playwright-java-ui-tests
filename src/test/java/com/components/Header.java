package com.components;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public final class Header extends BaseComponent {

    private static final String CART_SELECTOR = "//a[@data-test='shopping-cart-link']";
    private static final String CART_BADGE_SELECTOR = "//span[@data-test='shopping-cart-badge']";
    private static final String BURGER_MENU_SELECTOR = "#react-burger-menu-btn";

    public Header(final Page page) {
        super(page);
    }

    public void clickOnHamburgerIcon() {
        page.click(BURGER_MENU_SELECTOR);
    }

    public void clickOnCart() {
        page.locator(CART_SELECTOR).click();
    }

    public boolean cartIsEmpty() {
        Locator cartBadge = page.locator(CART_BADGE_SELECTOR);

        return cartBadge.count() == 0;
    }

    public boolean cartBadgeCountValidator(int expectedCount) {

        if (cartIsEmpty() == true) {
            return expectedCount == 0;
        }

        String actualCountText = page.locator(CART_BADGE_SELECTOR).innerText().trim();
        int actualCount = Integer.parseInt(actualCountText);

        return expectedCount == actualCount;
    }
}