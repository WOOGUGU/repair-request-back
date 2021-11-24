package com.kkkoke.networkrepair.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:values.properties")
public class ValuesUtil {
    @Value("${TokenOutTime}")
    private Integer TokenOutTime; // token过期时间

    // 获取token过期时间
    public Integer getTokenOutTime() {
        return TokenOutTime;
    }
}
