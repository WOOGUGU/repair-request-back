package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.PickerTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PickerTimeDao {
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
