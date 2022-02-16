package com.kkkoke.networkrepair.controller.innerController;

import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkkoke
 */
@Api(tags = "内部调用接口")
@Slf4j
@Validated
@RequestMapping("/v2/inner")
@RestController
public class InnerController {

    @ApiOperation(value = "获取当前时间")
    @Secured({"ROLE_admin", "ROLE_uer", "ROLE_repairman"})
    @GetMapping("/getTime")
    public ApiResult getTime() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String threeDaysAfter = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String, String> res = new HashMap<>();
        res.put("now", now);
        res.put("after", threeDaysAfter);

        return ApiResult.success(res, "获取成功");
    }
}