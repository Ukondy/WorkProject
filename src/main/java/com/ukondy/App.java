package com.ukondy;

import com.ukondy.navigation.NavigationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Загружаем интерфейс из FXML файла
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/ukondy/on_site/on_site.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 780, 900);

        // Передаем сцену в менеджер навигации перед показом окна
        NavigationManager.setMainScene(scene);

        // Жестко запрещаем окну сжиматься меньше этого размера
        stage.setMinWidth(780);
        stage.setMinHeight(900);

        stage.setTitle("JavaFX FXML Приложение");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
