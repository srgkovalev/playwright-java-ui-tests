package com.components;

import com.microsoft.playwright.Page;

public final class Menu extends BaseComponent {

    public Menu(final Page page) {
        super(page);
    }

    public void clickOnLogout() {
        page.click("#logout_sidebar_link");
    }

    public void  clickOnCloseMenu() {
        page.locator("//button[]").click();
    }
}