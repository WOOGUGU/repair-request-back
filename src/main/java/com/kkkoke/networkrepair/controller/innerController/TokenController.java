package com.kkkoke.networkrepair.controller.innerController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import com.kkkoke.networkrepair.util.token.JwtToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @PostMapping("/isExpired")
    public StatusAndDataFeedback isExpired(@RequestBody JSONObject tokenJson) {
        String token = (String) tokenJson.get("token");
        boolean res = JwtToken.isExpired(token);

        return new StatusAndDataFeedback(JwtToken.isExpired(token), StatusAndDataFeedback.HANDLE_SUCCESS);
    }
}
