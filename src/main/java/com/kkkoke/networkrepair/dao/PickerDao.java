package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PickerDao {
    // 增加picker
    int addPicker(Picker picker);

    // 通过id删除故障类型
    int deletePickerById(Integer id);

    // 通过value删除故障类型
    int deletePickerByValue(String value);

    // 根据id查找某个Picker
    Picker selectPickerById(Integer id);

    // 根据value查找某个报修时间段
    PickerResult selectPickerByValue(String Value);

    // 查找所有报修时间段
    List<PickerResult> selectAllPickerTime();

    // 查找所有故障描述
    List<PickerResult> selectAllPickerDes();

    // 修改picker
    Integer updatePicker(Picker picker);
}
