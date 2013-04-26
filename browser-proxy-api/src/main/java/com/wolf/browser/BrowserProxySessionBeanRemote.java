package com.wolf.browser;

import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author aladdin
 */
@Remote
public interface BrowserProxySessionBeanRemote {
    
    /**
     * 获取最新cookie
     * @param url
     * @param cookieMap
     * @return 
     */
    public Map<String, String> getNewCookie(String url, Map<String, String> cookieMap);
    
    /**
     * 通过帐号密码登录获取cookie
     * @param userName
     * @param password
     * @return 
     */
    public Map<String, String> getLoginCookie(String url, String userName, String userNameXPath, String password, String passwordXPath, String checkCodeXPath, int checkCodeLength, String loginBtnXPath);
}
