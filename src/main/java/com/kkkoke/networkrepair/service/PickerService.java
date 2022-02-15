package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;

import java.util.HashMap;
import java.util.List;

public interface PickerService {

    // 增加报修时间段
    Picker addPickerTime(String time) throws DataHasExistedException;

    // 增加故障类型
    Picker addPickerType(String type) throws DataHasExistedException;

    // 删除报修时间段
    int deletePickerTime(String time) throws DataHasNotExistedException;

    // 删除故障类型
    int deletePickerType(String type) throws DataHasNotExistedException;

    // 根据Id删除picker
    int deletePickerById(Integer pickerId) throws DataHasNotExistedException;

    // 根据id超找某个时间段
    Picker selectPickerById(Integer id) throws DataHasNotExistedException;

    // 根据time查找某个报修时间段
    PickerResult selectPickerByTime(String time) throws DataHasNotExistedException;

    // 根据type查找某个故障类型
    PickerResult selectPickerByType(String type) throws DataHasNotExistedException;

    // 查找所有时间段和故障类型
    HashMap<String, List<PickerResult>> selectAllPicker() throws DataHasNotExistedException;

    // 修改时间段
    Picker updatePickerTime(Integer pickerId, String time) throws DataHasNotExistedException;

    // 修改故障类型
    Picker updatePickerType(Integer pickerId, String type) throws DataHasNotExistedException;
}
