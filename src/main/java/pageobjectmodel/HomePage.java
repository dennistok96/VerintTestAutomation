package pageobjectmodel;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class HomePage {
    WebDriver driver;

    @FindBy(xpath = "//ul[@class='site-header__buttons flex unstyled-list']/li[1]/following-sibling::li[6]")
    private WebElement searchBtn;

    @FindBy(id ="search-input--desktop")
    private WebElement searchBox;

    @FindBy(xpath= "(//span[@class='search__query'])[2]")
    private WebElement queryResultLabel;

    By viewAllButton= By.xpath("(//a[@class='search__view-all button button--border'][normalize-space()='View All'])[2]");

    @FindBy(xpath = "//a[@class='gray-card__link pseudo-link']/span[@class='gray-card__heading h5']")
    private List<WebElement> listArticlesHeader;

    @FindBy(xpath = "//*[contains(@class,'arrow--next')]")
    private WebElement nextPageButton;

    By headerLabel= By.xpath("//h1[contains(text(),'Boundless Possibilities')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickSearchButton(){
        searchBtn.click();
    }

    public void setSearchBoxAndEnter(String inputKeyword){
      searchBox.sendKeys(inputKeyword);
      searchBox.sendKeys(Keys.ENTER);
    }

    public WebElement getElementResultLabel(){
        return queryResultLabel;
    }

    public By getHeaderLabel(){
        return headerLabel;
    }

    public void clickViewAllButton(){
        driver.findElement(viewAllButton).click();
    }

    public List<WebElement> getListArticlesHeader(){
        return listArticlesHeader;
    }

    public boolean validateIsNextPageButtonExists(){
        if(nextPageButton.getAttribute("class").contains("arrow--disabled"))
            return false;

        return true;
    }

    public void clickNextPageButton(){
        nextPageButton.click();
    }

    public By getViewAllButton(){
        return viewAllButton;
    }

    public WebElement getNextPageButton(){
        return nextPageButton;
    }

    public void verifyArticlesHeader(WebElement element,String searchedString){
        if(!element.getText().toLowerCase().matches(searchedString)){
            System.out.println("Fail="+element.getText());
            Assert.assertTrue(false);
        }
    }


}
