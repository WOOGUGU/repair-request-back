package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Notice;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.NoticeService;
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
 * @author kkkoke
 */
@Api(tags = "工单管理")
@Slf4j
@Validated
@RequestMapping("/v2/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ApiOperation(value = "增加通知")
    @ApiImplicitParams({@ApiImplicitParam(name = "content", value = "公告内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "发布者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "展示状态", required = true, paramType = "query")})
    @Secured("ROLE_admin")
    @PostMapping("/addNotice")
    public ApiResult addNotice(@NotBlank(message = "content can not be null") String content, @NotBlank(message = "author can not be null") String author,
                               @NotNull(message = "displayStatus can not be null") Integer displayStatus) {
        noticeService.addNotice(content, author, displayStatus);
        return ApiResult.success("通知添加成功");
    }

    @ApiOperation(value = "删除通知")
    @ApiImplicitParam(name = "noticeId", value = "通知Id", required = true, paramType = "query")
    @Secured("ROLE_admin")
    @PostMapping("/deleteNotice")
    public ApiResult deleteNotice(@NotNull(message = "noticeId can not be null") Integer noticeId) throws DataHasNotExistedException {
        noticeService.deleteNotice(noticeId);
        return ApiResult.success("通知删除成功");
    }

    @ApiOperation(value = "修改通知")
    @ApiImplicitParams({@ApiImplicitParam(name = "noticeId", value = "公告id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "公告内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "发布者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "展示状态", required = true, paramType = "query")})
    @Secured("ROLE_admin")
    @PostMapping("/updateNotice")
    public ApiResult updateNotice(@NotNull(message = "noticeId can not be null") Integer noticeId, String content, String author, Integer displayStatus) throws DataHasNotExistedException {
        noticeService.updateNotice(noticeId, content, author, displayStatus);
        return ApiResult.success("通知修改成功");
    }

    @ApiOperation(value = "通过工单id查找通知")
    @ApiImplicitParam(name = "orderId", value = "工单Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectNoticeById")
    public ApiResult selectNoticeById(@NotNull(message = "noticeId can not be null") Integer noticeId) throws DataHasNotExistedException {
        Notice notice = noticeService.selectNoticeById(noticeId);
        return ApiResult.success(notice, "通知查找成功");
    }

    @ApiOperation(value = "通过author查找通知")
    @ApiImplicitParam(name = "author", value = "发布者", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectNoticeByAuthor")
    public ApiResult selectNoticeByAuthor(@NotBlank(message = "author can not be null") String author) throws DataHasNotExistedException {
        List<Notice> notices = noticeService.selectNoticeByAuthor(author);
        return ApiResult.success(notices, "通知查找成功");
    }

    @ApiOperation(value = "查找所有通知")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @GetMapping("/selectAllNotice")
    public ApiResult selectAllNotice(Integer pageNum, Integer pageSize) throws DataHasNotExistedException {
        ResultPage<Notice> notices = noticeService.selectAllNotice(pageNum, pageSize);
        return ApiResult.success(notices, "通知查找成功");
    }

    @ApiOperation(value = "查看通知 后台接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "noticeId", value = "公告Id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "公告内容", required = false, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "发布者", required = false, paramType = "query"),
            @ApiImplicitParam(name = "displayStatus", value = "展示状态", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectNotice")
    public ApiResult selectNotice(Integer noticeId, String author, Integer displayStatus, Integer pageNum, Integer pageSize) throws DataHasNotExistedException {
        ResultPage<Notice> notices = noticeService.selectNotice(noticeId, author, displayStatus, pageNum, pageSize);
        return ApiResult.success(notices, "通知查找成功");
    }
}
