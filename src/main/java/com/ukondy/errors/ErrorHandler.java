package com.ukondy.errors;

import com.ukondy.errors.entity.Error;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ErrorHandler {
    public static boolean urlError(String url, Label lblUrlError, TextField inputUrl) {
        Error error = new Error("", lblUrlError, inputUrl);
        if(url.equals("")) {
            error.setMsg("Поле ссылки не может быть пустым!");
            error.showError();
            return true;
        } else if(!url.matches("^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
            error.setMsg("Ссылка некорректна! Она должна начинаться с http:// или https://"); // https://naver.com
            error.showError();
            return true;
        } else {
            error.hideError();
        }

        return false;
    }
}
