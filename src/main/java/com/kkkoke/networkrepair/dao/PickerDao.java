package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PickerDao {
    // 增加报修时间段
    int addPickerTime(Picker picker);

    // 增加故障类型
    int addPickerType(Picker picker);

    // 删除报修时间段
    int deletePickerTime(String time);

    // 删除故障类型
    int deletePickerType(String type);

    // 根据Id删除picker
    int deletePickerById(Integer id);

    // 根据id超找某个Picker
    Picker selectPickerById(Integer id);

    // 根据time查找某个报修时间段
    PickerResult selectPickerByTime(String time);

    // 根据type查找某个报修时间段
    PickerResult selectPickerByType(String type);

    // 查找所有时间段
    List<PickerResult> selectAllPickerTime();

    // 查找所有故障类型
    List<PickerResult> selectAllPickerType();

    // 修改报修时间段
    Integer updatePickerTime(Picker picker);

    // 修改故障类型
    Integer updatePickerType(Picker picker);
}
