package com.kkkoke.networkrepair.controller.innerController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.util.token.JwtToken;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kkkoke
 */
@Api(tags = "认证管理")
@Slf4j
@Validated
@RequestMapping("/v2/authentication")
@RestController
public class TokenController {
    @PostMapping("/isExpired")
    public ApiResult isExpired(@RequestBody JSONObject tokenJson) {
        String token = (String) tokenJson.get("token");

        return ApiResult.success(JwtToken.isExpired(token), "请求成功");
    }
}
