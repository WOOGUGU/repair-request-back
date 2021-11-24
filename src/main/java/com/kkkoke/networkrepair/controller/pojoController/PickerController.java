package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerTimeService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PickerController {
    private final PickerLocationService pickerLocationService;
    private final PickerTimeService pickerTimeService;

    public PickerController(PickerLocationService pickerLocationService, PickerTimeService pickerTimeService) {
        this.pickerLocationService = pickerLocationService;
        this.pickerTimeService = pickerTimeService;
    }

    // 增加报修地点
//    @PostMapping("/addPickerLocation")
//    public StatusAndDataFeedback addPickerLocation(PickerLocation pickerLocation) {
//        // 判断前端传过来的参数是否为空
//        if (Objects.equals(pickerLocation, null)) {
//            return new StatusAndDataFeedback(null, "Incomplete_data");
//        }
//
//        // 查看数据库中是否已经存在此报修地点
//        if (Objects.equals(pickerLocationService.selectPickerLocation(pickerLocation.getId())), null) {
//
//        }
//    }
}
