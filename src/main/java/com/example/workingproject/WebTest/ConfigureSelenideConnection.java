package com.example.workingproject.WebTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Data
public class ConfigureSelenideConnection {
    private Boolean holdBrowserAfterTests = true;
    private Integer timeout = 6000;
    private String browser = "chrome";
    private String url = "http://localhost:8080";


    @Before
    public void setUp() {
        Configuration.webdriverLogsEnabled = true;
//        Configuration.holdBrowserOpen = this.holdBrowserAfterTests;
        Configuration.browser = this.browser;
        Configuration.timeout = this.timeout;

    }

    @Test
    public void createConnection() {
//        Configuration.holdBrowserOpen = true;
        Selenide.open(url);
    }

    @After
    public void tearDown() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
