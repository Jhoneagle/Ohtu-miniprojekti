package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

public class Stepdefs {
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";

    @Given("^front page is selected$")
    public void front_page_selected() throws Throwable {
        driver.get(baseUrl);
    }

    @When("^list all tips clicked$")
    public void list_all_tips_clicked() throws Throwable {
        WebElement element = driver.findElement(By.name("toVinkit"));
        element.click();
    }

    @Then("^list all view is open$")
    public void list_all_view_is_open() throws Throwable {
        pageHasContent("Lukuvinkit listana");
    }

    @When("^new tip is clicked$")
    public void new_tip_is_clicked() throws Throwable {
        WebElement element = driver.findElement(By.name("toNewVinkki"));
        element.click();
    }

    @Then("^new tip view is open$")
    public void new_tip_view_is_open() throws Throwable {
        pageHasContent("uusi vinkki");
    }

    @Given("^new tip view is selected$")
    public void new_tip_view_is_selected() throws Throwable {
        driver.get(baseUrl + "/newVinkki");
    }

    @When("^otsikko \"([^\"]*)\", tekija \"([^\"]*)\", kuvaus \"([^\"]*)\", linkki \"([^\"]*)\" are given$")
    public void otsikko_kirjoittaja_tyyppi_are_given(String otsikko, String tekija, String kuvaus, String linkki) throws Throwable {
        createTip(otsikko, tekija, kuvaus, linkki);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void createTip(String otsikko, String tekija, String kuvaus, String linkki) {
        assertTrue(driver.getPageSource().contains("uusi vinkki"));
        WebElement element = driver.findElement(By.name("otsikko"));
        element.sendKeys(otsikko);
        element = driver.findElement(By.name("tekija"));
        element.sendKeys(tekija);
        element = driver.findElement(By.name("kuvaus"));
        element.sendKeys(kuvaus);
        element = driver.findElement(By.name("linkki"));
        element.sendKeys(linkki);
        element = driver.findElement(By.name("addVinkki"));
        element.submit();
    }
    
    /*
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    private void createUserWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(password);
        element = driver.findElement(By.name("signup"));
        element.submit();
    }

    private void createUserUnValidConfirm(String username, String password) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("");
        element = driver.findElement(By.name("signup"));
        element.submit();
    }
    */
}
