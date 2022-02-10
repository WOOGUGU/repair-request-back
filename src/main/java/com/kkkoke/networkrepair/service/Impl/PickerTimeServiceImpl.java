package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.PickerTimeDao;
import com.kkkoke.networkrepair.exception.PickerHasExistedException;
import com.kkkoke.networkrepair.exception.PickerHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.PickerTime;
import com.kkkoke.networkrepair.service.PickerTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class PickerTimeServiceImpl implements PickerTimeService {

    @Autowired
    private PickerTimeDao pickerTimeDao;

    // 增加报修时间段
    @Override
    public PickerTime addPickerTime(String time) throws PickerHasExistedException {
        // 查看数据库中是否已经存在此报修时间段
        if (ObjectUtils.isEmpty(pickerTimeDao.selectPickerTimeByTime(time))) {
            PickerTime pickerTime = new PickerTime(time);
            pickerTimeDao.addPickerTime(pickerTime);
            return pickerTime;
        } else {
            // picker已存在
            throw new PickerHasExistedException("Picker has existed");
        }
    }

    // 删除报修时间段
    @Override
    public int deletePickerTime(Integer pickerId) throws PickerHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerTimeDao.selectPickerTime(pickerId))) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerTimeDao.deletePickerTime(pickerId);
        }
    }

    // 根据id查找某个时间段
    @Override
    public PickerTime selectPickerTime(Integer pickerId) throws PickerHasNotExistedException {
        // 根据用户名查找pickerTime
        PickerTime pickerTime = pickerTimeDao.selectPickerTime(pickerId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerTime)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerTime;
        }
    }

    // 根据time查找某个报修时间段
    @Override
    public PickerTime selectPickerTimeByTime(String time) throws PickerHasNotExistedException {
        // 根据area查找PickerTime
        PickerTime pickerTime = pickerTimeDao.selectPickerTimeByTime(time);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerTime)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerTime;
        }
    }

    // 查找所有时间段
    @Override
    public List<PickerTime> selectAllPickerTime() throws PickerHasNotExistedException {
        List<PickerTime> pickerTimes = pickerTimeDao.selectAllPickerTime();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerTimes)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerTimes;
        }
    }

    // 修改时间段
    @Override
    public PickerTime updatePickerTime(Integer pickerId, String time) throws PickerHasNotExistedException {
        // 创建要修改的PickerTime对象
        PickerTime pickerTime = new PickerTime(pickerId, time);
        // 查找数据库中是否存在此PickerTime
        if (ObjectUtils.isEmpty(pickerTimeDao.selectPickerTime(pickerId))) {
            throw new PickerHasNotExistedException();
        } else {
            // 如果存在就更新数据
            pickerTimeDao.updatePickerTime(pickerTime);
            return pickerTime;
        }
    }
}
