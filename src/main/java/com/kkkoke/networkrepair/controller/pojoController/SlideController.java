package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.SlideService;
import com.kkkoke.networkrepair.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
    @ApiImplicitParams({@ApiImplicitParam(name = "img", value = "图片", required = true, paramType = "query"),
            @ApiImplicitParam(name = "author", value = "上传者", required = true, paramType = "query"),
            @ApiImplicitParam(name = "submitTime", value = "上传时间", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/uploadSlide")
    public ApiResult uploadSlide(@RequestParam("slideImg") MultipartFile file, String author, String submitTime) {
        String originalFilename = file.getOriginalFilename();
        // 截取图片后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 获取当前时间戳为上传的图片重命名
        String filename = String.valueOf(System.currentTimeMillis());
        filename += suffix;
        // 定义上传文件保存路径
        String path = propertiesUtil.getSlideImgPath();
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }

        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        slideService.uploadSlide(path + filename, submitTime, author);

        return ApiResult.success("轮播图上传成功");
    }
}
