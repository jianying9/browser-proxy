package com.browser.localservice;

/**
 *
 * @author aladdin
 */
public class SinaCookie {
    
    private final String longCookie;
    private final String weiboCookie;

    public SinaCookie(String longCookie, String weiboCookie) {
        this.longCookie = longCookie;
        this.weiboCookie = weiboCookie;
    }

    public String getLongCookie() {
        return longCookie;
    }

    public String getWeiboCookie() {
        return weiboCookie;
    }
}
