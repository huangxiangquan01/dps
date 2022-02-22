package cn.xqhuang.dps.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestDemo {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com");

        String title = driver.getTitle();
        System.out.printf(title);

        driver.close();
    }
}
