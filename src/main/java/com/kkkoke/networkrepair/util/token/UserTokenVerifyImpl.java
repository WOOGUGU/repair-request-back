package com.kkkoke.networkrepair.util.token;

import com.auth0.jwt.interfaces.Claim;
import com.kkkoke.networkrepair.service.UserService;
import com.kkkoke.networkrepair.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

@Service
public class UserTokenVerifyImpl implements TokenVerify {
    @Autowired
    UserService userService;

    @Override
    public boolean verify(String token) {
        // 解析token
        try {
            Map<String, Claim> jwt = JwtToken.verifyToken(token);
            // 验证token的正确性
            return Objects.equals(MD5Util.md5(userService.selectUserByUsername(jwt.get("username").asString()).getPassword()), jwt.get("password").asString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
