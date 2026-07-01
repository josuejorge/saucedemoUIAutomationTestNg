package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Validar login com sucesso")
    public void validarLoginComSucesso() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "Esperava entrar na aplicação após login, mas a página de login ainda está visível.");
    }
}
