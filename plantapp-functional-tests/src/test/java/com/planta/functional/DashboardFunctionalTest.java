package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DashboardFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL = System.getenv().getOrDefault(
            "BASE_URL",
            "http://localhost:8080"
    );

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        realizarLogin();
    }

    void realizarLogin() {
        driver.get(BASE_URL + "/web/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys("admin");

        driver.findElement(By.id("password"))
                .sendKeys("admin123");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @Test
    @DisplayName("Dashboard carga correctamente")
    void dashboardCargaCorrectamente() {

        driver.get(BASE_URL + "/web/dashboard");

        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("app-header")
                )
        );

        assertTrue(header.isDisplayed(),
                "El header del dashboard debería mostrarse");
    }

    @Test
    @DisplayName("Botón menú abre el sidebar")
    void botonMenuAbreSidebar() {

        driver.get(BASE_URL + "/web/dashboard");

        WebElement menuToggle = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("menu-toggle"))
        );

        menuToggle.click();

        WebElement sidebar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("sidebar"))
        );

        assertTrue(
                sidebar.getAttribute("class").contains("active"),
                "El sidebar debería activarse"
        );
    }

    @Test
    @DisplayName("Botón Ver Catálogo redirige al catálogo")
    void botonVerCatalogoRedirigeCorrectamente() {

        driver.get(BASE_URL + "/web/dashboard");

        WebElement btnCatalogo = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.partialLinkText("Catálogo")
                )
        );

        btnCatalogo.click();

        wait.until(ExpectedConditions.urlContains("/web/plantas/catalogo"));

        assertTrue(
                driver.getCurrentUrl().contains("/web/plantas/catalogo"),
                "Debe redirigir al catálogo"
        );
    }

    @Test
    @DisplayName("Botón Dashboard del header funciona")
    void botonDashboardHeaderFunciona() {

        driver.get(BASE_URL + "/web/dashboard");

        WebElement btnDashboard = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href*='dashboard']")
                )
        );

        btnDashboard.click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));

        assertTrue(
                driver.getCurrentUrl().contains("/dashboard"),
                "Debe mantenerse en dashboard"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
