package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickerLocation {
    private Integer id;
    private String area;
    private String position;

    // 不带id的构造函数
    public PickerLocation(String area, String position) {
        this.area = area;
        this.position = position;
    }
}
