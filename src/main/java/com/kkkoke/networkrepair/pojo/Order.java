package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Order {
    private Long id;
    private String sender;
    private String tel;
    private String type;
    private String des;
    private String position;
    private Integer progress;
    private String solver;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String feedBack;
}
