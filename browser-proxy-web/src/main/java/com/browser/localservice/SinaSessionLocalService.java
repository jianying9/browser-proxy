package com.browser.localservice;

import com.browser.entity.SinaSessionEntity;
import com.wolf.framework.local.Local;

/**
 *
 * @author aladdin
 */
public interface SinaSessionLocalService extends Local{
    
    public SinaSessionEntity inquireByUserName(String userName);
    
    public void insert(String userName, String cookie);
    
    public void udpate(String userName, String cookie);
    
}
