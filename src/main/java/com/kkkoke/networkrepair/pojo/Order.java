package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id; // 工单id
    private String username; // 用户名
    private String sender; // 工单发起者（用户）
    private String tel; // 工单发起者联系方式
    private String type; // 工单类型
    private String des; // 故障描述
    private String position; // 故障位置
    private String timeSubscribe; // 工单预约上门时间
    private Integer progress; // 0：待审核，1：待处理，2：已处理，3：用户取消，4：审核不通过
    private String solver; // 解决工单的技术人员
    private String timeStart; // 工单发起时间
    private String timeDistribution; // 工单分配时间
    private String timeEnd; // 工单解决时间
    private String feedback; // 用户反馈


    // 不带id的报修工单构造函数
    public Order(String username, String sender, String tel, String type, String des, String position,
                 String timeSubscribe, Integer progress, String solver, String timeStart,
                 String timeDistribution, String timeEnd, String feedback) {
        this.username = username;
        this.sender = sender;
        this.tel = tel;
        this.type = type;
        this.des = des;
        this.position = position;
        this.timeSubscribe = timeSubscribe;
        this.progress = progress;
        this.solver = solver;
        this.timeStart = timeStart;
        this.timeDistribution = timeDistribution;
        this.timeEnd = timeEnd;
        this.feedback = feedback;
    }

    // 添加工单时的构造函数
    public Order(String username, String sender, String tel, String type, String des, String position, String timeSubscribe, String timeStart) {
        this.username = username;
        this.sender = sender;
        this.tel = tel;
        this.type = type;
        this.des = des;
        this.position = position;
        this.timeSubscribe = timeSubscribe;
        this.timeStart = timeStart;
    }

    public Order(Integer orderId, Integer progress) {
        this.id = orderId;
        this.progress = progress;
    }
}
