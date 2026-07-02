package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By inventoryList    = By.cssSelector(".inventory_list");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private final By removeButtons    = By.cssSelector("[data-test^='remove']");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartLink         = By.cssSelector(".shopping_cart_link");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getInventoryList() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }

    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons)).click();
    }

    public void removeFirstItemFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButtons)).click();
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}
