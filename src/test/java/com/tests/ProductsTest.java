package com.tests;

import com.components.Header;
import com.components.SortOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsTest extends BaseTest {

    @BeforeEach
    public void setUpCookies() {
        setUpWithCookies();
    }

    @Test
    public void sortingItemsInReverseOrder() {
        getProductsPage().open();
        getProductsPage().setSortFilter(SortOrder.NAME_DESC.getSortValue());

        List<String> actualProductNames = getProductsPage().getProductNames().allInnerTexts();

        List<String> sortedProductNames = new ArrayList<>(actualProductNames);
        sortedProductNames.sort(Collections.reverseOrder());

        Assertions.assertEquals(sortedProductNames, actualProductNames, "Invalid sorting by product DESC");
    }

    @Test
    public void sortingItemsLowToHighPrice() {
        getProductsPage().open();
        getProductsPage().setSortFilter(SortOrder.PRICE_ASC.getSortValue());

        // Преобразуем в список чисел (удаляем "$" и парсим в Double)
        List<Double> actualItemPrices = getProductsPage().getProductPrices().allInnerTexts().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .collect(Collectors.toList());

        // Создаем копию списка и сортируем его
        List<Double> sortedItemPrices = actualItemPrices.stream()
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertEquals(sortedItemPrices, actualItemPrices, "Invalid sorting by price ASC");
    }

    @Test
    public void addItemToCartValidation() {
        Header header = new Header(page);
        getProductsPage().open();

        // Чтобы не было хардкода - получаем динамично первое имя товара, предварительно дожидаемся его появления.
        getProductsPage().getProductNames().first().waitFor();
        String productName = getProductsPage().getProductNames().first().innerText();

        getProductsPage().addItemToCartByName(productName);
        assertTrue(header.cartBadgeCountValidator(1), "Invalid item count in cart");

        // Получаем список всех наименований товаров
        List<String> productNames = getProductsPage().getProductNames().allInnerTexts();

        // Получаем состояние всех кнопок добавления в корзину
        List<String> buttonTexts = getProductsPage().getAddToCartButtonTexts().allInnerTexts();

        // На всякий случай проверяем что размеры списков равны.
        // Для отладки + исключаем проверки ниже если списки не равны.
        if (productNames.size() != buttonTexts.size()) {
            throw new AssertionError(String.format(
                    "Mismatch in product and button count! Products: %d, Buttons: %d",
                    productNames.size(), buttonTexts.size()
            ));
        }

        // Проверяем состояние всех кнопок добавления в корзину
        for (int i = 0; i < productNames.size(); i++) {
            String currentProduct = productNames.get(i);
            String buttonText = buttonTexts.get(i);

            if (currentProduct.equals(productName)) {
                Assertions.assertEquals("Remove", buttonText, "Invalid text in button 'Remove'");
                continue;
            }

            Assertions.assertEquals("Add to cart", buttonText, "Invalid text in button 'Add to cart'");
        }
    }
}
