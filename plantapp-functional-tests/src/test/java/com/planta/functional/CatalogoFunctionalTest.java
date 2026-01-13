package com.planta.functional;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("El catálogo botánico carga correctamente")
    void catalogoDebeCargar() {
        driver.get("http://localhost:8080/web/plantas/catalogo");

        WebElement titulo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("hero-title"))
        );

        assertTrue(titulo.getText().contains("Catálogo Botánico"));
    }

    @Test
    @DisplayName("Se muestran plantas al cargar el catálogo")
    void debeMostrarPlantas() {
        driver.get("http://localhost:8080/web/plantas/catalogo");

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
        driver.get("http://localhost:8080/web/plantas/catalogo");

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("plant-search-input"))
        );

        search.sendKeys("rosa");

        wait.until(ExpectedConditions
                .numberOfElementsToBeMoreThan(By.className("plant-card"), 0));

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        assertFalse(plantas.isEmpty(), "Deberían mostrarse plantas filtradas");
    }


    @Test
    @DisplayName("Si no hay resultados se muestra mensaje correspondiente")
    void noResultadosDebeMostrarMensaje() {
        driver.get("http://localhost:8080/web/plantas/catalogo");

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
        driver.get("http://localhost:8080/web/plantas/catalogo");

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
        driver.get("http://localhost:8080/web/plantas/catalogo");

        WebElement filtroInterior = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-filter='interior']")
                )
        );

        filtroInterior.click();

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        assertFalse(plantas.isEmpty());
    }

    @Test
    @DisplayName("Al hacer click en una planta se abre el modal")
    void modalDebeAbrirseYCerrarse() {
        driver.get("http://localhost:8080/web/plantas/catalogo");

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
        driver.quit();
    }
}