package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleDao {
    // 添加文章
    int addArticle(Article article);

    // 删除文章
    int deleteArticle(Integer id);

    // 修改文章
    int updateArticle(Integer id, String updateTime, String contentPath, String author, Integer displayStatus, String title, String des, String coverPath);

    // 查看所有文章
    List<Article> selectAllArticle();

    // 通过用id查找文章
    Article selectArticleById(Integer id);

    // 通过创建时间查找文章
    Article selectArticleByCreateTime(String createTime);

    // 通过修改时间查找文章
    Article selectArticleByUpdateTime(String updateTime);

    // 通过作者查找文章
    List<Article> selectArticleByAuthor(String author);

    //查找文章 后台接口
    List<Article> selectArticle(Integer id, String author, Integer displayStatus, String title, String des);
}

