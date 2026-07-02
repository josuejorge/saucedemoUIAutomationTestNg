package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.util.List;

public class HomeTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
        homePage = new HomePage(driver);
    }

    @Test(description = "Validar que a home carregou com sucesso")
    public void validarHomeCarregouComSucesso() {
        Assert.assertTrue(homePage.getInventoryList().isDisplayed(),
                "A lista de produtos deveria estar visível na home.");
    }

    @Test(description = "Validar quantidade de produtos exibidos")
    public void validarQuantidadeDeProdutos() {
        Assert.assertEquals(homePage.getInventoryItems().size(), 6,
                "Deveriam ser exibidos 6 produtos na home.");
    }

    @Test(description = "Validar badge ao adicionar item ao carrinho")
    public void validarAdicionarItemAoCarrinho() {
        homePage.addFirstItemToCart();

        Assert.assertEquals(homePage.getCartBadgeCount(), "1",
                "O badge do carrinho deveria exibir 1 após adicionar um produto.");
    }

    @Test(description = "Validar remover item da home")
    public void validarRemoverItemDaHome() {
        homePage.addFirstItemToCart();
        homePage.removeFirstItemFromCart();

        Assert.assertFalse(homePage.isCartBadgeVisible(),
                "O badge do carrinho deveria desaparecer após remover o produto.");
    }

    @Test(description = "Validar ordenação por nome A → Z")
    public void validarOrdenacaoNomeAZ() {
        homePage.selectSort("az");
        List<String> names = homePage.getProductNames();

        Assert.assertEquals(names.get(0), "Sauce Labs Backpack",
                "O primeiro produto na ordenação A→Z deveria ser 'Sauce Labs Backpack'.");
    }

    @Test(description = "Validar ordenação por nome Z → A")
    public void validarOrdenacaoNomeZA() {
        homePage.selectSort("za");
        List<String> names = homePage.getProductNames();

        Assert.assertEquals(names.get(0), "Test.allTheThings() T-Shirt (Red)",
                "O primeiro produto na ordenação Z→A deveria ser 'Test.allTheThings() T-Shirt (Red)'.");
    }

    @Test(description = "Validar ordenação por preço menor → maior")
    public void validarOrdenacaoPrecoMenorMaior() {
        homePage.selectSort("lohi");
        List<Double> prices = homePage.getProductPrices();

        Assert.assertEquals(prices.get(0), 7.99,
                "O produto mais barato deveria ser o primeiro na ordenação de preço crescente.");
    }

    @Test(description = "Validar ordenação por preço maior → menor")
    public void validarOrdenacaoPrecoMaiorMenor() {
        homePage.selectSort("hilo");
        List<Double> prices = homePage.getProductPrices();

        Assert.assertEquals(prices.get(0), 49.99,
                "O produto mais caro deveria ser o primeiro na ordenação de preço decrescente.");
    }
}
