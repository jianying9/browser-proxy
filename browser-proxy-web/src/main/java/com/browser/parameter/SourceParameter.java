package com.browser.parameter;

import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;

/**
 * 第三方源帐号信息
 *
 * @author aladdin
 */
@ParametersConfig()
public class SourceParameter implements Parameter {
    //

    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "帐号", filterTypes = {})
    private String userName;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_10, desc = "来源标识", filterTypes = {})
    private String source;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_60, desc = "密码", filterTypes = {})
    private String password;
    //
    @ParameterConfig(dateTypeEnum = DataTypeEnum.CHAR_4000, desc = "cookie", filterTypes = {})
    private String cookie;
}
