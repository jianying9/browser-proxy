package com.browser.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnConfig;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 新浪帐号信息
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "SinaSession",
useCache = false)
public class SinaSessionEntity extends Entity implements Parameter {
    //

    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_60, columnTypeEnum = ColumnTypeEnum.KEY, desc = "帐号")
    private String userName;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.CHAR_4000, desc = "cookie信息")
    private String cookie;
    //
    @ColumnConfig(dataTypeEnum = DataTypeEnum.DATE_TIME, desc = "最后更新时间")
    private long lastUpdateTime;

    public String getUserName() {
        return userName;
    }

    public String getCookie() {
        return cookie;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public String getKeyValue() {
        return this.userName;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("userName", this.userName);
        map.put("cookie", this.cookie);
        map.put("lastUpdateTime", Long.toString(this.lastUpdateTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userName = entityMap.get("userName");
        this.cookie = entityMap.get("cookie");
        this.lastUpdateTime = Long.parseLong(entityMap.get("lastUpdateTime"));
    }
}
