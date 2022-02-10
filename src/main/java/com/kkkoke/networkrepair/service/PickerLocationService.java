package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.PickerHasExistedException;
import com.kkkoke.networkrepair.exception.PickerHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface PickerLocationService {
    // 增加报修地点
    PickerLocation addPickerLocation(String area, String position) throws PickerHasExistedException;

    // 删除报修地点
    int deletePickerLocation(Integer pickerId) throws PickerHasNotExistedException;

    // 根据id查找某个报修地点
    PickerLocation selectPickerLocation(Integer pickerId) throws PickerHasNotExistedException;

    // 根据area查找报修地点
    List<PickerLocation> selectPickerLocationByArea(String area) throws PickerHasNotExistedException;

    // 根据position查找报修地点
    PickerLocation selectPickerLocationByPosition(String position) throws PickerHasNotExistedException;

    // 查找所有报修地点
    List<PickerLocation> selectAllPickerLocation() throws PickerHasNotExistedException;

    // 修改报修地点
    PickerLocation updatePickerLocation(Integer pickerId, String area, String position) throws PickerHasNotExistedException;
}
