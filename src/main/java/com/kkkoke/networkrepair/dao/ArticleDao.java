package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.pojo.Article;
import java.util.List;

public interface ArticleDao {
    // 添加文章
    int addArticle(Article article);
    // 删除文章
    int deleteArticle(Long id);
    // 修改文章
    Integer updateArticle(Article article);
    // 查看所有文章
    List<Article> selectAllArticle();
    // 通过用id查找文章
    Article selectArticleById(Long id);
    // 通过创建时间查找文章
    Article selectArticleByCreateTime(String createTime);
    // 通过修改时间查找文章
    Article selectArticleByUpdateTime(String updateTime);
    // 通过作者查找文章
    Article selectArticleByAuthor(String author);



}

