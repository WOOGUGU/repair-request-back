package com.kkkoke.networkrepair.controller.pojoController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.pojo.NameAndPosition;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.PickerTime;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerTimeService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.TokenVerify;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class PickerController {
    private final PickerLocationService pickerLocationService;
    private final PickerTimeService pickerTimeService;
    private final TokenVerify tokenVerifyForUser;
    private final TokenVerify tokenVerifyForAdmin;

    public PickerController(PickerLocationService pickerLocationService,
                            PickerTimeService pickerTimeService,
                            @Qualifier("userTokenVerifyImpl") TokenVerify tokenVerifyForUser,
                            @Qualifier("adminTokenVerifyImpl") TokenVerify tokenVerifyForAdmin) {
        this.pickerLocationService = pickerLocationService;
        this.pickerTimeService = pickerTimeService;
        this.tokenVerifyForUser = tokenVerifyForUser;
        this.tokenVerifyForAdmin = tokenVerifyForAdmin;
    }

    // 增加报修地点
    @PostMapping("/addPickerLocation")
    public StatusAndDataFeedback addPickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String area = (String) Json.get("area"); // 获取网络报修区域
        String position = (String) Json.get("position"); // 获取网络报修位置
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查看数据库中是否已经存在此报修地点
            if (Objects.equals(pickerLocationService.selectPickerLocationByArea(area), null)) {
                // 创建添加的PickerLocation对象
                PickerLocation pickerLocation = new PickerLocation(area, position);
                // 调用service层添加PickerLocation对象
                pickerLocationService.addPickerLocation(pickerLocation);
                return new StatusAndDataFeedback(pickerLocation, "handle_success");
            } else {
                return new StatusAndDataFeedback(null, "data_exist");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 删除报修地点
    @PostMapping("/deletePickerLocation")
    public StatusAndDataFeedback deletePickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt((String) Json.get("id"));
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查询数据库，查看要删除的PickerLocation是否存在
            if (Objects.equals(pickerLocationService.selectPickerLocation(id), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                // 调用service层删除PickerLocation
                pickerLocationService.deletePickerLocation(id);
            }
            return new StatusAndDataFeedback(null, "handle_success");
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 根据id查找某个报修地点
    @PostMapping("/selectPickerLocation")
    public StatusAndDataFeedback selectPickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt((String) Json.get("id"));
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据id查找PickerLocation
            PickerLocation pickerLocation = pickerLocationService.selectPickerLocation(id);
            // 判断查询结果是否为空
            if (Objects.equals(pickerLocation, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerLocation, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 根据area查找报修地点
    @PostMapping("/selectPickerLocationByArea")
    public StatusAndDataFeedback selectPickerLocationByArea(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String area = (String) Json.get("area");
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据area查找PickerLocations
            List<PickerLocation> pickerLocations = pickerLocationService.selectPickerLocationByArea(area);
            // 判断查询结果是否为空
            if (Objects.equals(pickerLocations, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerLocations, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 根据position查找报修地点
    @PostMapping("/selectPickerLocationByPosition")
    public StatusAndDataFeedback selectPickerLocationByPosition(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String position = (String) Json.get("position");
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据position查找PickerLocation
            PickerLocation pickerLocation = pickerLocationService.selectPickerLocationByPosition(position);
            // 判断查询结果是否为空
            if (Objects.equals(pickerLocation, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerLocation, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 为用户查找所有报修地点
    @PostMapping("/selectAllPickerLocationForUser")
    public StatusAndDataFeedback selectAllPickerLocationForUser(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForUser.verify(token)) {
            // token验证成功，查找所有PickerLocation
            List<PickerLocation> pickerLocations = pickerLocationService.selectAllPickerLocation();
            HashMap<String, List<String>> map = new HashMap<>();
            for (PickerLocation pickerLocation : pickerLocations) {
                if (!map.containsKey(pickerLocation.getArea())) {
                    map.put(pickerLocation.getArea(), new ArrayList<>());
                }
                map.get(pickerLocation.getArea()).add(pickerLocation.getPosition());
            }
            // 判断查询结果是否为空
            if (Objects.equals(pickerLocations, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                List<NameAndPosition> res = new ArrayList<>();
                for (String s : map.keySet()) {
                    res.add(new NameAndPosition(s, map.get(s)));
                }
                return new StatusAndDataFeedback(JSON.toJSON(res), "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 为管理员查找所有报修地点
    @PostMapping("/selectAllPickerLocationForAdmin")
    public StatusAndDataFeedback selectAllPickerLocationForAdmin(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查找所有PickerLocation
            List<PickerLocation> pickerLocations = pickerLocationService.selectAllPickerLocation();
            // 判断查询结果是否为空
            if (Objects.equals(pickerLocations, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerLocations, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 修改报修地点
    @PostMapping("/updatePickerLocation")
    public StatusAndDataFeedback updatePickerLocation(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt(Json.get("id").toString()); // PickerLocation的id
        String area = (String) Json.get("area");
        String position = (String) Json.get("position");
        String token = (String) Json.get("token"); // 获取待解析的token
        // 创建修改的PickerLocation对象
        PickerLocation pickerLocation = new PickerLocation(id, area, position);
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查找数据库中是否存在此工单
            if (Objects.equals(pickerLocationService.selectPickerLocation(id), null)) {
                return new StatusAndDataFeedback(pickerLocation, "data_not_exist");
            } else {
                // 如果此工单存在就更新数据
                pickerLocationService.updatePickerLocation(pickerLocation);
                return new StatusAndDataFeedback(pickerLocation, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 增加报修时间段
    @PostMapping("/addPickerTime")
    public StatusAndDataFeedback addPickerTime(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String time = (String) Json.get("time"); // 获取网络报修空余的时间段
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查看数据库中是否已经存在此报修地点
            if (Objects.equals(pickerTimeService.selectPickerTimeByTime(time), null)) {
                // 创建添加的PickerTime对象
                PickerTime pickerTime = new PickerTime(time);
                // 调用service层添加PickerTime对象
                pickerTimeService.addPickerTime(pickerTime);
                return new StatusAndDataFeedback(pickerTime, "handle_success");
            } else {
                return new StatusAndDataFeedback(null, "data_exist");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 删除报修时间段
    @PostMapping("/deletePickerTime")
    public StatusAndDataFeedback deletePickerTime(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt((String) Json.get("id"));
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查询数据库，查看要删除的PickerTime是否存在
            if (Objects.equals(pickerTimeService.selectPickerTime(id), null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                // 调用service层删除PickerTime
                pickerTimeService.deletePickerTime(id);
            }
            return new StatusAndDataFeedback(null, "handle_success");
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 根据id超找某个时间段
    @PostMapping("/selectPickerTime")
    public StatusAndDataFeedback selectPickerTime(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt((String) Json.get("id"));
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据id查找PickerTime
            PickerTime pickerTime = pickerTimeService.selectPickerTime(id);
            // 判断查询结果是否为空
            if (Objects.equals(pickerTime, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerTime, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 根据time查找某个报修时间段
    @PostMapping("/selectPickerTimeByTime")
    public StatusAndDataFeedback selectPickerTimeByTime(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String time = (String) Json.get("time");
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，根据time查找PickerTime
            PickerTime pickerTime = pickerTimeService.selectPickerTimeByTime(time);
            // 判断查询结果是否为空
            if (Objects.equals(pickerTime, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerTime, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 为用户查找所有时间段
    @PostMapping("/selectAllPickerTimeForUser")
    public StatusAndDataFeedback selectAllPickerTimeForUser(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForUser.verify(token)) {
            // token验证成功，查找所有PickerTime
            List<PickerTime> pickerTimes = pickerTimeService.selectAllPickerTime();
            // 判断查询结果是否为空
            if (Objects.equals(pickerTimes, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerTimes.stream().sorted(), "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 为管理员查找所有时间段
    @PostMapping("/selectAllPickerTimeForAdmin")
    public StatusAndDataFeedback selectAllPickerTimeForAdmin(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        String token = (String) Json.get("token"); // 获取待解析的token
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查找所有PickerTime
            List<PickerTime> pickerTimes = pickerTimeService.selectAllPickerTime();
            // 判断查询结果是否为空
            if (Objects.equals(pickerTimes, null)) {
                return new StatusAndDataFeedback(null, "data_not_exist");
            } else {
                return new StatusAndDataFeedback(pickerTimes.stream().sorted(), "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }

    // 修改报修时间段
    @PostMapping("/updatePickerTime")
    public StatusAndDataFeedback updatePickerTime(@RequestBody JSONObject Json) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(Json.toJSONString(), "{}")) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 获取Json中的数据
        Integer id = Integer.parseInt(Json.get("id").toString()); // PickerLocation的id
        String time = (String) Json.get("time");
        String token = (String) Json.get("token"); // 获取待解析的token
        // 创建修改的PickerLocation对象
        PickerTime pickerTime = new PickerTime(id, time);
        // 验证token的正确性
        if (tokenVerifyForAdmin.verify(token)) {
            // token验证成功，查找数据库中是否存在此工单
            if (Objects.equals(pickerTimeService.selectPickerTime(id), null)) {
                return new StatusAndDataFeedback(pickerTime, "data_not_exist");
            } else {
                // 如果此工单存在就更新数据
                pickerTimeService.updatePickerTime(pickerTime);
                return new StatusAndDataFeedback(pickerTime, "handle_success");
            }
        } else {
            // token验证失败，返回错误码
            return new StatusAndDataFeedback(null, "wrong_token");
        }
    }
}
