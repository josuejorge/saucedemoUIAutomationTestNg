package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

public class CardsTest extends BaseTest {

    private ProductPage productPage;

    @BeforeMethod
    public void loginEAbrirProduto() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        new HomePage(driver).clickFirstProduct();
        productPage = new ProductPage(driver);
    }

    @Test(description = "Validar que página de detalhe do produto abriu")
    public void validarAbrirDetalhesDoProduto() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory-item.html"),
                "A URL deveria conter '/inventory-item.html' após abrir o produto.");
    }

    @Test(description = "Validar nome do produto na página de detalhe")
    public void validarNomeDoProduto() {
        Assert.assertFalse(productPage.getName().isEmpty(),
                "O nome do produto não deveria estar vazio na página de detalhe.");
    }

    @Test(description = "Validar preço do produto na página de detalhe")
    public void validarPrecoDoProduto() {
        Assert.assertTrue(productPage.getPrice().contains("$"),
                "O preço do produto deveria conter o símbolo '$'.");
    }

    @Test(description = "Validar imagem do produto na página de detalhe")
    public void validarImagemDoProduto() {
        Assert.assertTrue(productPage.isImageDisplayed(),
                "A imagem do produto deveria estar visível na página de detalhe.");
    }

    @Test(description = "Validar adicionar produto ao carrinho pela página de detalhe")
    public void validarAdicionarAoCarrinhoNaDetalhes() {
        productPage.addToCart();

        Assert.assertEquals(productPage.getCartBadgeCount(), "1",
                "O badge do carrinho deveria exibir 1 após adicionar o produto na página de detalhe.");
    }

    @Test(description = "Validar botão voltar para a home")
    public void validarVoltarParaHome() {
        productPage.goBack();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Deveria ter voltado para a home após clicar em 'Back to products'.");
    }
}
