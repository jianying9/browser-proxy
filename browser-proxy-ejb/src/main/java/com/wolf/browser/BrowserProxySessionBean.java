package com.wolf.browser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author aladdin
 */
@Stateless
@Startup
@TransactionAttribute(TransactionAttributeType.NEVER)
public class BrowserProxySessionBean implements BrowserProxySessionBeanRemote {

    @Override
    public Map<String, String> getNewCookie(String url, Map<String, String> cookieMap) {
        WebDriver driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
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
        //获取新的cookie
        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
            cookieMap.put(loadedCookie.getName(), loadedCookie.getValue());
        }
        //
        driver.close();
        return cookieMap;
    }

    @Override
    public Map<String, String> getLoginCookie(String url, String userName, String userNameXPath, String password, String passwordXPath, String checkCodeXPath, int checkCodeLength, String loginBtnXPath) {
        
        WebDriver driver = new ChromeDriver(ApplicationContext.CONTEXT.getChrome());
        //访问地址
        driver.get(url);
        WebElement userNameElement = driver.findElement(By.xpath(userNameXPath));
        userNameElement.sendKeys(userName);
        WebElement passwordElement = driver.findElement(By.xpath(passwordXPath));
        passwordElement.sendKeys(password);
        System.out.println("等待检测帐号");
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException ex) {
        }
        WebElement checkCodeElement = driver.findElement(By.xpath(checkCodeXPath));
        if (checkCodeElement.isDisplayed()) {
            //等待
            int time = 10;
            String value;
            boolean wait = true;
            while (wait && time > 0) {
                time--;
                value = checkCodeElement.getAttribute("value");
                if (value != null && value.length() == checkCodeLength) {
                    wait = false;
                }
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException ex) {
                }
            }
        }
        //
        WebElement loginBtnElement = driver.findElement(By.xpath(loginBtnXPath));
        loginBtnElement.click();
        //
        Set<Cookie> allCookies = driver.manage().getCookies();
        Map<String, String> newCookieMap = new HashMap<String, String>(16, 1);
        for (Cookie loadedCookie : allCookies) {
            newCookieMap.put(loadedCookie.getName(), loadedCookie.getValue());
        }
        //
        driver.close();
        return newCookieMap;
    }
}
