package com.wolf.browser;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Administrator
 */
@Stateless
@Startup
@TransactionAttribute(TransactionAttributeType.NEVER)
public class BrowserProxySessionBean implements BrowserProxySessionBeanRemote {
    
}
