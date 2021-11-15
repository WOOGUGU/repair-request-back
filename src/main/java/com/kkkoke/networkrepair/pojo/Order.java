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
    private Long id; // 工单id
    private String sender; // 工单发起者（用户）
    private String tel; // 工单发起者联系方式
    private String type; // 工单类型
    private String des; // 故障描述
    private String position; // 故障位置
    private Integer progress; // -2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理
    private String solver; // 解决工单的技术人员
    private LocalDateTime timeStart; // 工单发起时间
    private LocalDateTime timeEnd; // 工单解决时间
    private String feedBack; // 用户反馈
}
