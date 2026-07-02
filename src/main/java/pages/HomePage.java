package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By inventoryList    = By.cssSelector(".inventory_list");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By productNames     = By.cssSelector(".inventory_item_name");
    private final By productPrices    = By.cssSelector(".inventory_item_price");
    private final By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private final By removeButtons    = By.cssSelector("[data-test^='remove']");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartLink         = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown     = By.cssSelector("[data-test='product-sort-container']");

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

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(productNames)).click();
    }

    public void selectSort(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(dropdown).selectByValue(value);
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
        return driver.findElements(productNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
        return driver.findElements(productPrices).stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .collect(Collectors.toList());
    }
}
