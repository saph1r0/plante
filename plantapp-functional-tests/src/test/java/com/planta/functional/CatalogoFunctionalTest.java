package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // CI / Jenkins
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

        // BASE_URL configurable
        baseUrl = System.getenv().getOrDefault(
                "BASE_URL",
                "http://localhost:8080"
        );
    }

    @Test
    @DisplayName("El catálogo botánico carga correctamente")
    void catalogoDebeCargar() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement titulo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("hero-title"))
        );

        assertTrue(titulo.getText().contains("Catálogo Botánico"));
    }

    @Test
    @DisplayName("Se muestran plantas al cargar el catálogo")
    void debeMostrarPlantas() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        List<WebElement> plantas = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.className("plant-card")
                )
        );

        assertFalse(plantas.isEmpty(), "Deberían mostrarse plantas en el catálogo");
    }

    @Test
    @DisplayName("La búsqueda filtra plantas por nombre")
    void busquedaDebeFiltrarPlantas() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("plant-search-input"))
        );

        search.sendKeys("rosa");

        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.className("plant-card"), 0
                )
        );

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        assertFalse(plantas.isEmpty());
    }

    @Test
    @DisplayName("Si no hay resultados se muestra mensaje correspondiente")
    void noResultadosDebeMostrarMensaje() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("plant-search-input"))
        );

        search.sendKeys("xxxxxxxx");

        WebElement noResults = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("no-results"))
        );

        assertTrue(noResults.isDisplayed());
    }

    @Test
    @DisplayName("El botón reset restaura todas las plantas")
    void resetBusquedaDebeRestaurarCatalogo() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("plant-search-input"))
        );
        search.sendKeys("xxxxx");

        WebElement resetBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("reset-search"))
        );
        resetBtn.click();

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        assertFalse(plantas.isEmpty());
    }

    @Test
    @DisplayName("El filtro Interior funciona")
    void filtroInteriorDebeAplicarse() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement filtroInterior = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[data-filter='interior']")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", filtroInterior);

        wait.until(
                ExpectedConditions.presenceOfElementLocated(By.className("plant-card"))
        );

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        assertFalse(plantas.isEmpty());
    }

    @Test
    @DisplayName("Al hacer click en una planta se abre el modal")
    void modalDebeAbrirseYCerrarse() {
        driver.get(baseUrl + "/web/plantas/catalogo");

        WebElement planta = wait.until(
                ExpectedConditions.elementToBeClickable(By.className("plant-card"))
        );
        planta.click();

        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("plant-modal-overlay"))
        );

        assertTrue(modal.isDisplayed());

        driver.findElement(By.className("plant-modal-close")).click();

        wait.until(ExpectedConditions.invisibilityOf(modal));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
