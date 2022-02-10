package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.PickerLocationDao;
import com.kkkoke.networkrepair.exception.PickerHasExistedException;
import com.kkkoke.networkrepair.exception.PickerHasNotExistedException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.service.PickerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

@Service
public class PickerLocationServiceImpl implements PickerLocationService {
    @Autowired
    private PickerLocationDao pickerLocationDao;

    // 增加报修地点
    @Override
    public PickerLocation addPickerLocation(String area, String position) throws PickerHasExistedException {
        // 查看数据库中是否已经存在此报修地点
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocationByPosition(position))) {
            PickerLocation pickerLocation = new PickerLocation(area, position);
            pickerLocationDao.addPickerLocation(pickerLocation);
            return pickerLocation;
        } else {
            // picker已存在
            throw new PickerHasExistedException("Picker has existed");
        }
    }

    // 删除报修地点
    @Override
    public int deletePickerLocation(Integer pickerId) throws PickerHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocation(pickerId))) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerLocationDao.deletePickerLocation(pickerId);
        }
    }

    // 根据id查找某个报修地点
    @Override
    public PickerLocation selectPickerLocation(Integer pickerId) throws PickerHasNotExistedException {
        // 根据用户名查找pickerLocation
        PickerLocation pickerLocation = pickerLocationDao.selectPickerLocation(pickerId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocation)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerLocation;
        }
    }

    // 根据area查找报修地点
    @Override
    public List<PickerLocation> selectPickerLocationByArea(String area) throws PickerHasNotExistedException {
        // 根据area查找PickerLocations
        List<PickerLocation> pickerLocations = pickerLocationDao.selectPickerLocationByArea(area);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocations)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerLocations;
        }
    }

    // 根据position查找报修地点
    @Override
    public PickerLocation selectPickerLocationByPosition(String position) throws PickerHasNotExistedException {
        // 根据position查找PickerLocation
        PickerLocation pickerLocation = pickerLocationDao.selectPickerLocationByPosition(position);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocation)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerLocation;
        }
    }

    // 查找所有报修地点
    @Override
    public List<PickerLocation> selectAllPickerLocation() throws PickerHasNotExistedException {
        List<PickerLocation> pickerLocations = pickerLocationDao.selectAllPickerLocation();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocations)) {
            throw new PickerHasNotExistedException();
        } else {
            return pickerLocations;
        }
    }

    // 修改报修地点
    @Override
    public PickerLocation updatePickerLocation(Integer pickerId, String area, String position) throws PickerHasNotExistedException {
        // 创建要修改的PickerLocation对象
        PickerLocation pickerLocation = new PickerLocation(pickerId, area, position);
        // 查找数据库中是否存在
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocation(pickerId))) {
            throw new PickerHasNotExistedException();
        } else {
            // 如果PickerLocation存在就更新数据
            pickerLocationDao.updatePickerLocation(pickerLocation);
            return pickerLocation;
        }
    }
}
