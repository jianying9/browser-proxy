
import com.wolf.browser.BrowserProxySessionBeanRemote;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class BroswerProxyRemoteUnitTest {

    public BroswerProxyRemoteUnitTest() {
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
    //

    @Test
    public void browserProxyRemoteTest() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.64.50");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        BrowserProxySessionBeanRemote remote;
        try {
            InitialContext ic = new InitialContext(props);
            remote = (BrowserProxySessionBeanRemote) ic.lookup("com.wolf.browser.BrowserProxySessionBeanRemote");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("init BrowserProxySessionBeanRemote failure.");
        }
        //
        Map<String, String> oldCookieMap = new HashMap<String, String>();
        oldCookieMap.put("SUE", "es%3D5300d4dd331e9807fe1f81ee560d6b6d%26ev%3Dv1%26es2%3D1c763dda1652040d50640952f6928271%26rs0%3DbBcbKji%252FqouPgr6%252F62v9IgBo%252B8ZNHHWzFHZCtY%252BG4Mm38%252FumggfzTHXIKe%252BR5QYF4yti9uV1t8xdpboXe20sVp%252FzQ0sQgaz5wGJ3MUjDQQ8qB269uxuAfpl2Xqa%252FDwlMtH0SQwXWz8cisAwzuqqcDVxgXVwqFqSFO4MZHsCL0x0%253D%26rv%3D0");
        oldCookieMap.put("SUP", "cv%3D1%26bt%3D1365927895%26et%3D1366014295%26d%3Dc909%26i%3Db0c2%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D25%26st%3D0%26uid%3D3316242410%26user%3Dhr10240001%2540163.com%26ag%3D4%26name%3Dhr10240001%2540163.com%26nick%3Dhr0001%26fmp%3D%26lcp%3D");
        oldCookieMap.put("SUS", "SID-3316242410-1365927895-JA-uh54l-6654af482f64bfe7d9d0a0bd5e2e8cc3");
        oldCookieMap.put("ALF", "1366532695");
        oldCookieMap.put("SSOLoginState", "1365927895");
        Map<String, String> newCookieMap = remote.getNewCookie("http://weibo.com", oldCookieMap);
        System.out.println(newCookieMap);
    }
}
