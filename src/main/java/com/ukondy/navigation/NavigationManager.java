package com.ukondy.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class NavigationManager {

    private static Scene mainScene;

    // Запоминаем текущую сцену при старте приложения
    public static void setMainScene(Scene scene) {
        mainScene = scene;
    }

    // Метод для переключения корневых страниц приложения
    public static void navigateTo(String fxmlPath) {
        try {
            if (mainScene == null) return;

            // Загружаем новый FXML файл
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlPath));
            Parent newRoot = loader.load();

            // Просто подменяем корень у сцены
            mainScene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
