package com.browser.localservice;

import com.browser.entity.SinaSessionEntity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.annotation.InjectDao;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SinaSessionLocalService.class,
description = "负责操作SinaSession表")
public class SinaSessionLocalServiceImpl implements SinaSessionLocalService {

    @InjectDao(clazz = SinaSessionEntity.class)
    private EntityDao<SinaSessionEntity> sinaSessionEntityDao;

    @Override
    public void init() {
    }

    @Override
    public SinaSessionEntity inquireByUserName(String userName) {
        return this.sinaSessionEntityDao.inquireByKey(userName);
    }

    @Override
    public void insert(String userName, String cookie) {
        Map<String, String> insertMap = new HashMap<String, String>(4, 1);
        insertMap.put("userName", userName);
        insertMap.put("cookie", cookie);
        insertMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        this.sinaSessionEntityDao.insert(insertMap);
    }

    @Override
    public void udpate(String userName, String cookie) {
        Map<String, String> insertMap = new HashMap<String, String>(4, 1);
        insertMap.put("userName", userName);
        insertMap.put("cookie", cookie);
        insertMap.put("lastUpdateTime", Long.toString(System.currentTimeMillis()));
        this.sinaSessionEntityDao.update(insertMap);
    }
}
