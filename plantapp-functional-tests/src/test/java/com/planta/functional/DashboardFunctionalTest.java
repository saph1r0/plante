package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DashboardFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Si estamos en Jenkins o CI/CD
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-extensions");
            options.addArguments("--remote-allow-origins=*");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    @Test
    @DisplayName("Dashboard carga correctamente")
    void dashboardCargaCorrectamente() {

        driver.get("http://localhost:8080/web/dashboard");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("app-header")
        ));

        assertTrue(
                driver.findElement(By.className("app-header")).isDisplayed(),
                "El header del dashboard debería mostrarse"
        );
    }

    @Test
    @DisplayName("Botón menú abre el sidebar")
    void botonMenuAbreSidebar() {

        driver.get("http://localhost:8080/web/dashboard");

        WebElement menuToggle = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("menu-toggle"))
        );

        menuToggle.click();

        WebElement sidebar = driver.findElement(By.id("sidebar"));

        assertTrue(
                sidebar.getAttribute("class").contains("active"),
                "El sidebar debería activarse al hacer click"
        );
    }

    @Test
    @DisplayName("Botón Ver Catálogo Completo redirige al catálogo")
    void botonVerCatalogoRedirigeCorrectamente() {

        driver.get("http://localhost:8080/web/dashboard");

        WebElement btnCatalogo = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.linkText("Ver Catálogo Completo")
                )
        );

        btnCatalogo.click();

        wait.until(ExpectedConditions.urlContains("/web/plantas/catalogo"));

        assertTrue(
                driver.getCurrentUrl().contains("/web/plantas/catalogo"),
                "Debería redirigir al catálogo"
        );
    }

    @Test
    @DisplayName("Botón Dashboard del header redirige al dashboard")
    void botonDashboardHeaderFunciona() {

        driver.get("http://localhost:8080/web/dashboard");

        WebElement btnDashboard = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='/web/plantas/dashboard2']")
                )
        );

        btnDashboard.click();

        wait.until(ExpectedConditions.urlContains("/dashboard2"));

        assertTrue(
                driver.getCurrentUrl().contains("dashboard2"),
                "Debería redirigir al dashboard del usuario"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
