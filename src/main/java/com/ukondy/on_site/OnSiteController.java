package com.ukondy.on_site;

import com.ukondy.navigation.NavigationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

import static com.ukondy.errors.ErrorHandler.urlError;

public class OnSiteController {
    @FXML
    public Button btnSettings;

    @FXML
    public Button btnHelp;
    // === Элементы меню ===
    @FXML
    private MenuItem menuSettingsDefault;

    @FXML
    private MenuItem menuHelpGithub;

    @FXML
    private MenuItem menuHelpBug;

    @FXML
    private MenuItem menuHelpTutorial;

    @FXML
    private MenuItem menuHelpVersion;

    @FXML
    private Button btnExit;

    // === Выбор режима работы ===
    @FXML
    private ToggleGroup modeGroup;

    @FXML
    private RadioButton modeSite;

    @FXML
    private RadioButton modeCustom;

    @FXML
    private RadioButton modePhoto;

    // === Поля ввода и выбора ===
    @FXML
    private TextField inputUrl;

    @FXML
    private ComboBox<String> comboSource; // Указан тип String для названий сайтов

    // === Выбор формата конвертации ===
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

    @FXML
    private Label lblUrlError;

    /**
     * Метод инициализации. Вызывается автоматически после загрузки FXML.
     * Здесь можно заполнить выпадающий список (ComboBox) начальными данными.
     */
    @FXML
    public void initialize() {
        if (comboSource != null) {
            comboSource.getItems().addAll("Naver", "MangaBuff", "Mangalib");
        }
        // --- 1. Создаем выпадающий список для кнопки "Настройки" ---
        ContextMenu settingsMenu = new ContextMenu();
        MenuItem itemDefaultValues = new MenuItem("Стандартные значения");

        // Привязываем действие к пункту меню
        itemDefaultValues.setOnAction(event -> handleDefaultSettings(null));
        settingsMenu.getItems().addAll(itemDefaultValues);

        // Открываем меню при клике на кнопку "Настройки"
        btnSettings.setOnAction(event -> {
            settingsMenu.show(btnSettings,
                    btnSettings.localToScreen(0, 0).getX() - 6, // Сдвиг на 6px влево для идеального выравнивания
                    btnSettings.localToScreen(0, 0).getY() + btnSettings.getHeight()
            );
        });

        // --- 3. Создаем выпадающий список для кнопки "Помощь" ---
        ContextMenu helpMenu = new ContextMenu();
        MenuItem itemGithub = new MenuItem("GitHub репозиторий");
        MenuItem itemBugReport = new MenuItem("Отправить отчёт об ошибке");
        MenuItem itemTutorial = new MenuItem("Обучение");
        MenuItem itemVersion = new MenuItem("Версия: 1.0.0");

        itemVersion.setDisable(true); // Отключенная версия по макету

        // Привязка действий
        itemTutorial.setOnAction(event -> openTutorial(null));
        itemGithub.setOnAction(event -> System.out.println("Открыть URL: https://github.com..."));
        itemBugReport.setOnAction(event -> System.out.println("Открыть окно ошибок..."));

        helpMenu.getItems().addAll(itemGithub, itemBugReport, itemTutorial, itemVersion);

        // Открываем меню при клике на кнопку "Помощь"
        btnHelp.setOnAction(event -> {
            helpMenu.show(btnHelp,
                    btnHelp.localToScreen(0, 0).getX() - 6, // Сдвиг на 6px влево для идеального выравнивания
                    btnHelp.localToScreen(0, 0).getY() + btnHelp.getHeight()
            );
        });

        // --- 4. Принудительно подключаем наш CSS к СЦЕНЕ (а не к меню) ---
        // Этот код берет текущую сцену приложения и добавляет ей файл стилей.
        // Всплывающие меню сами автоматически увидят его.
        btnSettings.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                String cssPath = Objects.requireNonNull(getClass().getResource("/com/ukondy/style.css")).toExternalForm();
                if (!newScene.getStylesheets().contains(cssPath)) {
                    newScene.getStylesheets().add(cssPath);
                }
            }
        });

        // --- 5. Закрытие меню при изменении размера окна ---
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
    }

    // Ваши существующие методы обработчиков:
    @FXML
    void handleDefaultSettings(Object event) { /* логика */ }

    @FXML
    void openTutorial(Object event) { /* логика */ }


    // === Обработчики событий меню ===
    @FXML
    void handleDefaultSettings(ActionEvent event) {
        // Логика сброса настроек на стандартные
    }

    @FXML
    void openGithub(ActionEvent event) {
        // Логика открытия ссылки на GitHub в браузере
    }

    @FXML
    void reportBug(ActionEvent event) {
        // Логика отправки отчета об ошибке
    }

    @FXML
    void openTutorial(ActionEvent event) {
        // Логика открытия обучения / справки
    }

    @FXML
    void handleExit(ActionEvent event) {
        // Корректное закрытие приложения
        System.exit(0);
    }

    // === Обработчики переключения режимов ===
    @FXML
    void switchToCustomMode(ActionEvent event) {
        if (modeCustom != null && modeCustom.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/custom_page/custom_page.fxml");
        }
    }

    @FXML
    void switchToPhotoMode(ActionEvent event) {
        if (modePhoto != null && modePhoto.isSelected()) {
            NavigationManager.navigateTo("/com/ukondy/photo_page/photo_page.fxml"); // Укажите имя вашего нового файла
        }
    }

    // === Обработчики ввода и кликов ===
    @FXML
    void selectFolder(MouseEvent event) {
        // 1. Создаем объект диалогового окна выбора папки
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку для сохранения");

        // 2. Указываем стартовую папку, если текущий путь в поле ввода уже существует
        String currentPath = inputSavePath.getText();
        if (currentPath != null && !currentPath.isEmpty()) {
            File currentDir = new File(currentPath);
            if (currentDir.exists() && currentDir.isDirectory()) {
                directoryChooser.setInitialDirectory(currentDir);
            }
        }

        // 3. Получаем текущее окно приложения (Stage) для блокировки заднего плана
        Stage stage = (Stage) inputSavePath.getScene().getWindow();

        // 4. Открываем проводник и ждем выбора пользователя
        File selectedDirectory = directoryChooser.showDialog(stage);

        // 5. Если пользователь выбрал папку, записываем ее путь в поле TextField
        if (selectedDirectory != null) {
            // Преобразуем путь со слэшами в единый формат (с косыми чертами вправо)
            String formattedPath = selectedDirectory.getAbsolutePath().replace("\\", "/");
            inputSavePath.setText(formattedPath);
        }
    }

    @FXML
    void handleBrowseFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку для сохранения");

        String currentPath = inputSavePath.getText();
        if (currentPath != null && !currentPath.isEmpty()) {
            File currentDir = new File(currentPath);
            if (currentDir.exists() && currentDir.isDirectory()) {
                directoryChooser.setInitialDirectory(currentDir);
            }
        }

        // Блокируем окно приложения на время выбора директории
        Stage stage = (Stage) inputSavePath.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            String formattedPath = selectedDirectory.getAbsolutePath().replace("\\", "/");
            inputSavePath.setText(formattedPath);
        }
    }

    // === Главное действие ===
    @FXML
    void handleExecute(ActionEvent event) {
        // Основная логика работы парсера при нажатии на кнопку "ВЫПОЛНИТЬ"
        String url = inputUrl.getText();
        String selectedSource = comboSource.getValue();
        String savePath = inputSavePath.getText();

        RadioButton selectedMode = (RadioButton) modeGroup.getSelectedToggle();
        RadioButton selectedFormat = (RadioButton) formatGroup.getSelectedToggle();

        if(urlError(url, lblUrlError, inputUrl)) return;

        OnSiteExecutor.execute(url, selectedSource, selectedFormat.getText(), savePath);

        System.out.println("url " + url);
        System.out.println(selectedSource);
        System.out.println(savePath);
        System.out.println(selectedMode);
        System.out.println(selectedFormat.getText());
    }
}

