package com.kkkoke.networkrepair.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;

public class COSClientConfig {
    private static final String SECRET_ID = "AKIDWt0rRmqd8ON6YjfKipfDVWGjvqUshHbf";

    private static final String SECRET_KEY = "oLaZHjvpSotiGYZJn0waz80buxkGVUCe";

    private static final String REGION = "ap-nanjing";

    private static final String APPID = "1257191112";

    public static String getSecretId() {
        return SECRET_ID;
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static String getRegion() {
        return REGION;
    }

    public static String getAppid() {
        return APPID;
    }

    public static COSClient initCOSClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = COSClientConfig.SECRET_ID;
        String secretKey = COSClientConfig.SECRET_KEY;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(COSClientConfig.REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }
}
