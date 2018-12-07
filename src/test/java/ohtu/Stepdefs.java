package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.sql.Date;
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

    @When("^submitted read of the tip$")
    public void submitted_read_of_the_tip() throws Throwable {
        WebElement element = driver.findElement(By.name("luettuButton"));
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
    public void otsikko_tekija_kuvaus_linkki_are_given(String otsikko, String tekija, String kuvaus, String linkki) throws Throwable {
        createTip(otsikko, tekija, kuvaus, linkki, "");
    }
    
    @When("^video \"([^\"]*)\" and tags \"([^\"]*)\" are given$")
    public void video_and_tags_are_given(String video, String tags) throws Throwable {
        createTip("tagsTest", "automatic", "test", video, tags);
    }
    
    @When("^wanted tags \"([^\"]*)\" typed and search button clicked$")
    public void tags_give_and_search_clicked(String tags) throws Throwable {
        search(tags);
    }
    
    @Given("^the page displaying all tips is selected$")
    public void the_page_displaying_all_tips_is_selected() throws Throwable {
        driver.get(baseUrl+"/vinkit");
    }

    @When("^heading of the chosen tip is clicked$")
    public void heading_of_the_chosen_tip_is_clicked() throws Throwable {
        WebElement element = driver.findElement(By.name("otsikko"));
        element.click();
    }

    @When("^heading of the tip with otsikko \"([^\"]*)\" is clicked$")
    public void heading_of_the_tip_with_otsikko_is_clicked(String otsikko) throws Throwable {
        WebElement element = driver.findElement(By.xpath("//*[text() = '" + otsikko + "']")).findElement(By.xpath("./.."));
        element.click();
    }
    
    @Then("^single tip details are displayed on a separate page$")
    public void single_tip_details_are_displayed_on_a_separate_page() throws Throwable {
        pageHasContent("testi");
    }

    @Then("^automatic tag can be found$")
    public void automatic_tag_can_be_found() throws Throwable {
        pageHasContent("test,amazing,video");
    }

    @Then("^result gives found tip \"([^\"]*)\" with tags asked$")
    public void result_gives_found_tips_acording_to_tags(String tipFound) throws Throwable {
        pageHasContent(tipFound);
    }
    
    @Then("^tip is signed read and have returned list view$")
    public void tip_is_signed_read_and_have_returned_list_view() throws Throwable {
        pageHasContent("Lukuvinkit listana");
        pageHasContent(new Date(System.currentTimeMillis()).toString());
    }
    
    @When("^pressed the edit button$")
    public void pressed_the_edit_button() throws Throwable {
        WebElement element = driver.findElement(By.name("editButton"));
        element.click();
    }

    @Then("^view for editing tip is opened$")
    public void view_for_editing_tip_is_opened() throws Throwable {
        pageHasContent("Muokkaa vinkkiä");
    }

    @Given("^the tips otsikko \"([^\"]*)\" and selected the tip$")
    public void the_tips_otsikko_and_selected_the_tip(String otsikko) throws Throwable {
        driver.get(baseUrl + "/newVinkki");
        createTip(otsikko, "test", "test", "test", "test");
        
        heading_of_the_tip_with_otsikko_is_clicked(otsikko);
    }

    @When("^details been chanced with \"([^\"]*)\"$")
    public void details_been_chanced_with(String value) throws Throwable {
        editTip(value);
    }

    @Then("^new details with \"([^\"]*)\" can be seen in the \"([^\"]*)\" tips info$")
    public void new_details_can_be_seen_in_the_tips_info(String value, String otsikko) throws Throwable {
        heading_of_the_tip_with_otsikko_is_clicked(otsikko + value);
        
        pageHasContent("Kommentit");
        pageHasContent("test" + value);
        pageHasContent(otsikko + value);
    }

    @When("^details nick \"([^\"]*)\" and content \"([^\"]*)\" for comment are given$")
    public void details_nick_and_content_for_comment_are_given(String nick, String content) throws Throwable {
        addComment(nick, content);
    }

    @Then("^tip has new comment with nick \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void tip_has_new_comment_with_nick_and_content(String nick, String content) {
        pageHasContent("Kirjoittanut");
        pageHasContent(nick);
        pageHasContent("Lähetetty");
        pageHasContent(content);
    }
    
    @When("^isbn \"([^\"]*)\" is given$")
    public void isbn_is_given(String isbn) throws Throwable {
        driver.get(baseUrl + "/newVinkki");
        
        WebElement element = driver.findElement(By.name("isbn"));
        element.sendKeys(isbn);
        
        element = driver.findElement(By.name("haeISBN"));
        element.submit();
    }

    @Then("^fields are occupied$")
    public void fields_are_occupied() throws Throwable {
        pageHasContent("uusi vinkki");
        createFormContent(true);
    }

    @Then("^fields are not occupied$")
    public void fields_are_not_occupied() throws Throwable {
        pageHasContent("uusi vinkki");
        createFormContent(false);
    }
    
    
    
    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
    
    private void createFormContent(boolean contains) {
        WebElement element = driver.findElement(By.name("otsikko"));
        assertEquals(contains, !element.getText().isEmpty());
        
        System.out.println("otsikko: " + element.getText());
        System.out.println("elementti: " + element);
        
        element = driver.findElement(By.name("tekija"));
        assertEquals(contains, !element.getText().isEmpty());
        
        element = driver.findElement(By.name("kuvaus"));
        assertEquals(contains, !element.getText().isEmpty());
        
        element = driver.findElement(By.name("linkki"));
        assertEquals(contains, !element.getText().isEmpty());
        
        element = driver.findElement(By.name("tagit"));
        assertEquals(contains, !element.getText().isEmpty());
    }

    private void createTip(String otsikko, String tekija, String kuvaus, String linkki, String tags) {
        assertTrue(driver.getPageSource().contains("uusi vinkki"));
        
        WebElement element = driver.findElement(By.name("otsikko"));
        element.sendKeys(otsikko);
        element = driver.findElement(By.name("tekija"));
        element.sendKeys(tekija);
        element = driver.findElement(By.name("kuvaus"));
        element.sendKeys(kuvaus);
        element = driver.findElement(By.name("linkki"));
        element.sendKeys(linkki);
        element = driver.findElement(By.name("tagit"));
        element.sendKeys(tags);
        element = driver.findElement(By.name("addVinkki"));
        element.submit();
    }
    
    private void addComment(String nick, String content) {
        assertTrue(driver.getPageSource().contains("Syötä uusi kommentti"));
        
        WebElement element = driver.findElement(By.name("nikki"));
        element.sendKeys(nick);
        element = driver.findElement(By.name("content"));
        element.sendKeys(content);
        
        element = driver.findElement(By.name("kommentoiButton"));
        element.submit();
    }
    
    private void editTip(String with) {
        assertTrue(driver.getPageSource().contains("uusi vinkki"));
        
        WebElement element = driver.findElement(By.name("otsikko"));
        element.sendKeys(with);
        element = driver.findElement(By.name("tekija"));
        element.sendKeys(with);
        element = driver.findElement(By.name("kuvaus"));
        element.sendKeys(with);
        element = driver.findElement(By.name("linkki"));
        element.sendKeys(with);
        element = driver.findElement(By.name("action"));
        element.submit();
    }
    
    private void search(String tags) {
        WebElement element = driver.findElement(By.name("etsi"));
        element.sendKeys(tags);
        element = driver.findElement(By.name("action"));
        element.submit();
    }
}
