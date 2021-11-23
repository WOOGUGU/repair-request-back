package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.pojo.PickerTime;

import java.util.List;

public interface PickerTimeService {
    // 增加报修时间段
    int addPickerTime(PickerTime pickerTime);

    // 删除报修时间段
    int deletePickerTime(Integer id);

    // 根据id超找某个时间段
    PickerTime selectPickerTime(Integer id);

    // 查找所有时间段
    List<PickerTime> selectAllPickerTime();

    // 修改报修时间段
    Integer updatePickerTime(PickerTime pickerTime);
}
