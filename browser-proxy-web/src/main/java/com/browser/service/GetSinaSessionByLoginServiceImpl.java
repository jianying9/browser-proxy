package com.browser.service;

import com.browser.config.ActionNames;
import com.browser.entity.SinaSessionEntity;
import com.browser.localservice.SinaBrowserProxyLocalService;
import com.browser.localservice.SinaCookie;
import com.browser.localservice.SinaSessionLocalService;
import com.browser.parameter.SourceParameter;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.GET_SINA_SESSION_BY_LOGIN,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"userName", "password"},
returnParameter = {"cookie"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "新浪已有cookie获取新的登录信息")
public class GetSinaSessionByLoginServiceImpl implements Service {
    
    @InjectLocalService()
    private SinaSessionLocalService sinaSessionLocalService;
    //
    @InjectLocalService()
    private SinaBrowserProxyLocalService sinaBrowserProxyLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String userName = messageContext.getParameter("userName");
        String password = messageContext.getParameter("password");
        SinaCookie sinaCookie = this.sinaBrowserProxyLocalService.getCookieByLogin(userName, password);
        SinaSessionEntity entity = this.sinaSessionLocalService.inquireByUserName(userName);
        if(entity == null) {
            this.sinaSessionLocalService.insert(userName, sinaCookie.getLongCookie());
        } else {
            this.sinaSessionLocalService.udpate(userName, sinaCookie.getLongCookie());
        }
        Map<String, String> dataMap = new HashMap<String, String>(2, 1);
        dataMap.put("cookie", sinaCookie.getWeiboCookie());
        messageContext.setMapData(dataMap);
        messageContext.success();
    }
}
