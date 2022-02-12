package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Slide;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.SlideService;
import com.kkkoke.networkrepair.util.FileUploadUtil;
import com.kkkoke.networkrepair.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "submitTime", value = "上传时间", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/uploadSlide")
    public ApiResult uploadSlide(@RequestParam("slideImg") MultipartFile file, @NotBlank(message = "author can not be null") String author,
                                 @NotBlank(message = "submitTime can not be null") String submitTime) {
        String imgPath = FileUploadUtil.fileUpload(file, propertiesUtil.getSlideImgPath());
        slideService.uploadSlide(imgPath, submitTime, author);

        return ApiResult.success("轮播图上传成功");
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

    @ApiOperation(value = "查找所有轮播图")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectAllSlide")
    public ApiResult selectAllSlide() throws DataHasNotExistedException {
        List<Slide> slides = slideService.selectAllSlide();
        return ApiResult.success(slides, "查找成功");
    }

    @ApiOperation(value = "修改轮播图")
    @ApiImplicitParams({@ApiImplicitParam(name = "slideId", value = "轮播图Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "file", value = "轮播图", required = true, paramType = "query"),
            @ApiImplicitParam(name = "submitTime", value = "上传时间", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "displayTime", value = "展示时间", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateSlide")
    public ApiResult updateSlide(@NotNull(message = "slideId can not be null") Integer slideId, @RequestParam("slideImg") MultipartFile file,
                                 @NotBlank(message = "submitTime can not be null") String submitTime,  @NotBlank(message = "author can not be null") String author,
                                 @NotBlank(message = "displayTime can not be null") String displayTime) throws DataHasNotExistedException {
        String imgPath = FileUploadUtil.fileUpload(file, propertiesUtil.getSlideImgPath());
        slideService.updateSlide(slideId, imgPath, submitTime, author, displayTime);
        return ApiResult.success("修改完成");
    }
}
