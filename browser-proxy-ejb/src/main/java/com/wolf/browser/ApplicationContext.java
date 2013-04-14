package com.wolf.browser;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author aladdin
 */
public class ApplicationContext {
    
    public final static ApplicationContext CONTEXT = new ApplicationContext();
    
    private DesiredCapabilities chrome;
    
    public void initChrome(String driver, String binary) {
        System.setProperty("webdriver.chrome.driver", driver);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.binary", binary);
        this.chrome = capabilities;
    }
    
    public DesiredCapabilities getChrome() {
        return this.chrome;
    }
}
