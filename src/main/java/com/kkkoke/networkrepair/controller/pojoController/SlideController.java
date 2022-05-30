package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Slide;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.SlideService;
import com.kkkoke.networkrepair.util.FileUploadUtil;
import com.kkkoke.networkrepair.util.PropertiesUtil;
import com.kkkoke.networkrepair.util.TencentCOSUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author kkkoke
 */
@Api(tags = "轮播图管理")
@Slf4j
@Validated
@RequestMapping("/v2/slide")
@RestController
public class SlideController {

    private final SlideService slideService;

    private final PropertiesUtil propertiesUtil;

    @Autowired
    public SlideController(SlideService slideService, PropertiesUtil propertiesUtil) {
        this.slideService = slideService;
        this.propertiesUtil = propertiesUtil;
    }

    @ApiOperation(value = "上传轮播图")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "轮播图", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/uploadSlide")
    public ApiResult uploadSlide(MultipartFile file, @NotBlank(message = "author can not be null") String author) {
        String imgPath = FileUploadUtil.fileUpload(file, propertiesUtil.getSlideImgPath());
        slideService.uploadSlide(imgPath, author);
        return ApiResult.success("上传成功");
    }

    @ApiOperation(value = "上传轮播图 流类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "fileStreams", value = "轮播图流数组", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/uploadStreamSlide")
    public ApiResult uploadStreamSlide(MultipartFile[] fileStreams, String author) {
        try {
            for (MultipartFile fileStream : fileStreams) {
                String fileKey = "slide/" + System.currentTimeMillis() + fileStream.getOriginalFilename().substring(fileStream.getOriginalFilename().lastIndexOf("."));
                TencentCOSUtil.upLoadFileStream("slide", fileKey, fileStream.getInputStream());
                URL url = TencentCOSUtil.getObjectUrl("slide", fileKey);
                slideService.uploadSlide(url.toString(), author);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ApiResult.success("上传成功");
    }

    @ApiOperation(value = "通过id删除轮播图")
    @ApiImplicitParam(name = "slideId", value = "轮播图Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteSlide")
    public ApiResult deleteSlide(@NotNull(message = "slideId can not be null") Integer slideId) throws DataHasNotExistedException {
        slideService.deleteSlide(slideId);
        return ApiResult.success("轮播图删除成功");
    }

    @ApiOperation(value = "通过id查找轮播图")
    @ApiImplicitParam(name = "slideId", value = "轮播图Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectSlideById")
    public ApiResult selectSlideById(@NotNull(message = "slideId can not be null") Integer slideId) throws DataHasNotExistedException {
        Slide slide = slideService.selectSlideById(slideId);
        return ApiResult.success(slide, "查找成功");
    }

    @ApiOperation(value = "查找轮播图 后台接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "slideId", value = "轮播图Id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectSlide")
    public ApiResult selectSlide(Integer slideId, String author) {
        List<Slide> slides = slideService.selectSlide(slideId, author);
        return ApiResult.success(slides, "查找成功");
    }

    @ApiOperation(value = "查找所有轮播图")
    @GetMapping("/selectAllSlide")
    public ApiResult selectAllSlide() throws DataHasNotExistedException {
        List<Slide> slides = slideService.selectAllSlide();
        return ApiResult.success(slides, "查找成功");
    }

    @ApiOperation(value = "修改轮播图")
    @ApiImplicitParams({@ApiImplicitParam(name = "slideId", value = "轮播图Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "file", value = "轮播图", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateSlide")
    public ApiResult updateSlide(@NotNull(message = "slideId can not be null") Integer slideId, MultipartFile file,
                                 @NotBlank(message = "author can not be null") String author) throws DataHasNotExistedException {
        String imgPath = FileUploadUtil.fileUpload(file, propertiesUtil.getSlideImgPath());
        slideService.updateSlide(slideId, imgPath, author);
        return ApiResult.success("轮播图修改完成");
    }
}
