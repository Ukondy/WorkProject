package com.ukondy.photo;

import com.ukondy.navigation.NavigationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class PhotoController {

    // === Верхняя навигация ===
    @FXML
    private Button btnSettings;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnExit;

    // === Режим работы (Сегментированный переключатель) ===
    @FXML
    private ToggleGroup modeGroup;

    @FXML
    private RadioButton modeSite;

    @FXML
    private RadioButton modeCustom;

    @FXML
    private RadioButton modePhoto;

    // === Статистика галереи ===
    @FXML
    private Label lblTotalItems;

    // === Область просмотра и контейнер карточек картинок ===
    @FXML
    private ScrollPane scrollGallery;

    @FXML
    private HBox boxImagesContainer;

    // === Панель инструментов под галереей ===
    @FXML
    private Button btnAddPhoto;

    @FXML
    private Button btnDeletePhoto;

    @FXML
    private Button btnSort;

    @FXML
    private TextField inputGap; // Зазор в пикселях

    // === Формат преобразования ===
    @FXML
    private ToggleGroup formatGroup;

    @FXML
    private RadioButton formatPng;

    @FXML
    private RadioButton formatPdf;

    // === Путь сохранения и кнопка действия ===
    @FXML
    private TextField inputSavePath;

    @FXML
    private Button btnExecute;

    /**
     * Вызывается автоматически JavaFX после загрузки FXML структуры.
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

    // === Обработчики верхней навигации ===
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
        System.exit(0);
    }

    // === Переключение страниц (Режимы загрузки) ===
    @FXML
    void switchToSiteMode(ActionEvent event) {
        if (modeSite != null && modeSite.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/on_site/on_site.fxml");
        }
    }

    @FXML
    void switchToCustomMode(ActionEvent event) {
        if (modeCustom != null && modeCustom.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/custom_page/custom_page.fxml");
        }
    }

    // === Инструменты управления локальными фото ===
    @FXML
    void handleAddPhoto(ActionEvent event) {
        // Логика добавления изображений в boxImagesContainer
    }

    @FXML
    void handleDeletePhoto(ActionEvent event) {
        // Логика удаления выбранных изображений из галереи
    }

    @FXML
    void handleSort(ActionEvent event) {
        // Логика сортировки изображений (1..3)
    }

    // === Выбор директории через мышь ===
    @FXML
    void selectFolder(MouseEvent event) {
        // Логика открытия диалогового окна выбора папки
    }

    // === Главное действие ===
    @FXML
    void handleExecute(ActionEvent event) {
        // Запуск процесса локальной склейки изображений
        String gapValue = inputGap.getText();
        String savePath = inputSavePath.getText();

        boolean isPng = formatPng.isSelected();
        // Логика обработки и склеивания...
    }
}
