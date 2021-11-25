package com.kkkoke.networkrepair.controller.pojoController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerTimeService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.TokenVerify;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
public class PickerController {
    private final PickerLocationService pickerLocationService;
    private final PickerTimeService pickerTimeService;
    private final TokenVerify tokenVerify;

    public PickerController(PickerLocationService pickerLocationService, PickerTimeService pickerTimeService, @Qualifier("adminTokenVerifyImpl") TokenVerify tokenVerify) {
        this.pickerLocationService = pickerLocationService;
        this.pickerTimeService = pickerTimeService;
        this.tokenVerify = tokenVerify;
    }

    // 增加报修地点
    @PostMapping("/addPickerLocation")
    public StatusAndDataFeedback addPickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String area = (String) Json.get("area"); // 获取网络报修区域
        String position = (String) Json.get("position"); // 获取网络报修位置
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，查看数据库中是否已经存在此报修地点
            if (Objects.equals(pickerLocationService.selectPickerLocationByArea(area), null)) {
                // 创建添加的PickerLocation对象
                PickerLocation pickerLocation = new PickerLocation(area, position);
                // 调用service层添加PickerLocation对象
                pickerLocationService.addPickerLocation(pickerLocation);
                return new StatusAndDataFeedback(pickerLocation, "handle_success");
            }
            else {
                return new StatusAndDataFeedback(null, "data_exist");
            }
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 删除报修地点
    @PostMapping("/deletePickerLocation")
    public StatusAndDataFeedback deletePickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt((String) Json.get("id"));
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerify.verify(token)) {
            // token验证成功，查询数据库，查看要删除的用户是否存在
            if (Objects.equals(pickerLocationService.selectPickerLocation(id), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            }
            else {
                // 调用service层删除PickerLocation
                pickerLocationService.deletePickerLocation(id);
            }
            return new StatusAndDataFeedback(null, "handle_success");
        }
        else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
}
