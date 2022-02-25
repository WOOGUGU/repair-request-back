package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Slide;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlideDao {
    // 上传轮播图
    int uploadSlide(Slide slide);

    // 通过id删除轮播图
    int deleteSlide(Integer id);

    // 通过id查找轮播图
    Slide selectSlideById(Integer id);

    // 查找所有轮播图
    List<Slide> selectAllSlide();

    // 查找轮播图 后台接口
    List<Slide> selectSlide(Integer id, String author);

    // 修改轮播图
    Integer updateSlide(Slide slide);
}
