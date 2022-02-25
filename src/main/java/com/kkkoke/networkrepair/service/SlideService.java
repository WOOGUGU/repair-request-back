package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Slide;

import java.util.List;

public interface SlideService {
    // 上传轮播图
    Slide uploadSlide(String imgPath, String author);

    // 通过id删除轮播图
    int deleteSlide(Integer slideId) throws DataHasNotExistedException;

    // 通过id查找轮播图
    Slide selectSlideById(Integer slideId) throws DataHasNotExistedException;

    // 查找轮播图 后台接口
    List<Slide> selectSlide(Integer slideId, String author);

    // 查找所有轮播图
    List<Slide> selectAllSlide() throws DataHasNotExistedException;

    // 修改轮播图
    Slide updateSlide(Integer slideId, String imgPath, String author) throws DataHasNotExistedException;
}
