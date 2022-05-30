package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasExistedException;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Picker;
import com.kkkoke.networkrepair.pojo.PickerLocation;
import com.kkkoke.networkrepair.pojo.helper.PickerResult;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultCode;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.PickerLocationService;
import com.kkkoke.networkrepair.service.PickerService;
import com.kkkoke.networkrepair.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
    private final PickerService pickerService;
    private final PropertiesUtil propertiesUtil;

    @Autowired
    public PickerController(PickerLocationService pickerLocationService, PickerService pickerService, PropertiesUtil propertiesUtil) {
        this.pickerLocationService = pickerLocationService;
        this.pickerService = pickerService;
        this.propertiesUtil = propertiesUtil;
    }

    @ApiOperation(value = "增加报修地点")
    @ApiImplicitParams({@ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addPickerLocation")
    public ApiResult addPickerLocation(@NotBlank(message = "area can not be null") String area,
                                       @NotBlank(message = "position can not be null") String position) throws DataHasExistedException {
        pickerLocationService.addPickerLocation(area, position);
        return ApiResult.success("添加成功");
    }

    @ApiOperation(value = "删除报修地点")
    @ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deletePickerLocation")
    public ApiResult deletePickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        pickerLocationService.deletePickerLocation(pickerId);
        return ApiResult.success("删除成功");
    }

    @ApiOperation(value = "根据id查找某个报修地点")
    @ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectPickerLocation")
    public ApiResult selectPickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        PickerLocation pickerLocation = pickerLocationService.selectPickerLocation(pickerId);
        return ApiResult.success(pickerLocation, "查找成功");
    }

    @ApiOperation(value = "根据area查找报修地点")
    @ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectPickerLocationByArea")
    public ApiResult selectPickerLocationByArea(@NotBlank(message = "area can not be null") String area) throws DataHasNotExistedException {
        List<PickerLocation> pickerLocations = pickerLocationService.selectPickerLocationByArea(area);
        return ApiResult.success(pickerLocations, "查找成功");
    }

    @ApiOperation(value = "根据position查找报修地点")
    @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectPickerLocationByPosition")
    public ApiResult selectPickerLocationByPosition(@NotBlank(message = "position can not be null") String position) throws DataHasNotExistedException {
        PickerLocation pickerLocation = pickerLocationService.selectPickerLocationByPosition(position);
        return ApiResult.success(pickerLocation, "查找成功");
    }

    @ApiOperation(value = "查找报修地点 后台接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pickerId", value = "报修位置id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区域", required = false, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "位置", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @GetMapping("/selectLocationForBackend")
    public ApiResult selectLocationForBackend(Integer pickerId, String area, String position, Integer pageNum, Integer pageSize) {
        ResultPage<PickerLocation> pickerLocations = pickerLocationService.selectLocationForBackend(pickerId, area, position, pageNum, pageSize);
        return ApiResult.success(pickerLocations, "查找成功");
    }

    @ApiOperation(value = "查找所有报修地点")
    @Secured({"ROLE_admin", "ROLE_user"})
    @GetMapping("/selectAllPickerLocation")
    public ApiResult selectAllPickerLocation() throws DataHasNotExistedException {
        Object pickerLocations = pickerLocationService.selectAllPickerLocation();
        return ApiResult.success(pickerLocations, "查找成功");
    }

    @ApiOperation(value = "修改报修地点")
    @ApiImplicitParams({@ApiImplicitParam(name = "pickerId", value = "报修地点Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "位置", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updatePickerLocation")
    public ApiResult updatePickerLocation(@NotNull(message = "pickerId can not be null") Integer pickerId, @NotBlank(message = "area can not be null") String area,
                                          @NotBlank(message = "position can not be null") String position) throws DataHasNotExistedException {
        pickerLocationService.updatePickerLocation(pickerId, area, position);
        return ApiResult.success("更新成功");
    }

    @ApiOperation(value = "增加picker")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "picker类型", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "picker值", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addPicker")
    public ApiResult addPicker(@NotBlank(message = "type can not be null") String type, @NotBlank(message = "value can not be null") String value) throws DataHasExistedException {
        if (type.equals("time") || type.equals("des")) {
            pickerService.addPicker(type, value);
            return ApiResult.success("添加成功");
        } else {
            return ApiResult.fail(ResultCode.PARAM_ERROR, "参数内容错误，type必须为time或者des", ApiResult.PARAM_ERROR);
        }
    }

    @ApiOperation(value = "通过id删除picker")
    @ApiImplicitParam(name = "pickerId", value = "pickerId", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deletePickerById")
    public ApiResult deletePickerById(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        pickerService.deletePickerById(pickerId);
        return ApiResult.success("删除成功");
    }

    @ApiOperation(value = "通过value删除故障类型")
    @ApiImplicitParam(name = "value", value = "picker值", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deletePickerByValue")
    public ApiResult deletePickerByValue(@NotNull(message = "value can not be null") String value) throws DataHasNotExistedException {
        pickerService.deletePickerByValue(value);
        return ApiResult.success("删除成功");
    }

    @ApiOperation(value = "根据id查找picker")
    @ApiImplicitParam(name = "pickerId", value = "报修时间段Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectPickerById")
    public ApiResult selectPickerById(@NotNull(message = "pickerId can not be null") Integer pickerId) throws DataHasNotExistedException {
        Picker picker = pickerService.selectPickerById(pickerId);
        return ApiResult.success(picker, "查找成功");
    }

    @ApiOperation(value = "根据value查找某个报修时间段")
    @ApiImplicitParam(name = "value", value = "picker值", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @GetMapping("/selectPickerByValue")
    public ApiResult selectPickerByValue(@NotBlank(message = "value can not be null") String value) throws DataHasNotExistedException {
        Picker picker = pickerService.selectPickerByValue(value);
        return ApiResult.success(picker, "查找成功");
    }

    @ApiOperation(value = "查找所有picker")
    @Secured({"ROLE_admin", "ROLE_user"})
    @GetMapping("/selectAllPicker")
    public ApiResult selectAllPicker() throws DataHasNotExistedException {
        HashMap<String, List<PickerResult>> pickers = pickerService.selectAllPicker();
        HashMap<String, Object> result = new HashMap<>();
        String start = LocalDate.now().plusDays(propertiesUtil.getStartTime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end = LocalDate.now().plusDays(propertiesUtil.getEndTime()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        result.put("picker", pickers);
        result.put("start", start);
        result.put("end", end);
        return ApiResult.success(result, "查找成功");
    }

    @ApiOperation(value = "查找所有报修时间段")
    @Secured({"ROLE_admin", "ROLE_user"})
    @GetMapping("/selectAllPickerTime")
    public ApiResult selectAllPickerTime() throws DataHasNotExistedException {
        List<PickerResult> pickerTimes = pickerService.selectAllPickerTime();
        return ApiResult.success(pickerTimes, "查找成功");
    }

    @ApiOperation(value = "查找所有故障描述")
    @Secured({"ROLE_admin", "ROLE_user"})
    @GetMapping("/selectAllPickerDes")
    public ApiResult selectAllPickerDes() throws DataHasNotExistedException {
        List<PickerResult> pickerDess = pickerService.selectAllPickerDes();
        return ApiResult.success(pickerDess, "查找成功");
    }

    @ApiOperation(value = "修改picker")
    @ApiImplicitParams({@ApiImplicitParam(name = "pickerId", value = "pickerId", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "picker类型", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "picker值", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updatePicker")
    public ApiResult updatePicker(@NotNull(message = "pickerId can not be null") Integer pickerId, @NotBlank(message = "type can not be null") String type,
                                  @NotBlank(message = "value can not be null") String value) throws DataHasNotExistedException {
        pickerService.updatePicker(pickerId, type, value);
        return ApiResult.success("更新成功");
    }
}
