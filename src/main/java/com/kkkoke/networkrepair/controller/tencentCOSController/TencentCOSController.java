package com.kkkoke.networkrepair.controller.tencentCOSController;

import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.SlideService;
import com.kkkoke.networkrepair.util.CosTemporaryKeyUtil;
import com.kkkoke.networkrepair.util.TencentCOSUtil;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Api(tags = "腾讯云cos")
@Slf4j
@RestController
@RequestMapping("/v2/cos")
public class TencentCOSController {

    @Autowired
    private SlideService slideService;

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

    @ApiOperation(value = "获取对象访问 URL")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "key", value = "文件key", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @GetMapping("/getObjectUrl")
    public ApiResult getObjectUrl(@NotBlank(message = "bucket can not be null") String bucket, @NotBlank(message = "key can not be null") String key) {
        URL url = TencentCOSUtil.getObjectUrl(bucket, key);
        return ApiResult.success(url, "获取成功");
    }

    @ApiOperation(value = "上传流类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "fileKey", value = "文件key", required = true, paramType = "query"),
            @ApiImplicitParam(name = "fileStreams", value = "二进制文件流", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "文件上传者", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/upLoadFileStream")
    public ApiResult upLoadFileStream(String bucket, String fileKey, MultipartFile[] fileStreams, String author) {
        String urlResult = "";
        try {
            for (MultipartFile fileStream : fileStreams) {
                String finalFileKey = fileKey + System.currentTimeMillis() + fileStream.getOriginalFilename().substring(fileStream.getOriginalFilename().lastIndexOf("."));
                TencentCOSUtil.upLoadFileStream(bucket, finalFileKey, fileStream.getInputStream());
                URL url = TencentCOSUtil.getObjectUrl(bucket, finalFileKey);
                urlResult = url.toString();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ApiResult.success(urlResult, "上传成功");
    }

    @ApiOperation(value = "获取对象存储临时密钥")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶名称", required = false, paramType = "query"),
            @ApiImplicitParam(name = "region", value = "存储桶位置", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_repairman", "ROLE_user"})
    @GetMapping("/getCosTemporaryKey")
    public ApiResult getCosTemporaryKey(String bucket, String region) {
        HashMap<String, String> cosTemporaryKey;
        if (!ObjectUtils.isEmpty(bucket) && !ObjectUtils.isEmpty(region)) {
            cosTemporaryKey = CosTemporaryKeyUtil.getCosTemporaryKey(bucket, region);
        } else {
            cosTemporaryKey = CosTemporaryKeyUtil.getCosTemporaryKey();
        }
        return ApiResult.success(cosTemporaryKey, "获取成功");
    }

    @ApiOperation(value = "获取对象存储的存储桶及位置信息")
    @Secured({"ROLE_admin", "ROLE_repairman", "ROLE_user"})
    @GetMapping("/getCosBucketAndRegion")
    public ApiResult getCosBucketAndRegion() {
        HashMap<String, String> cosBucketAndRegion = TencentCOSUtil.getCosBucketAndRegion();
        return ApiResult.success(cosBucketAndRegion, "获取成功");
    }
}
