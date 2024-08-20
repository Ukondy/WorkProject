package com.example.workingproject.DBTest;

import Utils.AllureTestListener;
import UtilsClass.Fruit;
import com.example.workingproject.DBTest.service.FoodService;
import com.example.workingproject.DBTest.service.FoodServiceJDBC;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureTestListener.class)
@Epic("DB-jdbc")
@Feature("Тестирование DB-страницы c фруктами")
public class DBTestProductPage {
    private final FoodService foodService = new FoodServiceJDBC();

    @Test
    @DisplayName("Тест с корректным именем")
    @Description("Тест проверяющий только что добавленный продукт со стандартными параметрами")
    @Story("Тестирование только что добавленного продукта со стандартными параметрами")
    public void testProductPageOnDBWhenAddedNewProductWithUsuallyParams() {
        DBConnection.getJDBCConnection();

        Fruit fruit = new Fruit("SomeFruit", "Овощ", 1);
        foodService.dbRefresh();
        foodService.addFruit(fruit.getName(), fruit.getType(), fruit.getExotic());

        Assertions.assertEquals(fruit, foodService.getFruitById(fruit.getId()), "Объект, который был добавлен в начале, равен объекту, который в итоге добавился.");

        Fruit.refresh();
        foodService.dbRefresh();
    }

    @Test
    @DisplayName("Тест с некорректным именем")
    @Description("Тест проверяющий только что добавленный продукт с null в параметре name")
    @Story("Тестирование только что добавленного продукта с null в параметре name")
    public void testProductPageOnDBWhenAddedNewProductWithNullName() {
        DBConnection.getJDBCConnection();

        Fruit fruit = new Fruit(null, "Овощ", 1);
        foodService.dbRefresh();
        foodService.addFruit(fruit.getName(), fruit.getType(), fruit.getExotic());

        Assertions.assertEquals(fruit, foodService.getFruitById(fruit.getId()), "Объект, который был добавлен в начале, равен объекту, который в итоге добавился.");

        Fruit.refresh();
        foodService.dbRefresh();
    }

    @Test
    @DisplayName("Тест по получению фрукта")
    @Description("Тест проверяющий получение продукта по его Id")
    @Story("Тестирование получение продкута по его Id")
    public void testProductPageOnDBWhenTryingToGetFruitWithExistsIndex() {
        DBConnection.getJDBCConnection();

        Fruit fruit = new Fruit(2,"Капуста", "Овощ", 0);
        foodService.dbRefresh();

        Assertions.assertEquals(fruit, foodService.getFruitById(2), "Объект, который был добавлен в начале, равен объекту, который в итоге добавился.");

        Fruit.refresh();
        foodService.dbRefresh();
    }
}
