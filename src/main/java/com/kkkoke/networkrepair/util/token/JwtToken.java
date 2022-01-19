package com.kkkoke.networkrepair.util.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.TokenExpiredException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    /*
    * 公用密钥-保存在服务端，客户端是不知道密钥的，以防被攻击
    * */
    public static String SECRET = "HBUTitc2021";

    // String username, String password
    public static String creatToken(String username, String password, Long id) throws Exception {
        // 签发时间
        Date iatDate = new Date();

        // 过期时间  过期时间为7天
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DAY_OF_WEEK, 7);  // 七天过期
//        nowTime.add(Calendar.SECOND, 7);  // 七秒过期
        Date expiresDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HMAC256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map) // header
                .withClaim("username", username) // payload
                .withClaim("password", password)
                .withClaim("id", id)
                .withExpiresAt(expiresDate) // 设置过期时间。过期时间要大于签发时间
                .withIssuedAt(iatDate) // 设置签发时间
                .sign(Algorithm.HMAC256(SECRET)); // 加密

        return token;
    }

    /*
    * 解密Token
    * */
    public static Map<String, Claim> verifyToken(String token) throws TokenExpiredException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        Date expiresDate = jwt.getExpiresAt();
        Date now = new Date();
        if (now.getTime() - expiresDate.getTime() < 0) {
            throw new TokenExpiredException("token has expired");
        }

        return jwt.getClaims();
    }

    /*
     * 查看Token是否过期
     * */
    public static boolean isExpired(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        Date expiresDate = jwt.getExpiresAt();
        Date now = new Date();
        return now.getTime() - expiresDate.getTime() > 0;
    }
}
