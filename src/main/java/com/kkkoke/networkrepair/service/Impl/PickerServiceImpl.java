package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.PickerDao;
import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;
import com.kkkoke.networkrepair.service.PickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PickerServiceImpl implements PickerService {

    @Autowired
    private PickerDao pickerDao;

    // 增加报修时间段
    @Override
    public Picker addPickerTime(String time) throws DataHasExistedException {
        // 查看数据库中是否已经存在此报修时间段
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByTime(time))) {
            Picker pickerTime = new Picker(time, null);
            pickerDao.addPickerTime(pickerTime);
            return pickerTime;
        } else {
            // picker已存在
            throw new DataHasExistedException("Picker has existed");
        }
    }

    // 增加故障类型
    @Override
    public Picker addPickerType(String type) throws DataHasExistedException {
        // 查看数据库中是否已经存在此故障类型
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByType(type))) {
            Picker pickerType = new Picker(null, type);
            pickerDao.addPickerType(pickerType);
            return pickerType;
        } else {
            // picker已存在
            throw new DataHasExistedException("Picker has existed");
        }
    }

    // 删除报修时间段
    @Override
    public int deletePickerTime(String time) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByTime(time))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            return pickerDao.deletePickerTime(time);
        }
    }

    // 删除故障类型
    @Override
    public int deletePickerType(String type) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByType(type))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            return pickerDao.deletePickerType(type);
        }
    }

    // 删除故障类型
    @Override
    public int deletePickerById(Integer pickerId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerDao.selectPickerById(pickerId))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            return pickerDao.deletePickerById(pickerId);
        }
    }

    // 根据id查找某个picker
    @Override
    public Picker selectPickerById(Integer pickerId) throws DataHasNotExistedException {
        // 根据用户名查找pickerTime
        Picker picker = pickerDao.selectPickerById(pickerId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(picker)) {
            throw new DataHasNotExistedException("Picker has existed");
        } else {
            return picker;
        }
    }

    // 根据time查找某个报修时间段
    @Override
    public PickerResult selectPickerByTime(String time) throws DataHasNotExistedException {
        // 根据area查找PickerTime
        PickerResult pickerTime = pickerDao.selectPickerByTime(time);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerTime)) {
            throw new DataHasNotExistedException("PickerTime has not existed");
        } else {
            return pickerTime;
        }
    }

    // 根据time查找某个故障类型
    @Override
    public PickerResult selectPickerByType(String type) throws DataHasNotExistedException {
        // 根据area查找PickerType
        PickerResult pickerType = pickerDao.selectPickerByType(type);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerType)) {
            throw new DataHasNotExistedException("PickerType has not existed");
        } else {
            return pickerType;
        }
    }

    // 查找所有picker
    @Override
    public HashMap<String, List<PickerResult>> selectAllPicker() throws DataHasNotExistedException {
        List<PickerResult> pickerTimes = pickerDao.selectAllPickerTime();
        List<PickerResult> pickerTimesNotBlank = new ArrayList<>();
        for (PickerResult picker : pickerTimes) {
            if (!ObjectUtils.isEmpty(picker.getPicker())) {
                pickerTimesNotBlank.add(picker);
            }
        }
        List<PickerResult> pickerTypes = pickerDao.selectAllPickerType();
        List<PickerResult> pickerTypesNotBlank = new ArrayList<>();
        for (PickerResult picker : pickerTypes) {
            if (!ObjectUtils.isEmpty(picker.getPicker())) {
                pickerTypesNotBlank.add(picker);
            }
        }
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerTimes) && ObjectUtils.isEmpty(pickerTypes)) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            HashMap<String, List<PickerResult>> resultHashMap = new HashMap<>();
            resultHashMap.put("times", pickerTimesNotBlank);
            resultHashMap.put("types", pickerTypesNotBlank);
            return resultHashMap;
        }
    }

    // 修改时间段
    @Override
    public Picker updatePickerTime(Integer pickerId, String time) throws DataHasNotExistedException {
        // 创建要修改的Picker对象
        Picker pickerTime = new Picker(pickerId, time, null);
        // 查找数据库中是否存在此PickerTime
        if (ObjectUtils.isEmpty(pickerDao.selectPickerById(pickerId))) {
            throw new DataHasNotExistedException("PickerTime has not existed");
        } else {
            // 如果存在就更新数据
            pickerDao.updatePickerTime(pickerTime);
            return pickerTime;
        }
    }

    // 修改故障类型
    @Override
    public Picker updatePickerType(Integer pickerId, String type) throws DataHasNotExistedException {
        // 创建要修改的Picker对象
        Picker pickerType = new Picker(pickerId, null, type);
        // 查找数据库中是否存在此PickerType
        if (ObjectUtils.isEmpty(pickerDao.selectPickerById(pickerId))) {
            throw new DataHasNotExistedException("PickerType has not existed");
        } else {
            // 如果存在就更新数据
            pickerDao.updatePickerType(pickerType);
            return pickerType;
        }
    }
}
