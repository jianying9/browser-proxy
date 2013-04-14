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
}
