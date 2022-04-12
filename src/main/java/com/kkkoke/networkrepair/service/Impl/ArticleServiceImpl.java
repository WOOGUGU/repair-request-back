package com.kkkoke.networkrepair.service.Impl;
import com.kkkoke.networkrepair.dao.ArticleDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RestTemplate restTemplate;

    //添加文章
    @Override
    public Article addArticle(String contentPath, String author, Integer displayStatus) {
        String createTime = LocalDateTime.now().toString();
        String updateTime = LocalDateTime.now().toString();
        String result = restTemplate.getForObject(contentPath, String.class);
        String title = result.split("msg_title")[1].split("'")[1];
        String des = result.split("msg_desc")[1].split("\"")[1];
        String coverPath = result.split("cdn_url_235_1")[1].split("\"")[1];
        Article article = new Article(createTime, updateTime, contentPath, author, displayStatus, title, des, coverPath);
        articleDao.addArticle(article);
        return article;
    }

    //删除文章
    @Override
    public Integer deleteArticle(Integer articleId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的文章是否存在
        if (ObjectUtils.isEmpty(articleDao.selectArticleById(articleId))) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return articleDao.deleteArticle(articleId);
        }
    }

    //修改文章
    @Override
    public Integer updateArticle(Integer articleId, String contentPath, String author, Integer displayStatus,
                                 String title, String des, String coverPath) throws DataHasNotExistedException {
        String updateTime = LocalDateTime.now().toString();
        // 查找数据库中是否存在此文章
        if (ObjectUtils.isEmpty(articleDao.selectArticleById(articleId))) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            // 如果用户存在就更新数据
            articleDao.updateArticle(articleId, updateTime, contentPath, author, displayStatus, title, des, coverPath);
            return 0;
        }
    }

    //查看所有文章
    @Override
    public List<Article> selectAllArticle() throws DataHasNotExistedException {
        List<Article> articles = articleDao.selectAllArticle();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(articles)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return articles;
        }
    }

    //通过id查看文章
    @Override
    public Article selectArticleById(Integer articleId) throws DataHasNotExistedException {
        // 根据id查找文章
        Article article = articleDao.selectArticleById(articleId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(article)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return article;
        }
    }

    //通过创建时间查看文章
    @Override
    public Article selectArticleByCreateTime(String createTime) throws DataHasNotExistedException {
        // 根据createTime查找文章
        Article article = articleDao.selectArticleByCreateTime(createTime);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(article)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return article;
        }
    }

    //通过更新时间查看文章
    @Override
    public Article selectArticleByUpdateTime(String updateTime) throws DataHasNotExistedException {
        // 根据updateTime查找文章
        Article article = articleDao.selectArticleByCreateTime(updateTime);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(article)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return article;
        }
    }

    //通过作者来查看文章
    @Override
    public List<Article> selectArticleByAuthor(String author) throws DataHasNotExistedException {
        // 根据author查找文章
        List<Article> articles = articleDao.selectArticleByAuthor(author);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(articles)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return articles;
        }
    }

    //查找文章 后台接口
    @Override
    public List<Article> selectArticle(Integer articleId, String author, Integer displayStatus, String title, String des) throws DataHasNotExistedException {
        List<Article> articles = articleDao.selectArticle(articleId, author, displayStatus, title, des);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(articles)) {
            throw new DataHasNotExistedException("Article has not existed");
        } else {
            return articles;
        }
    }
}
