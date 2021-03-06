package com.kkkoke.networkrepair.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.kkkoke.networkrepair.dao.PickerLocationDao;
import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.helper.NameAndPosition;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.PickerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PickerLocationServiceImpl implements PickerLocationService {
    @Autowired
    private PickerLocationDao pickerLocationDao;

    // 增加报修地点
    @Override
    public PickerLocation addPickerLocation(String area, String position) throws DataHasExistedException {
        // 查看数据库中是否已经存在此报修地点
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocationByPosition(position))) {
            PickerLocation pickerLocation = new PickerLocation(area, position);
            pickerLocationDao.addPickerLocation(pickerLocation);
            return pickerLocation;
        } else {
            // picker已存在
            throw new DataHasExistedException("PickerLocation has existed");
        }
    }

    // 删除报修地点
    @Override
    public int deletePickerLocation(Integer pickerId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的报修地点是否存在
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocation(pickerId))) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            return pickerLocationDao.deletePickerLocation(pickerId);
        }
    }

    // 根据id查找某个报修地点
    @Override
    public PickerLocation selectPickerLocation(Integer pickerId) throws DataHasNotExistedException {
        // 根据用户名查找pickerLocation
        PickerLocation pickerLocation = pickerLocationDao.selectPickerLocation(pickerId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocation)) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            return pickerLocation;
        }
    }

    // 根据area查找报修地点
    @Override
    public List<PickerLocation> selectPickerLocationByArea(String area) throws DataHasNotExistedException {
        // 根据area查找PickerLocations
        List<PickerLocation> pickerLocations = pickerLocationDao.selectPickerLocationByArea(area);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocations)) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            return pickerLocations;
        }
    }

    // 根据position查找报修地点
    @Override
    public PickerLocation selectPickerLocationByPosition(String position) throws DataHasNotExistedException {
        // 根据position查找PickerLocation
        PickerLocation pickerLocation = pickerLocationDao.selectPickerLocationByPosition(position);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocation)) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            return pickerLocation;
        }
    }

    // 查找报修地点 后台接口
    @Override
    public ResultPage<PickerLocation> selectLocationForBackend(Integer pickerId, String area, String position, Integer pageNum, Integer pageSize) {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PickerLocation> pickerLocations = pickerLocationDao.selectLocationForBackend(pickerId, area, position);
        return ResultPage.restPage(pickerLocations);
    }

    // 查找所有报修地点
    @Override
    public Object selectAllPickerLocation() throws DataHasNotExistedException {
        List<PickerLocation> pickerLocations = pickerLocationDao.selectAllPickerLocation();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(pickerLocations)) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            HashMap<String, List<String>> map = new HashMap<>();
            for (PickerLocation pickerLocation : pickerLocations) {
                if (!map.containsKey(pickerLocation.getArea())) {
                    map.put(pickerLocation.getArea(), new ArrayList<>());
                }
                map.get(pickerLocation.getArea()).add(pickerLocation.getPosition());
            }
            List<NameAndPosition> res = new ArrayList<>();
            for (String s : map.keySet()) {
                res.add(new NameAndPosition(s, map.get(s)));
            }
            return JSON.toJSON(res);
        }
    }

    // 修改报修地点
    @Override
    public PickerLocation updatePickerLocation(Integer pickerId, String area, String position) throws DataHasNotExistedException {
        // 创建要修改的PickerLocation对象
        PickerLocation pickerLocation = new PickerLocation(pickerId, area, position);
        // 查找数据库中是否存在
        if (ObjectUtils.isEmpty(pickerLocationDao.selectPickerLocation(pickerId))) {
            throw new DataHasNotExistedException("PickerLocation has not existed");
        } else {
            // 如果PickerLocation存在就更新数据
            pickerLocationDao.updatePickerLocation(pickerLocation);
            return pickerLocation;
        }
    }
}
