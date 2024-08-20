package UtilsClass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@NoArgsConstructor
public class Fruit {
    @JsonIgnore
    @Getter
    private static Integer allFruit = 4;
    @JsonIgnore
    private Integer id;
    private String name;
    private String type;
    private Integer exotic;

    public Fruit(String name, String type, @DefaultValue(value = "0") Integer exotic) {
        this.name = name;
        if(type.equals("VEGETABLE")) {
            this.type = "Овощ";
        } else if(type.equals("FRUIT")) {
            this.type = "Фрукт";
        } else {
            this.type = type;
        }
        if(exotic == 1 || exotic == 0) {
            this.exotic = exotic;
        }
        id = ++allFruit;
    }

    public Fruit(Integer id, String name, String type, @DefaultValue(value = "0") Integer exotic) {
        this.id = id;
        this.name = name;
        if(type.equals("VEGETABLE")) {
            this.type = "Овощ";
        } else if(type.equals("FRUIT")) {
            this.type = "Фрукт";
        } else {
            this.type = type;
        }
        if(exotic == 1 || exotic == 0) {
            this.exotic = exotic;
        }
    }

    @Step("Шаг 5: Сброс данных класса Fruit")
    public static void refresh() {
        allFruit = 4;
    }

    @Override
    public String toString() {
        return String.format("\n============================================================\n" +
                               "Fruit: id - %d, name - %s, type - %s, isExotic - %d" +
                             "\n============================================================\n",
                             id, name, type, exotic);
    }
}




