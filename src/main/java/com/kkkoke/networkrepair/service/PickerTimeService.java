package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerTime;

import java.util.List;

public interface PickerTimeService {
    // 增加报修时间段
    PickerTime addPickerTime(String time) throws DataHasExistedException;

    // 删除报修时间段
    int deletePickerTime(Integer pickerId) throws DataHasNotExistedException;

    // 根据id查找某个时间段
    PickerTime selectPickerTime(Integer pickerId) throws DataHasNotExistedException;

    // 根据time查找某个报修时间段
    PickerTime selectPickerTimeByTime(String time) throws DataHasNotExistedException;

    // 查找所有时间段
    List<PickerTime> selectAllPickerTime() throws DataHasNotExistedException;

    // 修改报修时间段
    PickerTime updatePickerTime(Integer pcikerId, String time) throws DataHasNotExistedException;
}
