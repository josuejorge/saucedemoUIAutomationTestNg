package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;

public class CheckoutTest extends BaseTest {

    @BeforeMethod
    public void loginEAdicionarItem() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.checkout();
    }

    @Test(description = "Validar checkout sem preencher informações")
    public void validarCheckoutSemInformacao() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickContinue();

        Assert.assertTrue(
                checkoutPage.getErrorMessage().contains("First Name is required"),
                "Deveria exibir erro de campo obrigatório.");
    }

    @Test(description = "Validar checkout sem sobrenome")
    public void validarCheckoutSemSobrenome() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInfo("John", "", "");
        checkoutPage.clickContinue();

        Assert.assertTrue(
                checkoutPage.getErrorMessage().contains("Last Name is required"),
                "Deveria exibir erro de sobrenome obrigatório.");
    }

    @Test(description = "Validar checkout sem CEP")
    public void validarCheckoutSemCep() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInfo("John", "Doe", "");
        checkoutPage.clickContinue();

        Assert.assertTrue(
                checkoutPage.getErrorMessage().contains("Postal Code is required"),
                "Deveria exibir erro de CEP obrigatório.");
    }

    @Test(description = "Validar compra completa com sucesso")
    public void validarCompraCompleta() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertEquals(checkoutPage.getCompleteHeader(), "Thank you for your order!",
                "Deveria exibir mensagem de confirmação após finalizar a compra.");
    }

    @Test(description = "Validar cancelar compra e voltar ao carrinho")
    public void validarCancelarCompra() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickCancel();

        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"),
                "Deveria ter voltado para o carrinho após cancelar.");
    }
}
