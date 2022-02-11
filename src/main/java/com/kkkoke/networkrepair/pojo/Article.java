package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;//文章id
    private String createTime;//文章创建时间
    private String updateTime;//文章修改时间
    private String contentPath;//文章内容路径
    private String author;//文章作者
    private Integer displayStatus;//文章状态
    //不带id的构造函数
    public Article(String createTime, String updateTime, String contentPath, String author, Integer displayStatus) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.contentPath = contentPath;
        this.author = author;
        this.displayStatus = displayStatus;
    }
}
