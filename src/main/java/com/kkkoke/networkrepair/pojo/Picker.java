package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picker {
    private Integer id;
    private String type; // picker类型
    private String value; // picker值

    // 不带id构造函数
    public Picker(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
