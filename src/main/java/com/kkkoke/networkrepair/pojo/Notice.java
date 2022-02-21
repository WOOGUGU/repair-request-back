package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
    private Integer id; // 通知Id
    private String creatTime; // 创建时间
    private String announceTime; // 发布时间
    private String updateTime; // 修改时间
    private String content; // 公告内容
    private String author; // 发布者
    private Integer displayStatus; // 展示状态

    public Notice(String creatTime, String content, String author, Integer displayStatus) {
        this.creatTime = creatTime;
        this.content = content;
        this.author = author;
        this.displayStatus = displayStatus;
    }

    public Notice(String announceTime, String updateTime, String content, String author, Integer displayStatus) {
        this.announceTime = announceTime;
        this.updateTime = updateTime;
        this.content = content;
        this.author = author;
        this.displayStatus = displayStatus;
    }

    public Notice(String creatTime, String announceTime, String updateTime, String content, String author, Integer displayStatus) {
        this.creatTime = creatTime;
        this.announceTime = announceTime;
        this.updateTime = updateTime;
        this.content = content;
        this.author = author;
        this.displayStatus = displayStatus;
    }
}
