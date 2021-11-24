package com.kkkoke.networkrepair.util.token;

import com.alibaba.fastjson.JSONObject;

public interface TokenVerify {
    boolean verify(JSONObject Json, String token);
}
