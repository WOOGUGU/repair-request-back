package com.kkkoke.networkrepair.service.Impl;
import com.kkkoke.networkrepair.dao.ArticleDao;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    //添加文章
    public int addArticle(Article article) {
        return articleDao.addArticle(article);
    }

    @Override
    //删除文章
    public int deleteArticle(Long id) {
        return articleDao.deleteArticle(id);
    }

    @Override
    //修改文章
    public Integer updateArticle(Article article) {
        return articleDao.updateArticle(article);
    }

    @Override
    //查看所有文章
    public List<Article> selectAllArticle() {
        return articleDao.selectAllArticle();
    }

    @Override
    //通过id查看文章
    public Article selectArticleById(Long id) {
        return articleDao.selectArticleById(id);
    }

    @Override
    //通过创建时间查看文章
    public Article selectArticleByCreateTime(String createTime) {
        return articleDao.selectArticleByCreateTime(createTime);
    }

    @Override
    //通过更新时间查看文章
    public Article selectArticleByUpdateTime(String updateTime) {
        return articleDao.selectArticleByUpdateTime(updateTime);
    }

    @Override
    //通过作者来查看文章
    public Article selectArticleByAuthor(String author) {
        return articleDao.selectArticleByAuthor(author);
    }
}
