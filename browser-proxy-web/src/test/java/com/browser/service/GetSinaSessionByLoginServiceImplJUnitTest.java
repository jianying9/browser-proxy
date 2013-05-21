package com.browser.service;

import com.browser.AbstractBrowserTest;
import com.browser.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class GetSinaSessionByLoginServiceImplJUnitTest extends AbstractBrowserTest {

    public GetSinaSessionByLoginServiceImplJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void test() {
        String[] userNames = {"hr10240001@163.com", "hr10240002@163.com", "hr10240003@163.com", "hr10240004@163.com", "hr10240005@163.com", "hr10240006@163.com", "hr10240007@163.com", "hr10240008@163.com", "hr10240009@163.com", "hr10240010@163.com", "hr10240011@163.com", "hr10240012@163.com", "hr10240013@163.com", "hr10240014@163.com", "hr10240015@163.com", "hr10240016@163.com", "hr10240017@163.com", "hr10240018@163.com", "hr10240019@163.com", "hr10240020@163.com"};
//        String[] userNames = {"hr10240001@163.com", "hr10240002@163.com"};
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("password", "ljy1024");
        String result;
        for (String userName : userNames) {
            parameterMap.put("userName", userName);
            result = this.testHandler.execute(ActionNames.GET_SINA_SESSION_BY_LOGIN, parameterMap);
            System.out.println(result);
        }
    }
}
