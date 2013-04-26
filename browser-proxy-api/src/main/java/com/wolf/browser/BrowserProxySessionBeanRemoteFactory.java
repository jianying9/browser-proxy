package com.wolf.browser;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author aladdin
 */
public class BrowserProxySessionBeanRemoteFactory {

    public static BrowserProxySessionBeanRemote getBrowserProxySessionBeanRemote() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "data.91yong.com");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        BrowserProxySessionBeanRemote remote;
        try {
            InitialContext ic = new InitialContext(props);
            remote = (BrowserProxySessionBeanRemote) ic.lookup("com.wolf.browser.BrowserProxySessionBeanRemote");
        } catch (NamingException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
        return remote;
    }
}
