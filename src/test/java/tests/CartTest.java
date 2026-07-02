package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;

public class CartTest extends BaseTest {

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(description = "Validar que item adicionado aparece no carrinho")
    public void validarItemNoCarrinho() {
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertFalse(cartPage.isCartEmpty(),
                "O carrinho deveria conter ao menos um item.");
    }

    @Test(description = "Validar badge do carrinho ao adicionar item")
    public void validarBadgeDoCarrinho() {
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();

        Assert.assertEquals(homePage.getCartBadgeCount(), "1",
                "O badge do carrinho deveria exibir 1 após adicionar um item.");
    }

    @Test(description = "Validar remover item do carrinho")
    public void validarRemoverItemDoCarrinho() {
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.removeFirstItem();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "O carrinho deveria estar vazio após remover o item.");
    }

    @Test(description = "Validar continuar comprando a partir do carrinho")
    public void validarContinuarComprando() {
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.continueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Deveria ter voltado para a página de produtos.");
    }
}
