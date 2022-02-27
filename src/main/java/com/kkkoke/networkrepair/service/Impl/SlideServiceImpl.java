package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.SlideDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Slide;
import com.kkkoke.networkrepair.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideDao slideDao;

    @Override
    public Slide uploadSlide(String imgPath, String author) {
        String submitTime = LocalDateTime.now().toString();
        Slide slide = new Slide(imgPath, submitTime, author);
        slideDao.uploadSlide(slide);
        return slide;
    }

    @Override
    public int deleteSlide(Integer slideId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的轮播图是否存在
        if (ObjectUtils.isEmpty(slideDao.selectSlideById(slideId))) {
            throw new DataHasNotExistedException("Slide has not existed");
        } else {
            return slideDao.deleteSlide(slideId);
        }
    }

    @Override
    public Slide selectSlideById(Integer slideId)  throws DataHasNotExistedException {
        Slide slide = slideDao.selectSlideById(slideId);
        // 判断查找的轮播图是否为空
        if (ObjectUtils.isEmpty(slide)) {
            throw new DataHasNotExistedException("Slide has not existed");
        } else {
            return slide;
        }
    }

    @Override
    public List<Slide> selectAllSlide() throws DataHasNotExistedException {
        List<Slide> slides = slideDao.selectAllSlide();
        // 判断查找的轮播图是否为空
        if (ObjectUtils.isEmpty(slides)) {
            throw new DataHasNotExistedException("Slide has not existed");
        } else {
            return slides;
        }
    }

    @Override
    public List<Slide> selectSlide(Integer slideId, String author) {
        return slideDao.selectSlide(slideId, author);
    }

    @Override
    public Slide updateSlide(Integer slideId, String imgPath, String author) throws DataHasNotExistedException {
        String submitTime = LocalDateTime.now().toString();
        Slide slide = new Slide(slideId, imgPath, submitTime, author);
        if (ObjectUtils.isEmpty(slideDao.selectSlideById(slideId))) {
            throw new DataHasNotExistedException("Slide has not existed");
        } else {
            slideDao.updateSlide(slide);
            return slide;
        }
    }
}
