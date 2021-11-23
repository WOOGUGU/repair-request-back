package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.pojo.PickerLocation;

import java.util.List;

public interface PickerLocationService {
    // 增加报修地点
    int addPickerLocation(PickerLocation pickerLocation);

    // 删除报修地点
    int deletePickerLocation(Integer id);

    // 根据id查找某个报修地点
    PickerLocation selectPickerLocation(Integer id);

    // 查找所有报修地点
    List<PickerLocation> selectAllPickerLocation();

    // 修改报修地点
    Integer updatePickerLocation(PickerLocation pickerLocation);
}
