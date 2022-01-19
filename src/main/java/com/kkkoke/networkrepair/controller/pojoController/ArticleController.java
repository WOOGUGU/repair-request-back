package com.kkkoke.networkrepair.controller.pojoController;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.kkkoke.networkrepair.pojo.Admin;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.JwtToken;
import com.kkkoke.networkrepair.util.token.TokenVerify;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final TokenVerify tokenVerifyForUser;
    private final TokenVerify tokenVerifyForAdmin;
    public ArticleController(ArticleService articleService, @Qualifier("userTokenVerifyImpl") TokenVerify tokenVerifyForUser,
                             @Qualifier("adminTokenVerifyImpl") TokenVerify tokenVerifyForAdmin) {
        this.articleService = articleService;
        this.tokenVerifyForUser = tokenVerifyForUser;
        this.tokenVerifyForAdmin = tokenVerifyForAdmin;
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
        Integer displayStatus = Integer.parseInt((String) articleJson.get("displayStatus"));
        String token = (String) articleJson.get("token");
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，创建添加的article对象
            Article article = new Article(createTime,updateTime,contentPath,author,displayStatus);
            /*文章可以重复添加，不用判断是否存在
            if (Objects.equals(articleService.selectArticleById(id), null)) {
                // 调用service层添加文章
                articleService.addArticle(article);
                // 返回给前端添加的文章数据及处理的状态值*/
            articleService.addArticle(article);
            return new StatusAndDataFeedback(article, "handle_success");
           /* else {
                // 数据库中已经存在此数据
                return new StatusAndDataFeedback(article, "data_exist");
            }*/
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
        Long id = Long.parseLong(idJson.get("id").toString()); // 工单id
        String token = (String) idJson.get("token");
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
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
    //修改文章信息
    @PostMapping("/updateArticle")
    public StatusAndDataFeedback updateArticle(@RequestBody JSONObject articleJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(articleJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) articleJson.get("id"));
        String createTime = (String) articleJson.get("createTime");
        String updateTime = (String) articleJson.get("updateTime");
        String contentPath = (String) articleJson.get("contentPath");
        String author = (String) articleJson.get("author");
        Integer displayStatus = Integer.parseInt((String) articleJson.get("displayStatus"));
        String token = (String) articleJson.get("token");
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，创建要修改的article对象
            Article article = new Article(id,createTime,updateTime,contentPath,author,displayStatus);
            // 查找数据库中是否存在此文章
            if (Objects.equals(articleService.selectArticleById(id), null)){
                return new StatusAndDataFeedback(article, "data_not_exist");
            }
            else {
                // 如果管理员存在就更新数据
                articleService.updateArticle(article);
                return new StatusAndDataFeedback(article, "handle_success");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
    //查看所有文章
    @PostMapping("/selectAllArticle")
    public StatusAndDataFeedback selectAllArticle(@RequestBody JSONObject tokenJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(tokenJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取tokenJson中的数据
        String token = (String) tokenJson.get("token"); // 待验证的token
        // 验证token的正确性
        //是否为管理员
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，使用查询该所有文章
            try {
                List<Article> articles = articleService.selectAllArticle();
                // 判断查询结果是否为空
                if (articles.isEmpty()) {
                    return new StatusAndDataFeedback(null, "data_not_exist");
                }
                else {
                    return new StatusAndDataFeedback(articles, "handle_success");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new StatusAndDataFeedback(null, "exception_happen");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
    // 通过id查找文章
    @PostMapping("/selectArticleById")
    public StatusAndDataFeedback selectArticleById(@RequestBody JSONObject idJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(idJson.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong(idJson.get("id").toString()); //文章id
        String token = (String) idJson.get("token");
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据传入的id查询对应的文章
            Article article=articleService.selectArticleById(id);
            // 判断查询结果是否为空
            if (Objects.equals(article, null)) {

                return new StatusAndDataFeedback(null, "data_not_exist");
            }
            else {
                return new StatusAndDataFeedback(article, "handle_success");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

}

