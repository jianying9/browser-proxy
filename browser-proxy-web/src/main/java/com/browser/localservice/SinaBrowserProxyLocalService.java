package com.browser.localservice;

import com.wolf.framework.local.Local;

/**
 *
 * @author aladdin
 */
public interface SinaBrowserProxyLocalService extends Local{
    
    public SinaCookie getCookieByLogin(String userName, String password);
    
    public SinaCookie getNewCookieByCookie(SinaCookie sinaCookie);
    
}
