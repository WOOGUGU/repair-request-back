package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picker {
    private Integer id;
    private String time; // 可预约时间段
    private String type; // 故障类型

    // 不带id构造函数
    public Picker(String time, String type) {
        this.time = time;
        this.type = type;
    }
}
