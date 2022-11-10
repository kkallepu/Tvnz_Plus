import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageObjects {

    @FindBy(how = How.LINK_TEXT, using = "Login")
    private WebElement Login;
    @FindBy(how = How.ID, using = "email")
    private WebElement email;
    @FindBy(how = How.ID, using = "password")
    private WebElement password;
    @FindBy(how = How.XPATH, using = "Login")
    private WebElement submit;

}
