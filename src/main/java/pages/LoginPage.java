package pages;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigate() {
        driver.get(ConfigReader.getBaseUrl());
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
    }

    public WebElement getUsernameInput() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
    }

    public WebElement getPasswordInput() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
    }

    public WebElement getLoginButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    public void login(String username, String password) {
        getUsernameInput().clear();
        getUsernameInput().sendKeys(username);
        getPasswordInput().clear();
        getPasswordInput().sendKeys(password);
        getLoginButton().click();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.urlContains("/inventory.html"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
