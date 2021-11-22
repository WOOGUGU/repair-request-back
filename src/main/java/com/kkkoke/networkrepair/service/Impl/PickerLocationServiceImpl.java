package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.PickerLocationDao;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.service.PickerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickerLocationServiceImpl implements PickerLocationService {
    @Autowired
    private PickerLocationDao pickerLocationDao;

    @Override
    // 增加报修地点
    public int addPickerLocation(PickerLocation pickerLocation) {
        return pickerLocationDao.addPickerLocation(pickerLocation);
    }

    @Override
    // 删除报修地点
    public int deletePickerLocation(Integer id) {
        return pickerLocationDao.deletePickerLocation(id);
    }

    @Override
    // 查找所有报修地点
    public List<PickerLocation> selectAllPickerLocation() {
        return pickerLocationDao.selectAllPickerLocation();
    }

    @Override
    // 修改报修地点
    public Integer updatePickerLocation(PickerLocation pickerLocation) {
        return pickerLocationDao.updatePickerLocation(pickerLocation);
    }
}
