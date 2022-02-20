package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;

import java.util.HashMap;
import java.util.List;

public interface PickerService {

    // 增加picker
    Picker addPicker(String type, String value) throws DataHasExistedException;

    // 通过id删除picker
    int deletePickerById(Integer pickerId) throws DataHasNotExistedException;

    // 通过value删除故障类型
    int deletePickerByValue(String value) throws DataHasNotExistedException;

    // 根据id查找某个Picker
    Picker selectPickerById(Integer id) throws DataHasNotExistedException;

    // 根据value查找某个报修时间段
    PickerResult selectPickerByValue(String Value) throws DataHasNotExistedException;

    // 查找所有报修时间段
    List<PickerResult> selectAllPickerTime() throws DataHasNotExistedException;

    // 查找所有故障描述
    List<PickerResult> selectAllPickerDes() throws DataHasNotExistedException;

    // 查找所有picker
    HashMap<String, List<PickerResult>> selectAllPicker() throws DataHasNotExistedException;

    // 修改picker
    Picker updatePicker(Integer pickerId, String type, String value) throws DataHasNotExistedException;
}
