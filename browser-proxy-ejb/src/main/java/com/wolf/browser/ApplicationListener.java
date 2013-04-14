package com.wolf.browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 应用程序全局信息初始化
 *
 * @author aladdin
 */
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //1.加载/WEB-INF/system.propertiesl配置
        String appPath = sce.getServletContext().getRealPath("");
        StringBuilder fileBuilder = new StringBuilder(30);
        fileBuilder.append(appPath).append(File.separator).append("WEB-INF").append(File.separator).append("system.properties");
        String filePath = fileBuilder.toString();
        File file = new File(filePath);
        Properties configProperties = new Properties();
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
            configProperties.load(inStream);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }
        }
        //
        String webdriverIeDriver = configProperties.getProperty("webdriverIeDriver");
        System.setProperty("webdriver.ie.driver", webdriverIeDriver);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
