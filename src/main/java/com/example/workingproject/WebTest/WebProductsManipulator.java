package com.example.workingproject.WebTest;

import com.example.workingproject.WebTest.PageObjects.FoodPage;
import com.example.workingproject.WebTest.PageObjects.MainPage;
import UtilsClass.Fruit;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class WebProductsManipulator {
    private static WebProductsManipulator manipulator;
    private static final ConfigureSelenideConnection browser = new ConfigureSelenideConnection();
    private WebProductsManipulator() {}

    public static WebProductsManipulator getManipulator() {
        if(manipulator == null) {
            manipulator = new WebProductsManipulator();
        }
        return manipulator;
    }

    @Step("Шаг 1: Создание соеденения с браузером")
    public void createBrowserConnection() {
        browser.createConnection();
    }

    @Step("Шаг 2: Переход на страницу с фруктами")
    public void toFoodPage() {
        MainPage.toFoodPage();
    }

    @Step("Шаг 3.1: Добавление нового фрукта")
    public void addFruit(Fruit fruit) {
        FoodPage.addNewFruit(fruit);
    }

    @Step("Шаг 3.1: Добавление нового фрукта")
    public void addFruit(String name, String type, Integer exotic) {
        FoodPage.addNewFruit(new Fruit(name, type, exotic));
    }

    @Step("Шаг 3.2: Получение добавленного фрукта по его индексу")
    public Fruit getFruitByIdFromWeb(Integer id) {
        return FoodPage.getCertainFruit(id);
    }

    @Step("Шаг 3.3: Получение последнего добавленного фрукта")
    public Fruit getLastAddedFruit() {
        return FoodPage.getLastAddedFruit();
    }

    @Step("Шаг 4: Перезагрузка UI-интерфейса")
    public void refresh() {
        $(byText("Песочница")).click();
        $(byText("Сброс данных")).should(appear).click();
    }
}
