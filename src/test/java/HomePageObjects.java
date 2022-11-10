import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class HomePageObjects {

    @FindBy(how = How.XPATH, using = "(//a[@class='mainImage middleImage'])[1]")
    private WebElement heroImage;
    @FindBy(how = How.XPATH, using = "//h2[.='Latest stories']/../../../..//div[contains(@class,'storyContainer')]")
    private List<WebElement> storys;
    public boolean checkHeroImageDispalyed(){
        return heroImage.isDisplayed();
    }

    public void clickFirstStoryFromLatest(){
        storys.get(0).click();

    }

}
