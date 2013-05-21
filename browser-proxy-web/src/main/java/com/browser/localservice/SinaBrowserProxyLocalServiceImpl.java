package com.browser.localservice;

import com.browser.util.CookieUtils;
import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SinaBrowserProxyLocalService.class,
description = "负责操作模拟浏览器操作sina相关页面")
public class SinaBrowserProxyLocalServiceImpl implements SinaBrowserProxyLocalService {

    private String loginUrl = "http://login.sina.com.cn/";
    private String weiboUrl = "http://weibo.com/";
    private String userNameXpath = "/html/body/div/div[2]/div[2]/div/form/div/ul/li/input";
    private String passwordXpath = "/html/body/div/div[2]/div[2]/div/form/div/ul/li[2]/input";
    private String loginBtnXpath = "/html/body/div/div[2]/div[2]/div/form/div/ul/li[7]/a/input";
    private String waitXpath = "/html/body";
    private String chromeDriver = null;
    private String chromeBinary = null;
    private WebDriver webDriver = null;

    @Override
    public void init() {
        this.chromeDriver = ApplicationContext.CONTEXT.getProperty("chromeDriver");
        this.chromeBinary = ApplicationContext.CONTEXT.getProperty("chromeBinary");
    }

    private void initWebDriver() {
        boolean createFlag = false;
        boolean cleanCookie = false;
        if (this.webDriver == null) {
            createFlag = true;
        } else {
            cleanCookie = true;
            try {
                this.webDriver.getCurrentUrl();
            } catch (RuntimeException e) {
                createFlag = true;
            }
        }
        if (createFlag) {
            System.setProperty("webdriver.chrome.driver", this.chromeDriver);
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.binary", this.chromeBinary);
            this.webDriver = new ChromeDriver(capabilities);
        }
        if(cleanCookie) {
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
            System.out.println("clean cookie...");
            this.webDriver.get(this.loginUrl);
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
            this.webDriver.manage().deleteAllCookies();
            System.out.println("clean cookie long.sina.com.cn...");
            this.webDriver.get(this.weiboUrl);
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
            this.webDriver.manage().deleteAllCookies();
            System.out.println("clean cookie weibo.com...");
        }
    }

    @Override
    public SinaCookie getCookieByLogin(String userName, String password) {
        this.initWebDriver();
        //登录login.sina.com.cn
        System.out.println("login.......");
        webDriver.get(this.loginUrl);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        WebElement userNameElement = webDriver.findElement(By.xpath(this.userNameXpath));
        userNameElement.sendKeys(userName);
        WebElement passwordElement = webDriver.findElement(By.xpath(this.passwordXpath));
        passwordElement.sendKeys(password);
        WebElement loginBtnElement = webDriver.findElement(By.xpath(this.loginBtnXpath));
        loginBtnElement.click();
        String newUrl = webDriver.getCurrentUrl();
        while (newUrl.equals(this.loginUrl)) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
            }
            newUrl = webDriver.getCurrentUrl();
        }
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        Set<Cookie> allCookies = webDriver.manage().getCookies();
        Map<String, String> loginCookieMap = new HashMap<String, String>(16, 1);
        for (Cookie cookie : allCookies) {
            loginCookieMap.put(cookie.getName(), cookie.getValue());
        }
        final String loginCookies = CookieUtils.createCookie(loginCookieMap);
        System.out.println("long weibo......");
        webDriver.get(this.weiboUrl);
        newUrl = webDriver.getCurrentUrl();
        while (newUrl.equals(this.weiboUrl) || newUrl.startsWith(this.loginUrl)) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
            }
            newUrl = webDriver.getCurrentUrl();
        }
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        allCookies = webDriver.manage().getCookies();
        Map<String, String> weiboCookieMap = new HashMap<String, String>(16, 1);
        for (Cookie cookie : allCookies) {
            weiboCookieMap.put(cookie.getName(), cookie.getValue());
        }
        final String weiboCookies = CookieUtils.createCookie(weiboCookieMap);
        SinaCookie sinaCookie = new SinaCookie(loginCookies, weiboCookies);
        return sinaCookie;
    }

    @Override
    public SinaCookie getNewCookieByCookie(SinaCookie sinaCookie) {
        this.initWebDriver();
        System.out.println("wirte sina login cookie.....");
        webDriver.get(this.loginUrl);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        Map<String, String> loginCookieMap = CookieUtils.parseCookie(sinaCookie.getLongCookie());
        Cookie cookie;
        Set<Map.Entry<String, String>> entrySet = loginCookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookie = new Cookie.Builder(entry.getKey(), entry.getValue()).domain(".sina.com.cn").build();
            webDriver.manage().addCookie(cookie);
        }
        System.out.println("long sian by cookie....");
        webDriver.get(this.loginUrl);
        String newUrl = webDriver.getCurrentUrl();
        while (newUrl.equals(this.loginUrl)) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
            }
            newUrl = webDriver.getCurrentUrl();
        }
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        Set<Cookie> allCookies = webDriver.manage().getCookies();
        loginCookieMap.clear();
        for (Cookie newCookie : allCookies) {
            loginCookieMap.put(newCookie.getName(), newCookie.getValue());
        }
        final String loginCookies = CookieUtils.createCookie(loginCookieMap);
        //
        System.out.println("wirte weibo cookie.....");
        webDriver.get(this.weiboUrl);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        Map<String, String> weiboCookieMap = CookieUtils.parseCookie(sinaCookie.getWeiboCookie());
        entrySet = weiboCookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookie = new Cookie.Builder(entry.getKey(), entry.getValue()).domain(".weibo.com").build();
            webDriver.manage().addCookie(cookie);
        }
        //
        System.out.println("login weibo by cookie.....");
        webDriver.get(this.weiboUrl);
        newUrl = webDriver.getCurrentUrl();
        while (newUrl.equals(this.weiboUrl) || newUrl.startsWith(this.loginUrl)) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
            }
            newUrl = webDriver.getCurrentUrl();
        }
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.waitXpath)));
        allCookies = webDriver.manage().getCookies();
        weiboCookieMap.clear();
        for (Cookie newCookie : allCookies) {
            weiboCookieMap.put(newCookie.getName(), newCookie.getValue());
        }
        final String weiboCookies = CookieUtils.createCookie(weiboCookieMap);
        SinaCookie newSinaCookie = new SinaCookie(loginCookies, weiboCookies);
        return newSinaCookie;
    }
}
