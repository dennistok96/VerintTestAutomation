package stepDefinations;

import basepackage.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import pageobjectmodel.HomePage;

import javax.swing.*;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

public class StepDefinations extends Base {
    @Given("Initialize the browser with chrome")
    public void initialize_the_browser_with_chrome() throws IOException {
        driver = initializeDriver();
    }
    @Given("Navigate to {string} site")
    public void navigate_to_site(String string) {
        driver.get(string);
    }
    @When("User click on search button")
    public void user_click_on_search_button() {
        HomePage hp= new HomePage(driver);
        explicitWaitElementVisible(hp.getHeaderLabel());
        hp.clickSearchButton();
    }
    @When("Input {string} in the search box")
    public void input_in_the_search_box(String string) {
        HomePage hp= new HomePage(driver);
        hp.setSearchBoxAndEnter(string);
        explicitWaitElementPresent(hp.getElementResultLabel(),string);
    }
    @Then("Verify that it shows all articles title with {string} keyword")
    public void verify_that_it_shows_all_articles_title_with_keyword(String string) {
        HomePage hp= new HomePage(driver);

        boolean staleElement=true;
        while(staleElement) {
            try {
                explicitWaitElementClickable(hp.getViewAllButton());

                WebElement ele= driver.findElement( By.xpath("(//a[@class='search__view-all button button--border'][normalize-space()='View All'])[2]"));
                scrollIntoView(driver,ele);
                hp.clickViewAllButton();
                staleElement = false;
            } catch (StaleElementReferenceException staleException) {
                staleException.printStackTrace();
                staleElement = true;
            }
        }

        String[] splitString= string.split(" ");
        StringBuilder searchedString=new StringBuilder(".*");
        for(String s:splitString){
            searchedString.append(s);
            searchedString.append(".*");
        }

        boolean isNextPageExists= true;

        do{
            isNextPageExists= hp.validateIsNextPageButtonExists();
            List<WebElement> listArticles= hp.getListArticlesHeader();
            if(listArticles.size() > 0){
                for(int i=0;i<listArticles.size();i++){
                    WebElement element= listArticles.get(i);
                    System.out.println(element.getText());
                    if(!element.getText().toLowerCase().matches(searchedString.toString())){
                        System.out.println("Fail="+element.getText());
                        Assert.assertTrue(false);
                    }
                }

                if(isNextPageExists){
                    scrollIntoView(driver,hp.getNextPageButton());
                    hp.clickNextPageButton();
                }

            }
        }while(isNextPageExists);


    }
}
