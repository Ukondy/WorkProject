package com.example.workingproject.WebTest.PageObjects;

import com.codeborne.selenide.ElementsCollection;
import UtilsClass.Fruit;
import lombok.Data;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class FoodPage {
    private static ElementsCollection fruits = $$(By.xpath("//div[@class='container-fluid']//table[@class = 'table']/tbody/tr"));

    public static synchronized void addNewFruit(Fruit fruit) {
        $(byText("Добавить")).click();

        $(By.xpath("//div[@class='modal-dialog']//h5[contains(text(), 'Добавление товара')]")).should(appear);
        $(By.xpath("//div//label[text()='Наименование']/following-sibling::input")).should(appear).setValue(fruit.getName());
        $("#type").should(appear).selectOption($("#type").getOptions().texts().indexOf(fruit.getType() == null ? "Овощ" : fruit.getType()));
        $(By.xpath("//div//label[text()='Экзотический']/preceding-sibling::input")).should(appear).setSelected(Boolean.parseBoolean(fruit.getExotic() == 1 ? "true" : "false"));

        $(byText("Сохранить")).click();
    }

    public static Fruit getCertainFruit(int index) {
        fruits.last().shouldHave(text(Fruit.getAllFruit().toString()));
        List<String> fruitParam = List.of(fruits.get(--index).getText().split(" "));
        return getFruit(fruitParam);
    }

    public static Fruit getLastAddedFruit() {
        fruits.last().shouldHave(text(Fruit.getAllFruit().toString()));
        List<String> fruitParam = List.of(fruits.last().getText().split(" "));
        return getFruit(fruitParam);
    }

    private static Fruit getFruit(List<String> fruitParam) {
        if (fruitParam.size() == 4) {
            return new Fruit(Integer.parseInt(fruitParam.get(0)), fruitParam.get(1), fruitParam.get(2), Boolean.parseBoolean(fruitParam.get(3)) ? 1 : 0);
        } else {
            return new Fruit(Integer.parseInt(fruitParam.get(0)), null, fruitParam.get(1), Boolean.parseBoolean(fruitParam.get(2)) ? 1 : 0);
        }
    }
}
