package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.day12.TestParameterising;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Main {
    static String year = "1990";
    public int tests(){
        return 2222;
    }
    public static void main(String[] args) {
        new TestParameterising().registraion("bbb","aaa", "aa@aaa.com", "2000", "Female", "11111111");
        new TestParameterising().registraion("ccc","bbb", "bbb@aaa.com", "2002", "Male", "22222222");

//        WebDriverManager.firefoxdriver().setup();
//        FirefoxDriver fdriver = new FirefoxDriver();
//        fdriver.get("https://www.tvnz.co.nz");
//        System.out.println("abcd");
    }
}