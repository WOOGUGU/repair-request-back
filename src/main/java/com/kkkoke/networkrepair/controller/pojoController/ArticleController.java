package com.kkkoke.networkrepair.controller.pojoController;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fanghongli
 */
@Api(tags = "文章管理")
@Slf4j
@Validated
@RequestMapping("/v2/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "添加文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "contentPath", value = "文章内容路径", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "文章状态", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addArticle")
    public ApiResult addArticle(@NotBlank(message = "contentPath can not be null") String contentPath, @NotBlank(message = "author can not be null") String author,
                                @NotNull(message = "displayStatus can not be null") Integer displayStatus) {
        articleService.addArticle(contentPath, author, displayStatus);
        return ApiResult.success("文章添加成功");
    }

    @ApiOperation(value = "通过id删除文章")
    @ApiImplicitParam(name = "articleId", value = "文章Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteArticle")
    public ApiResult deleteArticle(@NotNull(message = "articleId can not be null") Integer articleId) throws DataHasNotExistedException {
        articleService.deleteArticle(articleId);
        return ApiResult.success("文章删除成功");
    }

    @ApiOperation(value = "修改文章信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "articleId", value = "文章Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "contentPath", value = "文章内容路径", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "文章状态", required = true, paramType = "query"),
            @ApiImplicitParam(name = "title", value = "文章标题", required = false, paramType = "query"),
            @ApiImplicitParam(name = "des", value = "文章摘要", required = false, paramType = "query"),
            @ApiImplicitParam(name = "coverPath", value = "文章封面路径", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateArticle")
    public ApiResult updateArticle(@NotNull(message = "articleId can not be null") Integer articleId, String contentPath,
                                   String author, Integer displayStatus, String title, String des, String coverPath) throws DataHasNotExistedException {
        articleService.updateArticle(articleId, contentPath, author, displayStatus, title, des, coverPath);
        return ApiResult.success("文章更新成功");
    }

    @ApiOperation(value = "查看所有文章")
    @GetMapping("/selectAllArticle")
    public ApiResult selectAllArticle() throws DataHasNotExistedException {
        List<Article> articles = articleService.selectAllArticle();
        return ApiResult.success(articles, "查找成功");
    }

    @ApiOperation(value = "通过id查找文章")
    @ApiImplicitParam(name = "articleId", value = "文章Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectArticleById")
    public ApiResult selectArticleById(@NotNull(message = "articleId can not be null") Integer articleId) throws DataHasNotExistedException {
        Article article = articleService.selectArticleById(articleId);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过创建时间查找文章")
    @ApiImplicitParam(name = "createTime", value = "文章创建时间", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectArticleByCreateTime")
    public ApiResult selectArticleByCreateTime(@NotBlank(message = "createTime can not be null") String createTime) throws DataHasNotExistedException {
        Article article = articleService.selectArticleByCreateTime(createTime);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过修改时间查找文章")
    @ApiImplicitParam(name = "updateTime", value = "文章修改时间", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectArticleByUpdateTime")
    public ApiResult selectArticleByUpdateTime(@NotBlank(message = "updateTime can not be null") String updateTime) throws DataHasNotExistedException {
        Article article = articleService.selectArticleByUpdateTime(updateTime);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过作者查找文章")
    @ApiImplicitParam(name = "author", value = "作者", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectArticleByAuthor")
    public ApiResult selectArticleByAuthor(@NotBlank(message = "author can not be null") String author) throws DataHasNotExistedException {
        List<Article> articles = articleService.selectArticleByAuthor(author);
        return ApiResult.success(articles, "查找成功");
    }

    @ApiOperation(value = "查找文章 后台接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "articleId", value = "文章Id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = false, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "文章状态", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectArticle")
    public ApiResult selectArticle(Integer articleId, String author, Integer displayStatus, String title, String des) throws DataHasNotExistedException {
        List<Article> articles = articleService.selectArticle(articleId, author, displayStatus, title, des);
        return ApiResult.success(articles, "查找成功");
    }
}

