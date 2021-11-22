package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PickerTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String time;

    // 不带id构造函数
    public PickerTime(String time) {
        this.time = time;
    }
}
