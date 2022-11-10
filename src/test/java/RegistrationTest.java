import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.example.day12.TestParameterising;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {

    HomePageObjects homePageObjects;
    public String RandomAlphabeticString(int stringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public int GenerateBirthYear(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }


    public void Registration() {
        new TestParameterising().registraion(RandomAlphabeticString(3), RandomAlphabeticString(4), RandomAlphabeticString(5) + "@aaa.com", String.valueOf(GenerateBirthYear(1920, 2000)), "Male", RandomAlphabeticString(8));
    }

    @BeforeEach
    public void startBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.tvnz.co.nz");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        homePageObjects =PageFactory.initElements(driver,HomePageObjects.class);

    }

    @AfterEach
    public void quitBrowser() {
        driver.close();
        driver.quit();

    }

    WebDriver driver = null;
    static String email = null;
    static String password = null;
    DevTools devTools =null;


    @Test
    public void registrationWithCodeInTestMethod() {
        String gender = "Male";
        email = RandomAlphabeticString(5) + "@aaa.com";
        password = RandomAlphabeticString(8);

        if (driver.findElement(By.xpath("//a[text()='Login']")).isDisplayed()) {

            driver.findElement(By.xpath("//a[text()='Login']")).click();
            driver.findElement(By.xpath("//span[text()='Sign up now']")).click();
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("firstName")).sendKeys(RandomAlphabeticString(4));
            driver.findElement(By.id("lastName")).sendKeys(RandomAlphabeticString(3));
            driver.findElement(By.xpath("//div[@name='yearOfBirth']")).click();
            driver.findElement(By.xpath("//div[.='" + String.valueOf(GenerateBirthYear(1920, 2000)) + "']")).click();
            driver.findElement(By.xpath("//div[@name='gender']")).click();
            driver.findElement(By.xpath("//div[@id='gender']/div[.='" + gender + "']")).click();
            driver.findElement(By.xpath("//label[@for='houseRules']//span")).click();
            driver.findElement(By.xpath("//label[@for='latestNews']/div/div")).click();
            driver.findElement(By.xpath("//button[.='Sign Me Up']")).click();
        } else {
            System.out.println("page not displayed");
        }
    }

    @Test
    public void logIn() throws InterruptedException, AWTException {
//        Thread.sleep(3000);
//       List<WebElement> elements = driver.findElements(By.xpath("//a[text()='Logins']"));
//        Assertions.assertTrue(elements.size()>0,"Home Page is not displayed");
//        System.out.println(driver.findElement(By.xpath("//li[@id='Categories']//a[text()='Natural World']")).isDisplayed());
//        driver.findElement(By.xpath("//a[text()='Login']")).click();
////        Thread.sleep(3000);
//        System.out.println(driver.getCurrentUrl());
//        Assertions.assertTrue(driver.findElement(By.id("email")).isDisplayed(), "Email field is not available");
////        driver.findElement(By.id("email")).sendKeys(email);
//        driver.findElement(By.id("password")).sendKeys(password);
//        driver.findElement(By.xpath("//span[.='Login']/..")).click();

//        List<WebElement> categories = driver.findElements(By.xpath("//li[@id='Categories']//li//a"));
//        for (WebElement element:categories   ) {
//            System.out.println(element.getAttribute("text"));
//
//        }
//        List<WebElement> topPicksShows = driver.findElements(By.xpath("//h2[@data-anchor= 'TopPicks']/../../..//div[@class='ember-view']//a/div"));
//        for (WebElement e :
//                topPicksShows) {
//            System.out.println(e.getAttribute("id"));
//
//        }

        Actions builder = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofSeconds(5));
//        WebElement element = driver.findElement(By.xpath("//div[.='More movies']"));
//        wait.until(ExpectedConditions.elementToBeSelected(element));

        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        Thread.sleep(3000);
        builder.moveToElement(movies.get(0)).perform();
        Thread.sleep(3000);
        System.out.println(movies.get(0).getAttribute("innerHTML"));
        WebElement element = driver.findElement(By.xpath("//h2[@data-anchor='Movies']/../../..//span[@aria-label='Next slide']/div[@class='swiper-button-next swiper-no-swiping']"));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        while (element.isDisplayed()) {
            element.click();
            Thread.sleep(1000);
        }


        movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        for (WebElement e : movies) {
            System.out.println(e.getAttribute("id"));

        }

    }
    @Test
    public void viewMoreTest() throws InterruptedException, AWTException {
        Actions builder = new Actions(driver);
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        Thread.sleep(3000);
        builder.moveToElement(movies.get(0)).perform();
//        Robot bot = new Robot();
//        Point p = movies.get(0).getLocation();
//        bot.mouseMove(p.getX(), p.getY());
//        bot=null;
        Thread.sleep(3000);
        movies.get(0).findElement(By.cssSelector("div.QuickInfo-actions *> a.Button--primary")).click();
        driver.getCurrentUrl();

    }

    @Test
    public void getSynopsis () throws InterruptedException {
        Actions builder = new Actions(driver);
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        Thread.sleep(3000);
        builder.moveToElement(movies.get(1)).perform();
        Thread.sleep(3000);
        System.out.println (movies.get(1).getAttribute("innerHTML"));
        String synopsis =movies.get(1).findElement(By.cssSelector("div.QuickInfo-synopsis")).getText();
        System.out.println(synopsis);

    }

    @Test
    public void contextClick () throws InterruptedException, IOException {
        Actions builder = new Actions(driver);
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        builder.contextClick(movies.get(1))
                .sendKeys(Keys.TAB).sendKeys(Keys.ESCAPE).build().perform();
        Thread.sleep(3000);
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        String parentFolder = System.getProperty("user.dir");
        File destFile  = new File(parentFolder+"/target/test.png");
        FileUtils.copyFile(srcFile,destFile);


    }
    @Test
    public void ScreenShoot () throws InterruptedException, IOException {
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        File elementScreenShot = movies.get(1).getScreenshotAs(OutputType.FILE);
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        String parentFolder = System.getProperty("user.dir");
        File destFile  = new File(parentFolder+"/target/test.png");
        File elementScreen  = new File(parentFolder+"/target/element.png");
        FileUtils.copyFile(srcFile,destFile);
        FileUtils.copyFile(elementScreenShot,elementScreen);



    }
    @Test
    public void MovieBeltScreenShoot () throws InterruptedException, IOException {
        WebElement movies = driver.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']"));
        File elementScreenShot = movies.getScreenshotAs(OutputType.FILE);
        String parentFolder = System.getProperty("user.dir");
        File elementScreen  = new File(parentFolder+"/target/MoviesBelt.png");
        FileUtils.copyFile(elementScreenShot,elementScreen);
    }
    public void waitForPageLoad(){
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Test
    public void SyncMovieBeltScreenShoot () throws IOException {
        driver.get("https://www.tvnz.co.nz");
       waitForPageLoad();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        WebElement movies = driver.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']"));
        wait.until(ExpectedConditions.visibilityOf(movies));
        File elementScreenShot = movies.getScreenshotAs(OutputType.FILE);
        System.out.println(new java.io.File(".").getCanonicalPath());
        String parentFolder = System.getProperty("user.dir");
        File elementScreen  = new File(parentFolder+"/target/MoviesBelt.png");
        FileUtils.copyFile(elementScreenShot,elementScreen);

    }
    @Test
    public void customSyncTest(){
        driver.get("https://www.tvnz.co.nz");
        waitForPageLoad();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        WebElement movies = driver.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']"));
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return d.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']")).getAttribute("class").equals("swiper-wrapper");
            }
        });
    }
    @Test
    public void customSyncWithCountTest(){
        driver.get("https://www.tvnz.co.nz");
        waitForPageLoad();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        //WebElement movies = driver.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']"));
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return d.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div")).size()>10;
            }
        });
    }
    @Test
    public void testFluentWaitWithPredicate(){
        driver.get("https://www.tvnz.co.nz");
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofMillis(20000))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div")).size() > 10;
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void  getRequestsAndResponseUrls() throws IOException {
        devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(new Command<>("Network.enable", ImmutableMap.of()));
        devTools.addListener(Network.responseReceived(), l ->{
            System.out.print("Response URL: ");
            System.out.println(l.getRequestId());
        });
        devTools.addListener(Network.requestWillBeSent(),l->{
            if(!l.getRequest().getUrl().contains("tvnz.co.nz"))
                System.out.println(l.getRequest().getUrl());
        });
        driver.get("https://www.tvnz.co.nz");
    }

    @Test
    public void jsExecutorTest(){
//        driver.get("https://www.tvnz.co.nz");
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(movies.get(1)));
        String title = driver.getTitle();
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String titleFromJs = (String) js.executeScript("return document.title");
            List<WebElement> count = (List<WebElement>) js.executeScript("var links = document.getElementsByTagName('A'); return links");
            for (WebElement e:count ) {
                System.out.println(e.getAttribute("href"));

            }
//            System.out.println(" Total Links on home page is : " + count);
            System.out.println(titleFromJs);
            System.out.println(title);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void scrollDownWithJsExecutor(){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)","");
        System.out.println();
    }
    @Test
    public void HightlightElementWithJsExecutor() throws InterruptedException {
        if (driver.findElement(By.xpath("//a[text()='Login']")).isDisplayed())
            driver.findElement(By.xpath("//a[text()='Login']")).click();
        //Collects the webelement
        WebElement ele = driver.findElement(By.id("email"));
        //Create object of a JavascriptExecutor interface
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //use executeScript() method and pass the arguments
        //Here i pass values based on css style. Yellow background color with solid red color border.
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
        Thread.sleep(10000);
    }
    @Test
    public void iFrameTest() {
        driver.get("http://demo.guru99.com/test/guru99home/");
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        List<WebElement> iFrames = driver.findElements(By.tagName("iframe"));

        for (WebElement ele:iFrames             ) {

            driver.switchTo().frame(ele);
            int total = driver.findElements(By.xpath("html/body/a/img")).size();
            System.out.println(total);
            driver.switchTo().defaultContent();
        }
    }

    @Test
    public void tableRowTest() {
        driver.get("https://demo.guru99.com/test/newtours/");
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        List<WebElement> tds = driver.findElements(By.xpath("//img[@alt='Specials']/../../../..//tr//tr/td"));

        for (WebElement ele:tds) {

            System.out.println(ele.getText());
        }
    }

    @Test
    public void testDragDrop() throws InterruptedException {
        driver.get("http://cookbook.seleniumacademy.com/DragDropDemo.html");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        Thread.sleep(10000);
        Actions builder = new Actions(driver);
        builder.dragAndDrop(source, target) .perform();
        assertEquals("Dropped!", target.getText(), "Drag and drop is failed");
        Thread.sleep(10000);
    }
    @Test
    public void handleTabs(){
        WebElement element =driver.findElement(By.xpath("//p[.='Corporate']/..//a[.='About TVNZ']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
       driver.switchTo().window(handles.get(1));
        WebElement element1 = driver.findElement(By.xpath("//a[@title='Reports']"));
        wait.until(ExpectedConditions.elementToBeClickable(element1)).click();
        driver.switchTo().defaultContent();
//        WebElement element2 = driver.findElement(By.xpath("//div[@class='dropdown show']"));
//        wait.until(ExpectedConditions.visibilityOf(element2));
    }

    @Test
    public void handleAlert(){
        List<WebElement> elements = driver.findElements(By.linkText("Login"));
        Assertions.assertTrue(elements.size()>0,"Home Page is not displayed");
        driver.findElement(By.linkText("Login")).click();
//        Thread.sleep(3000);
        System.out.println(driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("email")).isDisplayed(), "Email field is not available");
        driver.findElement(By.id("email")).sendKeys("assignment@grr.la");
        driver.findElement(By.id("password")).sendKeys("test1234");
        driver.findElement(By.xpath("//span[.='Login']/..")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
    }

    @Test
    public void gooseeTest() throws InterruptedException {
        driver.get("https://www.goseetravel.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[@id='app-header__nav']/ul/li[2]/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@title='Car deals']")).click();
        driver.findElement(By.xpath("//a[@aria-label='deals-item']")).click();
        Thread.sleep(1000);
        WebElement pickup = driver.findElement(By.id("pickup_location"));
        pickup.click();
        pickup.sendKeys("Auckland Airport (Domestic) (AKL), New Zealand");
        Thread.sleep(1000);
        pickup.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        pickup.sendKeys(Keys.ENTER);
        //checkbox Drop off at same location (to uncheck if different drop off location)
        driver.findElement(By.name("dropOffAtSameLocation")).click();
        //DROP OFF LOCATION -- USE IF DROP OFF LOCATION IS DIFFERENT FROM PICKUP
        WebElement dropoff = driver.findElement(By.id("dropoff_location"));
        dropoff.click();
        dropoff.sendKeys("Auckland Airport (International) (AKL), New Zealand");
        Thread.sleep(1000);
        dropoff.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        dropoff.sendKeys(Keys.ENTER);
        //Select date - Method 2
        driver.findElement(By.id("pickupDateField")).click();
        String pickupdate = addDaysToToday(4);
        String pickUpMonth = getMonthFromDateString(pickupdate);
        Thread.sleep(1000);
        while (driver.findElement(By.xpath("//*[@data-testid='ChevronLeftIcon']")).isDisplayed()) {
            driver.findElement(By.xpath("//*[@data-testid='ChevronLeftIcon']")).click();
            Thread.sleep(1000);
        }
        while (!driver.findElement(By.cssSelector(".DayPicker_1.DayPicker__horizontal.DayPicker")).getText().contains(pickUpMonth)) {
            driver.findElement(By.cssSelector(".DayPickerNavigation__horizontal_2 > div:nth-child(2)")).click();
        }
// Thread.sleep(1000);

        driver.findElement(By.xpath("//*/td[contains(@aria-label, ' " + pickupdate + "')]")).click();
// Thread.sleep(1000);
        String dropOffDate = addDaysToToday(7);
        String dropOffMonth = getMonthFromDateString(dropOffDate);
// Thread.sleep(1000);
        while (!driver.findElement(By.cssSelector(".DayPicker_1.DayPicker__horizontal.DayPicker")).getText().contains(dropOffMonth)) {
            driver.findElement(By.cssSelector(".DayPickerNavigation__horizontal_2 > div:nth-child(2)")).click();
        }
// Thread.sleep(2000);
        driver.findElement(By.xpath("//*/td[contains(@aria-label, ' " + dropOffDate + "')]")).click();
        //Calendar confirm button
        driver.findElement(By.xpath("//*[contains(button, 'Confirm')]/button")).click();
        //pickup time 10:30 am
        List<WebElement> myList3 = driver.findElements(By.xpath("//*[@id='menu-']/div[3]/ul/li"));
        java.util.Iterator<WebElement> iterate = myList3.iterator();
        if (iterate.hasNext()) {
            for (WebElement time : myList3) {
                if (time.getText().contains("10:30 AM")) {
                    time.click();
                    break;
                }
            }
        }
        //dropoff time 10:30 am
        List<WebElement> myList4 = driver.findElements(By.xpath("//*[@id='menu-']/div[3]/ul/li"));
        java.util.Iterator<WebElement> iterate4 = myList4.iterator();
        if (iterate4.hasNext()) {
            for (WebElement droptime : myList4) {
                if (droptime.getText().contains("10:30 AM")) {
                    droptime.click();
                    break;
                }
            }
        }
        //Checkbox - driver aged 25-69
        driver.findElement(By.name("customDriverDetails")).click();
        //Drivers age box - Selecting age
        List<WebElement> myList = driver.findElements(By.xpath("//*[@id='menu-']/div[3]/ul/li"));
        java.util.Iterator<WebElement> iterate5 = myList.iterator();
        if (iterate5.hasNext()) {
            for (WebElement age : myList) {
                if (age.getText().contains("24")) {
                    age.click();
                    break;
                }
            }
        } //country I live in
        //WebElement CountryIlivein = driver.findElement(By.xpath("//form[@id='widget-search-form']/div/div[2]/div[2]/div/div[2]/div/div/input"));
        WebElement CountryIlivein = driver.findElement(By.xpath("//*[.='Country I live in']/following-sibling::div//input"));
        CountryIlivein.click();
        CountryIlivein.sendKeys("New Zealand");
        Thread.sleep(1000);
        CountryIlivein.sendKeys(Keys.ARROW_DOWN);
        CountryIlivein.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        if(driver.findElements(By.xpath("//*[contains(button,'Search Cars')]/button")).size()>0)
            driver.findElement(By.xpath("//*[contains(button,'Search Cars')]/button")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.findElements(By.xpath("//*[@id='desktop-results']/li")).size()>0);
        System.out.println("Page title is : " + driver.getTitle());
        //driver.quit();
    }

    public String addDaysToToday(int daysToAdd){
        LocalDate today = LocalDate.now();

        return today.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));

    }

    public String getMonthFromDateString(String dateString){
        return dateString.split(" ")[0];
    }
    @Test
    public void testSimpleAlert() {
        driver.get("http://cookbook.seleniumacademy.com/Alerts.html");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("simple"))));
// Click Simple button to show an Alert box
        driver.findElement(By.id("simple")).click();
// Optionally we can also wait for an Alert box using the
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());
// Get the Alert
        Alert alert = driver.switchTo().alert();
// Get the text displayed on Alert
        String textOnAlert = alert.getText();
// Check correct message is displayed to the user on Alert box
        assertEquals("Hello! I am an alert box!", textOnAlert);
// Click OK button, by calling accept method
        alert.accept();
    }
    @Test
    public void findNewsAds() throws InterruptedException {

        driver.get("https://www.1news.co.nz/");
        Thread.sleep(10000);

        Assertions.assertTrue( homePageObjects.checkHeroImageDispalyed());
        homePageObjects.clickFirstStoryFromLatest();

//        List<WebElement> frames = driver.findElements(By.xpath("//iframe[@title='3rd party ad content']"));
//        for (WebElement e : frames) {
//            driver.switchTo().frame(e);
//
//
//            String href = driver.findElement(By.xpath("/html/body/div[2]/a")).getAttribute("href");
//            System.out.println(href);
//            driver.switchTo().defaultContent();
//        }


//        List<WebElement> ads = driver.findElements(By.xpath("//iframe[starts-with(@id,'google_ads_iframe_')]"));
//        for (WebElement e : ads) {
//            System.out.println(e.getText());
//        }
    }

}
