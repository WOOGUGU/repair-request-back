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

    // 增加picker
    @Override
    public Picker addPicker(String type, String value) throws DataHasExistedException {
        // 查看数据库中是否已经存在此报修时间段
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByValue(value))) {
            Picker picker = new Picker(type, value);
            pickerDao.addPicker(picker);
            return picker;
        } else {
            // picker已存在
            throw new DataHasExistedException("Picker has existed");
        }
    }

    // 通过Id删除picker
    @Override
    public int deletePickerById(Integer pickerId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerDao.selectPickerById(pickerId))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            return pickerDao.deletePickerById(pickerId);
        }
    }

    // 通过value删除故障类型
    @Override
    public int deletePickerByValue(String value) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerDao.selectPickerByValue(value))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            return pickerDao.deletePickerByValue(value);
        }
    }

    // 根据id查找某个picker
    @Override
    public Picker selectPickerById(Integer pickerId) throws DataHasNotExistedException {
        Picker picker = pickerDao.selectPickerById(pickerId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(picker)) {
            throw new DataHasNotExistedException("Picker has existed");
        } else {
            return picker;
        }
    }

    // 根据value查找某个报修时间段
    @Override
    public Picker selectPickerByValue(String value) throws DataHasNotExistedException {
        Picker picker = pickerDao.selectPickerByValue(value);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(picker)) {
            throw new DataHasNotExistedException("PickerTime has not existed");
        } else {
            return picker;
        }
    }

    // 查找所有time
    @Override
    public List<PickerResult> selectAllPickerTime() throws DataHasNotExistedException {
        List<PickerResult> pickers = pickerDao.selectAllPickerTime();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickers)) {
            throw new DataHasNotExistedException("PickerType has not existed");
        } else {
            return pickers;
        }
    }

    // 查找所有time
    @Override
    public List<PickerResult> selectAllPickerDes() throws DataHasNotExistedException {
        List<PickerResult> pickers = pickerDao.selectAllPickerDes();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickers)) {
            throw new DataHasNotExistedException("PickerType has not existed");
        } else {
            return pickers;
        }
    }

    // 查找所有picker
    @Override
    public HashMap<String, List<PickerResult>> selectAllPicker() throws DataHasNotExistedException {
        List<PickerResult> pickerTimes = pickerDao.selectAllPickerTime();
        List<PickerResult> pickerDess = pickerDao.selectAllPickerDes();
        if (ObjectUtils.isEmpty(pickerDess) && ObjectUtils.isEmpty(pickerTimes)) {
            throw new DataHasNotExistedException("PickerType has not existed");
        }
        HashMap<String, List<PickerResult>> resultHashMap = new HashMap<>();
        resultHashMap.put("time", pickerTimes);
        resultHashMap.put("des", pickerDess);
        return resultHashMap;
    }

    // 修改picker
    @Override
    public Picker updatePicker(Integer pickerId, String type, String value) throws DataHasNotExistedException {
        // 创建要修改的Picker对象
        Picker picker = new Picker(pickerId, type, value);
        // 查找数据库中是否存在此picker
        if (ObjectUtils.isEmpty(pickerDao.selectPickerById(pickerId))) {
            throw new DataHasNotExistedException("Picker has not existed");
        } else {
            // 如果存在就更新数据
            pickerDao.updatePicker(picker);
            return picker;
        }
    }
}
