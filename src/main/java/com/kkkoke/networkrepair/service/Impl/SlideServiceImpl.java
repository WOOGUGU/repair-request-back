package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.SlideDao;
import com.kkkoke.networkrepair.pojo.Slide;
import com.kkkoke.networkrepair.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideDao slideDao;

    @Override
    public Slide uploadSlide(String imgPath, String submitTime, String author) {
        Slide slide = new Slide(imgPath, submitTime, author);
        slideDao.uploadSlide(slide);
        return slide;
    }

    @Override
    public int deleteSlide(Integer id) {
        return 0;
    }

    @Override
    public Slide selectSlideById(Integer id) {
        return null;
    }

    @Override
    public List<Slide> selectAllSlide() {
        return null;
    }

    @Override
    public Integer updateSlide(Slide slide) {
        return null;
    }
}
