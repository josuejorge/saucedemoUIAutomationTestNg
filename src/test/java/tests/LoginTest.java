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
                "Esperava entrar na aplicação após login com sucesso.");
    }

    @Test(description = "Validar login com credenciais inválidas")
    public void validarLoginComCredenciaisInvalidas() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getInvalidUsername(), ConfigReader.getInvalidPassword());

        Assert.assertFalse(loginPage.isLoginSuccessful(),
                "Login não deveria ter sido realizado com credenciais inválidas.");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Mensagem de erro incorreta para credenciais inválidas.");
    }

    @Test(description = "Validar login com campos vazios")
    public void validarLoginComCamposVazios() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login("", "");

        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username is required",
                "Mensagem de erro incorreta para campos vazios.");
    }

    @Test(description = "Validar login apenas com senha (usuário vazio)")
    public void validarLoginComUsuarioVazio() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login("", ConfigReader.getPassword());

        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Username is required",
                "Mensagem de erro incorreta para usuário vazio.");
    }

    @Test(description = "Validar login apenas com usuário (senha vazia)")
    public void validarLoginComSenhaVazia() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getUsername(), "");

        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Password is required",
                "Mensagem de erro incorreta para senha vazia.");
    }

    @Test(description = "Validar login com usuário bloqueado")
    public void validarLoginComUsuarioBloqueado() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getLockedUsername(), ConfigReader.getPassword());

        Assert.assertFalse(loginPage.isLoginSuccessful(),
                "Usuário bloqueado não deveria conseguir fazer login.");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Sorry, this user has been locked out"),
                "Mensagem de erro incorreta para usuário bloqueado.");
    }
}
