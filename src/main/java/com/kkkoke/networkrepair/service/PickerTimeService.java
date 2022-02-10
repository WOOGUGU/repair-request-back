package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.PickerHasExistedException;
import com.kkkoke.networkrepair.exception.PickerHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerTime;

import java.util.List;

public interface PickerTimeService {
    // 增加报修时间段
    PickerTime addPickerTime(String time) throws PickerHasExistedException;

    // 删除报修时间段
    int deletePickerTime(Integer pickerId) throws PickerHasNotExistedException;

    // 根据id查找某个时间段
    PickerTime selectPickerTime(Integer pickerId) throws PickerHasNotExistedException;

    // 根据time查找某个报修时间段
    PickerTime selectPickerTimeByTime(String time) throws PickerHasNotExistedException;

    // 查找所有时间段
    List<PickerTime> selectAllPickerTime() throws PickerHasNotExistedException;

    // 修改报修时间段
    PickerTime updatePickerTime(Integer pcikerId, String time) throws PickerHasNotExistedException;
}
