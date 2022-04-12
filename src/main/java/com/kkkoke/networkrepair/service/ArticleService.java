package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Article;

import java.util.List;


public interface ArticleService {
    // 添加文章
    Article addArticle(String contentPath, String author, Integer displayStatus);

    // 删除文章
    Integer deleteArticle(Integer articleId) throws DataHasNotExistedException;

    // 修改文章
    Integer updateArticle(Integer articleId, String contentPath, String author, Integer displayStatus, String title, String des, String coverPath) throws DataHasNotExistedException;

    // 查看所有文章
    List<Article> selectAllArticle() throws DataHasNotExistedException;

    // 通过用id查找文章
    Article selectArticleById(Integer articleId) throws DataHasNotExistedException;

    // 通过创建时间查找文章
    Article selectArticleByCreateTime(String createTime) throws DataHasNotExistedException;

    // 通过修改时间查找文章
    Article selectArticleByUpdateTime(String updateTime) throws DataHasNotExistedException;

    // 通过作者查找文章
    List<Article> selectArticleByAuthor(String author) throws DataHasNotExistedException;

    //查找文章 后台接口
    List<Article> selectArticle(Integer articleId, String author, Integer displayStatus, String title, String des) throws DataHasNotExistedException;
}
