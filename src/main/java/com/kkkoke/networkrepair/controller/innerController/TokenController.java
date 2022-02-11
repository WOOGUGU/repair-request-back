package com.kkkoke.networkrepair.controller.innerController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.util.token.JwtToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @PostMapping("/isExpired")
    public ApiResult isExpired(@RequestBody JSONObject tokenJson) {
        String token = (String) tokenJson.get("token");

        return ApiResult.success(JwtToken.isExpired(token), "请求成功");
    }
}
