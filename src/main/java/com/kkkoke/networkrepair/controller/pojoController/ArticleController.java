package com.kkkoke.networkrepair.controller.pojoController;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Article;
import com.kkkoke.networkrepair.service.ArticleService;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "添加文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "createTime", value = "文章创建时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "文章修改时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "contentPath", value = "文章内容路径", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "文章状态", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addArticle")
    public ApiResult addArticle(@NotBlank(message = "createTime can not be null") String createTime, @NotBlank(message = "updateTime can not be null") String updateTime,
                                @NotBlank(message = "contentPath can not be null") String contentPath, @NotBlank(message = "author can not be null") String author,
                                @NotNull(message = "displayStatus can not be null") Integer displayStatus) {
        articleService.addArticle(createTime, updateTime, contentPath, author, displayStatus);
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
            @ApiImplicitParam(name = "createTime", value = "文章创建时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "文章修改时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "contentPath", value = "文章内容路径", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文章作者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "文章状态", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateArticle")
    public ApiResult updateArticle(@NotNull(message = "articleId can not be null") Integer articleId, @NotBlank(message = "createTime can not be null") String createTime,
                                   @NotBlank(message = "updateTime can not be null") String updateTime, @NotBlank(message = "contentPath can not be null") String contentPath,
                                   @NotBlank(message = "author can not be null") String author, @NotNull(message = "displayStatus can not be null") Integer displayStatus) throws DataHasNotExistedException {
        articleService.updateArticle(articleId, createTime, updateTime, contentPath, author, displayStatus);
        return ApiResult.success("更新成功");
    }

    @ApiOperation(value = "查看所有文章")
    @Secured({"ROLE_admin, ROLE_user, ROLE_repairman"})
    @GetMapping("/selectAllArticle")
    public ApiResult selectAllArticle() throws DataHasNotExistedException {
        List<Article> articles = articleService.selectAllArticle();
        return ApiResult.success(articles, "查询成功");
    }

    @ApiOperation(value = "通过id查找文章")
    @ApiImplicitParam(name = "articleId", value = "文章Id", required = true, paramType = "query")
    @Secured({"ROLE_admin, ROLE_user, ROLE_repairman"})
    @PostMapping("/selectArticleById")
    public ApiResult selectArticleById(@NotNull(message = "articleId can not be null") Integer articleId) throws DataHasNotExistedException {
        Article article = articleService.selectArticleById(articleId);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过创建时间查找文章")
    @ApiImplicitParam(name = "createTime", value = "文章创建时间", required = true, paramType = "query")
    @Secured({"ROLE_admin, ROLE_user, ROLE_repairman"})
    @PostMapping("/selectArticleByCreateTime")
    public ApiResult selectArticleByCreateTime(@NotBlank(message = "createTime can not be null") String createTime) throws DataHasNotExistedException {
        Article article = articleService.selectArticleByCreateTime(createTime);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过修改时间查找文章")
    @ApiImplicitParam(name = "updateTime", value = "文章修改时间", required = true, paramType = "query")
    @Secured({"ROLE_admin, ROLE_user, ROLE_repairman"})
    @PostMapping("/selectArticleByUpdateTime")
    public ApiResult selectArticleByUpdateTime(@NotBlank(message = "createTime can not be null") String updateTime) throws DataHasNotExistedException {
        Article article = articleService.selectArticleByUpdateTime(updateTime);
        return ApiResult.success(article, "查找成功");
    }

    @ApiOperation(value = "通过作者查找文章")
    @ApiImplicitParam(name = "createTime", value = "文章创建时间", required = true, paramType = "query")
    @Secured({"ROLE_admin, ROLE_user, ROLE_repairman"})
    @PostMapping("/selectArticleByAuthor")
    public ApiResult selectArticleByAuthor(@NotBlank(message = "author can not be null") String author) throws DataHasNotExistedException {
        List<Article> articles = articleService.selectArticleByAuthor(author);
        return ApiResult.success(articles, "查找成功");
    }
}

