package com.kkkoke.networkrepair.controller.tencentCOSController;

import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.util.TencentCOSUtil;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import io.swagger.annotations.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "腾讯云cos")
@RestController
@RequestMapping("/v2/cos")
public class TencentCOSController {

    @ApiOperation(value = "创建存储桶")
    @ApiImplicitParam(name = "bucketName", value = "桶名 注意不能有大写的字母", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/createBucket")
    public ApiResult createBucket(@NotBlank(message = "bucketName can not be null") String bucketName) {
        TencentCOSUtil.createBucket(bucketName);
        return ApiResult.success("桶创建成功");
    }

    @ApiOperation(value = "查询存储桶列表")
    @Secured({"ROLE_admin"})
    @GetMapping("/listAllBuckets")
    public ApiResult listAllBuckets() {
        List<Bucket> buckets = TencentCOSUtil.listAllBuckets();
        return ApiResult.success(buckets, "桶列表查询成功");
    }

    @ApiOperation(value = "上传对象")
    @ApiImplicitParams({@ApiImplicitParam(name = "localFilePath", value = "本地待上传文件路径", required = true, paramType = "query"),
            @ApiImplicitParam(name = "targetFilePath", value = "远程目标存储位置", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/uploadFile")
    public ApiResult uploadFile(@NotBlank(message = "localFilePath can not be null") String localFilePath, @NotBlank(message = "targetFilePath can not be null") String targetFilePath,
                                @NotBlank(message = "bucket can not be null") String bucket) {
        TencentCOSUtil.uploadFile(localFilePath, targetFilePath, bucket);
        return ApiResult.success("上传成功");
    }

    @ApiOperation(value = "查询对象列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "directoryPrefix", value = "存储目录", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @GetMapping("/listAllObjects")
    public ApiResult listAllObjects(@NotBlank(message = "bucket can not be null") String bucket, @NotBlank(message = "directoryPrefix can not be null") String directoryPrefix) {
        List<COSObjectSummary> cosObjectSummaries = TencentCOSUtil.listAllObjects(bucket, directoryPrefix);
        return ApiResult.success(cosObjectSummaries, "对象列表查询成功");
    }

    @ApiOperation(value = "下载对象")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "directoryPrefix", value = "存储目录", required = true, paramType = "query"),
            @ApiImplicitParam(name = "localStoragePath", value = "本地存储路径", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/downloadFile")
    public ApiResult downloadFile(@NotBlank(message = "bucket can not be null") String bucket, @NotBlank(message = "directoryPrefix can not be null") String directoryPrefix,
                                  @NotBlank(message = "localStoragePath can not be null") String localStoragePath) {
        TencentCOSUtil.downloadFile(bucket, directoryPrefix, localStoragePath);
        return ApiResult.success("下载成功");
    }

    @ApiOperation(value = "删除对象")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "directoryPrefix", value = "存储目录", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteFile")
    public ApiResult deleteFile(@NotBlank(message = "bucket can not be null") String bucket, @NotBlank(message = "directoryPrefix can not be null") String directoryPrefix) {
        TencentCOSUtil.deleteFile(bucket, directoryPrefix);
        return ApiResult.success("删除成功");
    }
}
