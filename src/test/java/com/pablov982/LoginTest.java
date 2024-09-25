package com.pablov982;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    WebDriver driver;

    @BeforeEach
    public void configuracion (){
         driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    public void realizarLogin(String username, String password) {
        driver.get("https://saucedemo.com/");
        WebElement inputUser = driver.findElement(By.id("user-name"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        WebElement inputLogin = driver.findElement(By.id("login-button"));
        inputUser.sendKeys(username);
        inputPassword.sendKeys(password);
        inputLogin.click();
    }
    public void agregarProductosAlCarrito() {
        WebElement addToCar1 = driver.findElement(By.xpath("//div[@class='pricebar']//child::button[@data-test='add-to-cart-sauce-labs-backpack']"));
        addToCar1.click();
        WebElement addToCar2 = driver.findElement(By.xpath("//div[@class='pricebar']//child::button[@data-test='add-to-cart-sauce-labs-bike-light']"));
        addToCar2.click();
        WebElement shoppingCart = driver.findElement(By.xpath("//div[@class='header_label']/following::div/child::a[@data-test='shopping-cart-link']"));
        shoppingCart.click();
    }
    public void checkout(String username, String lastName, String postalCode) {
        WebElement checkout = driver.findElement(By.xpath("//button[@id='checkout']"));
        checkout.click();
        WebElement inputFirstName = driver.findElement(By.id("first-name"));
        WebElement inputLastName = driver.findElement(By.id("last-name"));
        WebElement postCode = driver.findElement(By.id("postal-code"));

        inputFirstName.sendKeys(username);
        inputLastName.sendKeys(lastName);
        postCode.sendKeys(postalCode);
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

    }
    public Double validatePrice() {
        WebElement price1 = driver.findElement(By.xpath("//div[@data-test='inventory-item-price' and text()='29.99']"));
        Double primerValor = Double.parseDouble(price1.getText().replace("$", ""));
        WebElement price2 = driver.findElement(By.xpath("//div[@data-test='inventory-item-price' and text()='9.99']"));
        Double segundoValor = Double.parseDouble(price2.getText().replace("$", ""));
        Double sumaSubtotal = primerValor + segundoValor;
        return sumaSubtotal;
    }
    @Test
    public void loginExitoso() {
        realizarLogin("standard_user", "secret_sauce");
        WebElement pageTitle = driver.findElement(By.xpath("//div[@id='header_container']/div[@data-test='secondary-header']/span"));
        assertEquals("Products", pageTitle.getText());
    }
    @Test
    public void seleccionarProductos() {
        realizarLogin("standard_user", "secret_sauce");
        agregarProductosAlCarrito();
        WebElement countProducts = driver.findElement(By.xpath("//div[@class='header_label']/following::div/child::a[@data-test='shopping-cart-link']/child::span"));
        assertEquals("2",countProducts.getText());
    }
    @Test
    public void checkoutTest() {
        realizarLogin("standard_user", "secret_sauce");
        agregarProductosAlCarrito();
        checkout("Juan","Pablo","0000");
        WebElement Title = driver.findElement(By.xpath("//div[@data-test='header-container']//descendant::span[@data-test='title']"));
        assertEquals("Checkout: Overview",Title.getText());

    }
    @Test
    public void validatePriceTest() {
        realizarLogin("standard_user", "secret_sauce");
        agregarProductosAlCarrito();
        checkout("Juan","Pablo","0000");
        WebElement subTotal = driver.findElement(By.xpath("//div[@data-test='subtotal-label']"));
        Double subTotalValor = Double.parseDouble(subTotal.getText().replaceAll("Item total: \\$|\\s+", ""));
        Double sumaSubtotal = validatePrice();
        assertEquals(subTotalValor,sumaSubtotal);

    }
    @Test
    public void finalizarCheckout() {
        realizarLogin("standard_user", "secret_sauce");
        agregarProductosAlCarrito();
        checkout("Juan","Pablo","0000");
        WebElement finishBtn = driver.findElement(By.xpath("//div[@class='cart_footer']/child::button[@data-test='finish']"));
        finishBtn.click();
        WebElement completeText = driver.findElement(By.xpath("//span[@data-test='title']"));
        assertEquals("Checkout: Complete!",completeText.getText());

    }
    @AfterEach
    public void cierra (){
        driver.close();
    }

}
