package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;  // 文章id
    private String createTime;  // 文章创建时间
    private String updateTime;  // 文章修改时间
    private String contentPath;  // 文章内容路径
    private String author;  // 文章作者
    private Integer displayStatus;  // 文章状态
    private String title;  // 文章标题
    private String des;  // 文章摘要
    private String coverPath;  // 文章封面路径

    // 不带id的构造函数
    public Article(String createTime, String updateTime, String contentPath, String author, Integer displayStatus, String title, String des, String coverPath) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.contentPath = contentPath;
        this.author = author;
        this.displayStatus = displayStatus;
        this.title = title;
        this.des = des;
        this.coverPath = coverPath;
    }
}
