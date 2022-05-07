package com.kkkoke.networkrepair.controller.fileController;

import com.kkkoke.networkrepair.pojo.helper.UserResult;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传（图片）
 *
 * @author WOOGUGU
 */

@Api(tags = "文件管理")
@Slf4j
@RequestMapping("/v2/article")
@RestController
public class FileController {

    private final String imageHome = "/home/projects/wrz-network-repair/images";

    @ApiOperation(value = "传单张图片")
    @PostMapping(value = "/fileUpload")
    public Object fileUpload(
            @RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserResult user = (UserResult) securityContext.getAuthentication().getPrincipal();

        if (file.isEmpty()) {
            log.error("无图片");
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        fileName = "/" + user.getUsername() + "/" + UUID.randomUUID() + suffixName;
        // 上传后的路径
        String filePath = imageHome + fileName;
        File dest = new File(filePath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error(String.valueOf(e));
            return ApiResult.fail("500", "上传失败", String.valueOf(e));
        }
        log.info("保存图片至==>" + filePath);
        //返回图片名称
        return ApiResult.success(fileName, "上传成功");
    }

    @ApiOperation(value = "传多张图片")
    @PostMapping("/filesUpload")
    public Object filesUpload(
            @RequestParam(value = "files") MultipartFile[] files,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        UserResult user = (UserResult) securityContext.getAuthentication().getPrincipal();

        List<String> list = new ArrayList<>();
        String fileName, suffixName, filePath;
        for (int i = 0; i < files.length; i++) {
            fileName = files[i].getOriginalFilename();
            assert fileName != null;
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = "/" + user.getUsername() + "/" + UUID.randomUUID() + suffixName;
            filePath = imageHome + fileName;
            java.io.File dest = new java.io.File(filePath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (IOException e) {
                log.error(String.valueOf(e));
                return ApiResult.fail("500", "上传失败", String.valueOf(e));
            }
            log.info("保存图片至==>" + filePath);
            list.add(fileName);
        }
        return ApiResult.success(list, "获取成功");
    }
}
