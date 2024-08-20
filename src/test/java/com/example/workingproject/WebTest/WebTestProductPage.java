package com.example.workingproject.WebTest;

import Utils.AllureTestListener;
import UtilsClass.Fruit;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureTestListener.class)
@Epic("UI")
@Feature("Тестирование Web-страницы c фруктами")
public class WebTestProductPage {

    @Test
    @DisplayName("Тест с корректным именем")
    @Description("Тест проверяющий только что добавленный продукт со стандартными параметрами")
    @Story("Тестирование только что добавленного продукта со стандартными параметрами")
    public void testProductPageOnWebWhenAddedNewProductWithUsuallyParams() {
        WebProductsManipulator manipulator = WebProductsManipulator.getManipulator();
        manipulator.createBrowserConnection();
        manipulator.toFoodPage();

        Fruit expectedFruit = new Fruit("SomeFruit", "Фрукт", 1);
        manipulator.addFruit(expectedFruit);
        Fruit actFruit = manipulator.getLastAddedFruit();

        Assertions.assertEquals(expectedFruit, actFruit, "Объект, который был добавлен в начале, равен объекту, который в итоге добавился.");

        manipulator.refresh();
        Fruit.refresh();
    }

    @Test
    @DisplayName("Тест с некорректным именем")
    @Description("Тест проверяющий только что добавленный продукт с null в параметре name")
    @Story("Тестирование только что добавленного продукта с null в параметре name")
    public void testProductPageOnWebWhenAddedNewProductWithNullName() {
        WebProductsManipulator manipulator = WebProductsManipulator.getManipulator();
        manipulator.createBrowserConnection();
        manipulator.toFoodPage();

        Fruit expectedFruit = new Fruit(null, "Овощ", 1);
        manipulator.addFruit(expectedFruit);
        Fruit actFruit = manipulator.getLastAddedFruit();

        Assertions.assertEquals(expectedFruit, actFruit, "Объект, который был добавлен в начале, равен объекту, который в итоге добавился.");

        manipulator.refresh();
        Fruit.refresh();
    }

    @Test
    @DisplayName("Тест по получению фрукта")
    @Description("Тест проверяющий получение продукта по его Id")
    @Story("Тестирование получение продкута по его Id")
    public void testProductPageOnWebWhenTryingToGetFruitWithExistsIndex() {
        WebProductsManipulator manipulator = WebProductsManipulator.getManipulator();
        manipulator.createBrowserConnection();
        manipulator.toFoodPage();

        Fruit expectedFruit = new Fruit(2, "Капуста", "Овощ", 0);
        Fruit actFruit = manipulator.getFruitByIdFromWeb(2);

        Assertions.assertEquals(expectedFruit, actFruit, "Искомый, по id, объект оказался таким, как мы ожидали.");

        manipulator.refresh();
        Fruit.refresh();
    }
}
