package org.example.day12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.time.Duration;

public class TestParameterising {

    public void registraion(String fname, String lNmae, String email_address, String yearofBirth, String gender, String password){
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.tvnz.co.nz");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath("//span[text()='Sign up now']")).click();
        driver.findElement(By.id("email")).sendKeys(email_address);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("firstName")).sendKeys(fname);
        driver.findElement(By.id("lastName")).sendKeys(lNmae);
        driver.findElement(By.xpath("//div[@name='yearOfBirth']")).click();
        driver.findElement(By.xpath("//div[.='"+yearofBirth +"']")).click();
        driver.findElement(By.xpath("//div[@name='gender']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//div[@id='gender']/div[.='"+ gender+"']")).click();
        driver.findElement(By.xpath("//label[@for='houseRules']//span")).click();
        driver.findElement(By.xpath("//label[@for='latestNews']/div/div")).click();
        driver.findElement(By.xpath("//button[.='Sign Me Up']")).click();
        driver.close();
        driver.quit();


    }

}
