package com.ukondy.custom;

import com.ukondy.navigation.NavigationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class CustomController {

    // === Кнопки навигации ===
    @FXML
    private Button btnSettings;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnExit;

    // === Группа режима работы ===
    @FXML
    private ToggleGroup modeGroup;

    @FXML
    private RadioButton modeSite;

    @FXML
    private RadioButton modeCustom;

    @FXML
    private RadioButton modePhoto;

    // === Поля ввода ===
    @FXML
    private TextField inputUrl;

    @FXML
    private TextField nameMatcher; // Новое поле для регулярных выражений

    // === Группа формата ===
    @FXML
    private ToggleGroup formatGroup;

    @FXML
    private RadioButton formatPng;

    @FXML
    private RadioButton formatPdf;

    // === Сохранение и старт ===
    @FXML
    private TextField inputSavePath;

    @FXML
    private Button btnExecute;

    /**
     * Инициализация контроллера (вызывается автоматически)
     */
    @FXML
    public void initialize() {
        // --- 1. Создаем выпадающий список для кнопки "Настройки" ---
        ContextMenu settingsMenu = new ContextMenu();

        MenuItem itemDefaultValues = new MenuItem("Стандартные значения");
        // Привязываем действие к пункту меню
        itemDefaultValues.setOnAction(event -> handleDefaultSettings(null));

        settingsMenu.getItems().addAll(itemDefaultValues);

        // Открываем меню при клике на кнопку "Настройки"
        btnSettings.setOnAction(event -> {
            settingsMenu.show(btnSettings,
                    btnSettings.localToScreen(0, 0).getX(),
                    btnSettings.localToScreen(0, 0).getY() + btnSettings.getHeight()
            );
        });


        // --- 2. Создаем выпадающий список для кнопки "Помощь" ---
        ContextMenu helpMenu = new ContextMenu();

        MenuItem itemGithub = new MenuItem("GitHub репозиторий");
        MenuItem itemBugReport = new MenuItem("Отправить отчёт об ошибке");
        MenuItem itemTutorial = new MenuItem("Обучение");
        MenuItem itemVersion = new MenuItem("Версия: 1.0.0");

        // Делаем версию некликабельной (отключенной) по макету Figma
        itemVersion.setDisable(true);

        // ПРИВЯЗКА ДЕЙСТВИЙ (Имена переменных теперь строго совпадают):
        itemTutorial.setOnAction(event -> openTutorial(null));
        itemGithub.setOnAction(event -> System.out.println("Открыть URL в браузере: https://github.com..."));
        itemBugReport.setOnAction(event -> System.out.println("Открыть окно отправки отчёта об ошибке..."));

        helpMenu.getItems().addAll(itemGithub, itemBugReport, itemTutorial, itemVersion);


        // Открываем меню при клике на кнопку "Помощь"
        btnHelp.setOnAction(event -> {
            helpMenu.show(btnHelp,
                    btnHelp.localToScreen(0, 0).getX(),
                    btnHelp.localToScreen(0, 0).getY() + btnHelp.getHeight()
            );
        });

        // --- 3. Закрытие меню при изменении размера окна (чтобы они не висели в воздухе) ---
        btnSettings.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obsW, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        newWindow.widthProperty().addListener((o, oldVal, newVal) -> {
                            settingsMenu.hide();
                            helpMenu.hide();
                        });
                        newWindow.heightProperty().addListener((o, oldVal, newVal) -> {
                            settingsMenu.hide();
                            helpMenu.hide();
                        });
                    }
                });
            }
        });

        // Применяем стили к меню "Настройки"
        settingsMenu.getStyleClass().add("context-menu");
        settingsMenu.getStyleClass().add("bg-main"); // Подгрузит базовые цвета

        // Применяем стили к меню "Помощь"
        helpMenu.getStyleClass().add("context-menu");
        helpMenu.getStyleClass().add("bg-main");

        // Важно: чтобы JavaFX точно нашел файл, передайте путь к стилям напрямую в меню
        String cssPath = Objects.requireNonNull(getClass().getResource("/com/ukondy/style.css")).toExternalForm();
        // Замените путь выше на ваш реальный путь к файлу style.css в папке resources

        settingsMenu.getStyle().concat(cssPath);
        helpMenu.getStyle().concat(cssPath);
    }

    // Ваши существующие методы обработчиков:
    @FXML
    void handleDefaultSettings(Object event) { /* логика */ }

    @FXML
    void openTutorial(Object event) { /* логика */ }

    // === Обработчики верхнего меню ===
    @FXML
    void handleDefaultSettings(ActionEvent event) {
        // Логика кнопки "Настройки"
    }

    @FXML
    void openTutorial(ActionEvent event) {
        // Логика кнопки "Помощь"
    }

    @FXML
    void handleExit(ActionEvent event) {
        // Завершение работы программы
        System.exit(0);
    }

    // === Логика переключения режимов ===
    @FXML
    void switchToSiteMode(ActionEvent event) {
        if (modeSite != null && modeSite.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/on_site/on_site.fxml");
        }
    }

    @FXML
    void switchToPhotoMode(ActionEvent event) {
        if (modePhoto != null && modePhoto.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/photo_page/photo_page.fxml"); // Укажите имя вашего нового файла
        }
    }

    // === Клик по выбору папки ===
    @FXML
    void selectFolder(MouseEvent event) {
        // Выбор директории сохранения файлов через Проводник
    }

    // === Главное действие ===
    @FXML
    void handleExecute(ActionEvent event) {
        // Сбор данных из полей формы
        String url = inputUrl.getText();
        String matcherText = nameMatcher.getText();
        String savePath = inputSavePath.getText();

        RadioButton selectedMode = (RadioButton) modeGroup.getSelectedToggle();
        RadioButton selectedFormat = formatGroup.getSelectedToggle() != null
                ? (RadioButton) formatGroup.getSelectedToggle() : formatPng;

        // Здесь запускается ваш оффлайн-парсер
    }
}

