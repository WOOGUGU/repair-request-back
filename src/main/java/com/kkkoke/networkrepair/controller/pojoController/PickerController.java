package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.PickerTime;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerTimeService;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kkkoke
 */
@Api(tags = "选择栏")
@Slf4j
@Validated
@RequestMapping("/v2/picker")
@RestController
public class PickerController {
    private final PickerLocationService pickerLocationService;
    private final PickerTimeService pickerTimeService;

    public PickerController(PickerLocationService pickerLocationService,
                            PickerTimeService pickerTimeService) {
        this.pickerLocationService = pickerLocationService;
        this.pickerTimeService = pickerTimeService;
    }

    @ApiOperation(value = "增加报修地点")
    @ApiImplicitParams({@ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")})
    @PostMapping("/addPickerLocation")
    public ApiResult addPickerLocation(@NotBlank(message = "area can not be null") String area,
                                       @NotBlank(message = "position can not be null") String position) throws DataHasExistedException {
        pickerLocationService.addPickerLocation(area, position);
        return ApiResult.success("报修地点添加成功");
    }

    @ApiOperation(value = "删除报修地点")
    @ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query")
    @PostMapping("/deletePickerLocation")
    public ApiResult deletePickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        pickerLocationService.deletePickerLocation(pickerId);
        return ApiResult.success("报修地点删除成功");
    }

    @ApiOperation(value = "根据id查找某个报修地点")
    @ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query")
    @GetMapping("/selectPickerLocation")
    public ApiResult selectPickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        PickerLocation pickerLocation = pickerLocationService.selectPickerLocation(pickerId);
        return ApiResult.success(pickerLocation, "查找成功");
    }

    @ApiOperation(value = "根据area查找报修地点")
    @ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query")
    @GetMapping("/selectPickerLocationByArea")
    public ApiResult selectPickerLocationByArea(@NotBlank(message = "area can not be null") String area) throws DataHasNotExistedException {
        List<PickerLocation> pickerLocations = pickerLocationService.selectPickerLocationByArea(area);
        return ApiResult.success(pickerLocations, "查找成功");
    }

    @ApiOperation(value = "根据position查找报修地点")
    @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")
    @GetMapping("/selectPickerLocationByPosition")
    public ApiResult selectPickerLocationByPosition(@NotBlank(message = "position can not be null") String position) throws DataHasNotExistedException {
        PickerLocation pickerLocation = pickerLocationService.selectPickerLocationByPosition(position);
        return ApiResult.success(pickerLocation, "查找成功");
    }

    @ApiOperation(value = "查找所有报修地点")
    @PostMapping("/selectAllPickerLocation")
    public ApiResult selectAllPickerLocation() throws DataHasNotExistedException {
        List<PickerLocation> pickerLocations = pickerLocationService.selectAllPickerLocation();
        return ApiResult.success(pickerLocations, "查找成功");
    }

    @ApiOperation(value = "修改报修地点")
    @ApiImplicitParams({@ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")})
    @PostMapping("/updatePickerLocation")
    public ApiResult updatePickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId, @NotBlank(message = "area can not be null") String area,
                                          @NotBlank(message = "position can not be null") String position) throws DataHasNotExistedException {
        pickerLocationService.updatePickerLocation(pickerId, area, position);
        return ApiResult.success("更新成功");
    }

    @ApiOperation(value = "增加报修时间段")
    @ApiImplicitParam(name = "time", value = "时间段", required = true, paramType = "query")
    @PostMapping("/addPickerTime")
    public ApiResult addPickerTime(@NotBlank(message = "time can not be null") String time) throws DataHasExistedException {
        pickerTimeService.addPickerTime(time);
        return ApiResult.success("报修时间段添加成功");
    }

    @ApiOperation(value = "删除报修时间段")
    @ApiImplicitParam(name = "pickerId", value = "报修时间段Id", required = true, paramType = "query")
    @PostMapping("/deletePickerTime")
    public ApiResult deletePickerTime(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        pickerTimeService.deletePickerTime(pickerId);
        return ApiResult.success("报修地点删除成功");
    }

    @ApiOperation(value = "根据id超找某个时间段")
    @ApiImplicitParam(name = "pickerId", value = "报修时间段Id", required = true, paramType = "query")
    @GetMapping("/selectPickerTime")
    public ApiResult selectPickerTime(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        PickerTime pickerTime = pickerTimeService.selectPickerTime(pickerId);
        return ApiResult.success(pickerTime, "查找成功");
    }

    @ApiOperation(value = "根据time查找某个报修时间段")
    @ApiImplicitParam(name = "time", value = "时间段", required = true, paramType = "query")
    @GetMapping("/selectPickerTimeByTime")
    public ApiResult selectPickerTimeByTime(@NotBlank(message = "position can not be null") String time) throws DataHasNotExistedException {
        PickerTime pickerTime = pickerTimeService.selectPickerTimeByTime(time);
        return ApiResult.success(pickerTime, "查找成功");
    }

    @ApiOperation(value = "查找所有时间段")
    @PostMapping("/selectAllPickerTimeForUser")
    public ApiResult selectAllPickerTimeForUser() throws DataHasNotExistedException {
        List<PickerTime> pickerTimes = pickerTimeService.selectAllPickerTime();
        return ApiResult.success(pickerTimes, "查找成功");
    }

    @ApiOperation(value = "修改报修时间段")
    @ApiImplicitParams({@ApiImplicitParam(name = "pickerId", value = "报修时间段Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "time", value = "时间段", required = true, paramType = "query")})
    @PostMapping("/updatePickerTime")
    public ApiResult updatePickerTime(@NotNull(message = "pickerId can not be null") Integer pickerId,
                                      @NotBlank(message = "time can not be null") String time) throws DataHasNotExistedException {
        pickerTimeService.updatePickerTime(pickerId, time);
        return ApiResult.success("更新成功");
    }
}
