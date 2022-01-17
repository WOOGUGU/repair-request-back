package com.kkkoke.networkrepair.controller.pojoController;
import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.TokenVerify;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Objects;
public class ArticleController {
    private final ArticleService articleService;
    private final TokenVerify tokenVerify;
    public ArticleController(ArticleService articleService, @Qualifier("articleTokenVerifyImpl") TokenVerify tokenVerify) {
        this.articleService = articleService;
        this.tokenVerify = tokenVerify;
    }
    // 添加文章
    @PostMapping("/addArticle")
    public StatusAndDataFeedback addArticle(@RequestBody JSONObject articleJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(articleJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        String createTime = (String) articleJson.get("createTime");
        String updateTime = (String) articleJson.get("updateTime");
        String contentPath = (String) articleJson.get("contentPath");
        String author = (String) articleJson.get("author");
        Integer displayStatus = (Integer) articleJson.get("displayStatus");
        String token = (String) articleJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，创建添加的article对象
            Article article = new Article(createTime,updateTime,contentPath,author,displayStatus);
            // 查看数据库中是否已经存在此文章
            if (Objects.equals(articleService.selectArticleByCreateTime(createTime), null)) {
                // 调用service层添加文章
                articleService.addArticle(article);
                // 返回给前端添加的文章数据及处理的状态值
                return new StatusAndDataFeedback(article, "handle_success");
            }
            else {
                // 数据库中已经存在此数据
                return new StatusAndDataFeedback(article, "data_exist");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
    // 通过id删除文章
    @PostMapping("/deleteArticle")
    public StatusAndDataFeedback deleteArticle(@RequestBody JSONObject idJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(idJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) idJson.get("id"));
        String token = (String) idJson.get("token");
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，查询数据库，查看要删除的文章是否存在
            if (Objects.equals(articleService.selectArticleById(id), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            }
            else {
                articleService.deleteArticle(id);
                return new StatusAndDataFeedback(null, "handle_success");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
}

