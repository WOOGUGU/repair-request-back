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
    // 根据id查找某个报修地点
    public PickerLocation selectPickerLocation(Integer id) {
        return pickerLocationDao.selectPickerLocation(id);
    }

    @Override
    // 根据area查找报修地点
    public List<PickerLocation> selectPickerLocationByArea(String area) {
        return pickerLocationDao.selectPickerLocationByArea(area);
    }

    @Override
    // 根据position查找报修地点
    public PickerLocation selectPickerLocationByPosition(String position) {
        return pickerLocationDao.selectPickerLocationByPosition(position);
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
