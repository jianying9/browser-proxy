package com.wolf.browser;

import java.util.Map;
import java.util.Set;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        WebDriver driver = new FirefoxDriver();
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
        return cookieMap;
    }
}
