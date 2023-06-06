package com.example.demo.UITests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import com.example.demo.UITests.utils.BrowserFactory;
import com.example.demo.UITests.utils.BrowserTypesEnum;

import java.awt.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalUITest {
    private static Browser browser;

    private static Page currentPage;

    BrowserContext context;

    @BeforeAll
    public static void setUp() {
//        playwright = Playwright.create();
//        browser = playwright.firefox().launch(
//                new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100)
//        );
        browser = BrowserFactory.create(BrowserTypesEnum.CHROMIUM, false, 100);
//        currentPage = browser.newPage();
    }
    @BeforeEach
    void createContext(){
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        context = browser.newContext();
    }
    @AfterEach
    void closeContext() {
        context.close();
    }
        @Test
    public void testExample(){
        Page uploadPage = context.newPage();
        uploadPage.navigate("http://localhost:4200");
        PlaywrightAssertions.assertThat(uploadPage).hasTitle("VMware Talent Boost");
        //uploadPage.
        uploadPage.pause();
    }
}
