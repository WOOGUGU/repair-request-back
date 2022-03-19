package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;

import java.util.List;

public interface PickerLocationService {
    // 增加报修地点
    PickerLocation addPickerLocation(String area, String position) throws DataHasExistedException;

    // 删除报修地点
    int deletePickerLocation(Integer pickerId) throws DataHasNotExistedException;

    // 根据id查找某个报修地点
    PickerLocation selectPickerLocation(Integer pickerId) throws DataHasNotExistedException;

    // 根据area查找报修地点
    List<PickerLocation> selectPickerLocationByArea(String area) throws DataHasNotExistedException;

    // 根据position查找报修地点
    PickerLocation selectPickerLocationByPosition(String position) throws DataHasNotExistedException;

    // 查找报修地点 后台接口
    List<PickerLocation> selectLocationForBackend(Integer pickerId, String area, String position);

    // 查找所有报修地点
    Object selectAllPickerLocation() throws DataHasNotExistedException;

    // 修改报修地点
    PickerLocation updatePickerLocation(Integer pickerId, String area, String position) throws DataHasNotExistedException;
}
