package com.kkkoke.networkrepair.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *	Properties配置文件处理工具
 * @author kkkoke
 */
//从properties文件中读取路径
@Component
@PropertySource("classpath:values.properties")
public class PropertiesUtil {

    @Value("${TokenExpiredTime}")
    private String tokenExpiredTime;

    @Value("${slideImgPath}")
    private String slideImgPath;

    @Value("${startTime}")
    private Integer startTime;

    @Value("${endTime}")
    private Integer endTime;

    public String getTokenExpiredTime() {
        return tokenExpiredTime;
    }

    public String getSlideImgPath() {
        return slideImgPath;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }
}
