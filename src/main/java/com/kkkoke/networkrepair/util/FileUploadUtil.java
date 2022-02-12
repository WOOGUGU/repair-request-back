package com.kkkoke.networkrepair.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadUtil {
    /**
     * 文件上传
     * @param file 文件
     * @param path 文件保存路径
     * @return 文件位置
     */
    public static String fileUpload(MultipartFile file, String path) {
        String originalFilename = file.getOriginalFilename();
        // 截取图片后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 获取当前时间戳为上传的图片重命名
        String filename = String.valueOf(System.currentTimeMillis());
        filename += suffix;
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

        return path + filename;
    }
}
