package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.helper.FeedbackResult;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.FeedbackService;
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
@Api(tags = "反馈管理")
@Slf4j
@Validated
@RequestMapping("/v2/feedback")
@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation(value = "添加反馈")
    @ApiImplicitParams({@ApiImplicitParam(name = "uid", value = "用户id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "反馈内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "联系方式", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @PostMapping("/addFeedback")
    public ApiResult addFeedback(@NotNull(message = "uid can not be null") Integer uid, @NotBlank(message = "content can not be null") String content, String tel) {
        feedbackService.addFeedback(uid, content, tel);
        return ApiResult.success("反馈提交成功");
    }

    @ApiOperation(value = "根据id删除反馈")
    @ApiImplicitParam(name = "feedbackId", value = "反馈id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteFeedback")
    public ApiResult deleteFeedback(@NotNull(message = "feedbackId can not be null") Integer feedbackId) throws DataHasNotExistedException {
        feedbackService.deleteFeedback(feedbackId);
        return ApiResult.success("反馈删除成功");
    }

    @ApiOperation(value = "修改反馈")
    @ApiImplicitParams({@ApiImplicitParam(name = "feedbackId", value = "反馈id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "uid", value = "用户id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "反馈内容", required = false, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "联系方式", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateFeedback")
    public ApiResult updateFeedback(@NotNull(message = "feedbackId can not be null") Integer feedbackId, Integer uid, String content, String tel) throws DataHasNotExistedException {
        feedbackService.updateFeedback(feedbackId, uid, content, tel);
        return ApiResult.success("反馈修改成功");
    }

    @ApiOperation(value = "查找反馈")
    @ApiImplicitParams({@ApiImplicitParam(name = "feedbackId", value = "反馈id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "uid", value = "用户id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "反馈内容", required = false, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "联系方式", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @GetMapping("/selectFeedback")
    public ApiResult selectFeedback(Integer feedbackId, Integer uid, String content, String tel) {
        List<FeedbackResult> feedbackResults = feedbackService.selectFeedback(feedbackId, uid, content, tel);
        return ApiResult.success(feedbackResults, "反馈查找成功");
    }

    @ApiOperation(value = "根据id查找反馈")
    @ApiImplicitParam(name = "feedbackId", value = "反馈id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectFeedbackById")
    public ApiResult selectFeedbackById(@NotNull(message = "feedbackId can not be null") Integer feedbackId) throws DataHasNotExistedException {
        FeedbackResult feedbackResult = feedbackService.selectFeedbackById(feedbackId);
        return ApiResult.success(feedbackResult, "反馈查找成功");
    }
}
