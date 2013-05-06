package com.wolf.browser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author aladdin
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NEVER)
public class BrowserProxySessionBean implements BrowserProxySessionBeanRemote {

    private WebDriver driver = null;

    @Override
    public Map<String, String> getNewCookie(String url, Map<String, String> cookieMap) {
        if (this.driver == null) {
            driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
        } else {
            try {
                driver.getCurrentUrl();
            } catch (RuntimeException e) {
                driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
            }
        }
        //访问地址
        driver.get(url);
        //设置cookie
        Cookie cookie;
        Set<Map.Entry<String, String>> entrySet = cookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookie = new Cookie(entry.getKey(), entry.getValue());
            driver.manage().addCookie(cookie);
        }
        //重新访问地址
        driver.get(url);
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ex) {
        }
        //获取新的cookie
        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
            cookieMap.put(loadedCookie.getName(), loadedCookie.getValue());
        }
        //清除cookie
        driver.manage().deleteAllCookies();
        //
        return cookieMap;
    }

    @Override
    public Map<String, String> getLoginCookie(String url, String userName, String userNameXPath, String password, String passwordXPath, String checkCodeXPath, int checkCodeLength, String loginBtnXPath) {
        if (this.driver == null) {
            driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
        } else {
            try {
                driver.getCurrentUrl();
            } catch (RuntimeException e) {
                driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
            }
        }
        //访问地址
        System.out.println("first get:" + url);
        driver.get(url);
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(userNameXPath)));
        System.out.println("set userName:" + userName);
        WebElement userNameElement = driver.findElement(By.xpath(userNameXPath));
        userNameElement.sendKeys(userName);
        System.out.println("set password:" + password);
        WebElement passwordElement = driver.findElement(By.xpath(passwordXPath));
        passwordElement.sendKeys(password);
        //
        WebElement loginBtnElement = driver.findElement(By.xpath(loginBtnXPath));
        WebElement checkCodeElement = driver.findElement(By.xpath(checkCodeXPath));
        loginBtnElement.click();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
        }
        String newUrl = driver.getCurrentUrl();
        boolean waitCode;
        String codeValue;
        while (newUrl.endsWith(url)) {
            System.out.println("登录失败:");
            System.out.println(newUrl);
            if (checkCodeElement.isDisplayed()) {
                System.out.println("等待输入验证码");
                //等待
                waitCode = true;
                while (waitCode) {
                    codeValue = checkCodeElement.getAttribute("value");
                    if (codeValue != null && codeValue.length() == checkCodeLength) {
                        waitCode = false;
                    }
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                System.out.println("验证码输入完成，登录");
                loginBtnElement.click();
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException ex) {
                }
            }
            newUrl = driver.getCurrentUrl();
        }
        //获取cookie
        System.out.println("获取cookie");
        Set<Cookie> allCookies = driver.manage().getCookies();
        Map<String, String> newCookieMap = new HashMap<String, String>(16, 1);
        for (Cookie loadedCookie : allCookies) {
            newCookieMap.put(loadedCookie.getName(), loadedCookie.getValue());
        }
        //删除cookie
        System.out.println("清除cookie");
        driver.manage().deleteAllCookies();
        return newCookieMap;
    }
}
