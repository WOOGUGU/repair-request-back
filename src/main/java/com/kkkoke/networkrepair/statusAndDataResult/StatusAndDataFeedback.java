package com.kkkoke.networkrepair.statusAndDataResult;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class StatusAndDataFeedback {
    // 返回的数据
    private Object data;
    // 返回的状态值
    private String status;

    // 返回给前端的状态值
    public final static String INCOMPLETE_DATA = "Incomplete_data"; // 前端传入的数据不完整
    public final static String HANDLE_SUCCESS = "handle_success"; // 处理成功

    public StatusAndDataFeedback(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
