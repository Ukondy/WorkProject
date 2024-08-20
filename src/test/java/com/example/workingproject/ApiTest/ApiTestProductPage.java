package com.example.workingproject.ApiTest;

import Utils.AllureTestListener;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

//allure serve "target\allure-results"
//allure generate "target\allure-results" --clean
@ExtendWith(AllureTestListener.class)
@Epic("Rest-API")
@Feature("Тестирование RestAPI-страницы c фруктами")
public class ApiTestProductPage {
    private final String URL = "http://localhost:8080/api/food";
    private final String STANDARD_OBJECT = "{\"name\":\"string\",\"type\":\"VEGETABLE\",\"exotic\":true}";

    @Test
    @DisplayName("Тест с корректным именем")
    @Description("Тест проверяющий только что добавленный продукт со стандартными параметрами")
    @Story("Тестирование только что добавленного продукта со стандартными параметрами")
    public void testProductPageWhenAddedNewProductWithUsuallyParams() {
        given().contentType(ContentType.JSON).body(STANDARD_OBJECT)
                .when().post(URL)
                .then().statusCode(200).onFailMessage("Ошибка в выполнении POST-запроса");
    }

    @Test
    @DisplayName("Тест с некорректным именем")
    @Description("Тест проверяющий только что добавленный продукт с null в параметре name")
    @Story("Тестирование только что добавленного продукта с null в параметре name")
    public void testProductPageWhenAddedNewProductWithNullName() {
        Response post = given().contentType(ContentType.JSON).body(STANDARD_OBJECT)
                .when().post(URL);

        Assertions.assertEquals(200, post.statusCode(), "Статус код: " + post.getStatusCode() + ", сообщение: " + post.getBody());
    }

    @Test
    @DisplayName("Тест по получению фрукта")
    @Description("Тест проверяющий получение продукта по его Id")
    @Story("Тестирование получение продкута по его Id")
    public void testProductPageWhenTryingToGetFruitWithExistsIndex() {
        given().contentType(ContentType.JSON)
                .when().get(URL)
                .then().assertThat().body("[0].name", is("Апельсин"))
                .body("[1].name", is("Капуста"))
                .body("[2].name", is("Помидор"))
                .body("[3].name", is("Яблоко"))
                .body("[0].exotic", is(true));
    }
}
