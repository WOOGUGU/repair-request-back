package com.kkkoke.networkrepair.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PickerLocationDao {
    // 增加报修地点
    int addPickerLocation(String area, String position);

    // 删除报修地点

    // 查找所有报修地点

    // 修改报修地点
}
