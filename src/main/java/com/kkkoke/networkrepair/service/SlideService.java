package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.pojo.Slide;

import java.util.List;

public interface SlideService {
    // 上传轮播图
    Slide uploadSlide(String imgPath, String submitTime, String author);

    // 通过id删除轮播图
    int deleteSlide(Integer id);

    // 通过id查找轮播图
    Slide selectSlideById(Integer id);

    // 查找所有轮播图
    List<Slide> selectAllSlide();

    // 修改轮播图
    Integer updateSlide(Slide slide);
}
