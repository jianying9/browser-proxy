package com.wolf.browser;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class BrowserProxySessionBeanTest {
    
    public BrowserProxySessionBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNewCookie method, of class BrowserProxySessionBean.
     */
    @Test
    public void testGetNewCookie() throws Exception {
        BrowserProxySessionBean remote = new BrowserProxySessionBean();
        Map<String, String> oldCookieMap = new HashMap<String, String>();
        oldCookieMap.put("SUE", "es%3D92b4dc0a9f65799f04ca5932120f9083%26ev%3Dv1%26es2%3D25106237b701a4235bf30cc2781a3f48%26rs0%3DQKBTv3sT4UtfRv%252BWs5%252Fkj9P5Rks3ZB1zh71Bs9mZhCS3xwwc3TACDTCVoHin4mYRuYroZtlVS03apyJ2YpEO04WEBug6619dtU%252FVMvRtErxM%252B41hqY6k6KSvfZYdYXb78cdU5M%252FKBEluoxBENXDt%252FJDMuDnmzOwS4AvlPd1joA4%253D%26rv%3D0");
        oldCookieMap.put("SUP", "cv%3D1%26bt%3D1365776594%26et%3D1365862994%26d%3Dc909%26i%3Dce70%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D25%26st%3D0%26uid%3D3316242410%26user%3Dhr10240001%2540163.com%26ag%3D4%26name%3Dhr10240001%2540163.com%26nick%3Dhr0001%26fmp%3D%26lcp%3D");
        oldCookieMap.put("SUS", "SID-3316242410-1365776594-JA-05iru-2e9fa1acf81e39004142426fcb1b8cc3");
        oldCookieMap.put("un", "hr10240001@163.com");
        Map<String, String> newCookieMap = remote.getNewCookie("http://weibo.com", oldCookieMap);
        System.out.println(newCookieMap);
    }
}
