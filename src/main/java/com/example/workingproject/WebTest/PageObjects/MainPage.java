package com.example.workingproject.WebTest.PageObjects;

import lombok.Data;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Data
public class MainPage {

    public static void toFoodPage() {
        $(byText("Песочница")).click();
        $(byText("Товары")).should(appear).click();
    }
}