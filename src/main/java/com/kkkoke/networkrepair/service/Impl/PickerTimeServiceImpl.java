package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.PickerTimeDao;
import com.kkkoke.networkrepair.pojo.PickerTime;
import com.kkkoke.networkrepair.service.PickerTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickerTimeServiceImpl implements PickerTimeService {
    @Autowired
    private PickerTimeDao pickerTimeDao;

    @Override
    // 增加报修时间段
    public int addPickerTime(PickerTime pickerTime) {
        return pickerTimeDao.addPickerTime(pickerTime);
    }

    @Override
    // 删除报修时间段
    public int deletePickerTime(Integer id) {
        return pickerTimeDao.deletePickerTime(id);
    }

    @Override
    // 查找所有时间段
    public List<PickerTime> selectAllPickerTime() {
        return pickerTimeDao.selectAllPickerTime();
    }

    @Override
    // 修改报修时间段
    public Integer updatePickerTime(PickerTime pickerTime) {
        return pickerTimeDao.updatePickerTime(pickerTime);
    }
}
