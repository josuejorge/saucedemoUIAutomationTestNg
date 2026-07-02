package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productName    = By.cssSelector(".inventory_details_name");
    private final By productPrice   = By.cssSelector(".inventory_details_price");
    private final By productImage   = By.cssSelector("img.inventory_details_img");
    private final By addToCart      = By.cssSelector("[data-test^='add-to-cart']");
    private final By backButton     = By.cssSelector("[data-test='back-to-products']");
    private final By cartBadge      = By.cssSelector(".shopping_cart_badge");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
    }

    public boolean isImageDisplayed() {
        WebElement img = wait.until(ExpectedConditions.visibilityOfElementLocated(productImage));
        return img.isDisplayed();
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public void goBack() {
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
    }
}
