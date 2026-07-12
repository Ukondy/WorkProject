package com.ukondy.errors.entity;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Error {
    String msg;
    Label label;
    TextField input;

    public Error(String msg, Label label, TextField input) {
        this.msg = msg;
        this.label = label;
        this.input = input;
    }

    public void showError() {
        label.setText(msg);
        label.setVisible(true);
        label.setManaged(true);
        input.setStyle("-fx-border-color: #ff453a; -fx-focused-border-color: #ff453a;");
    }

    public void hideError() {
        label.setVisible(false);
        label.setManaged(false);
        input.setStyle("");
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
