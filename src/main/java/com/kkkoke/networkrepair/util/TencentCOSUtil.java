package com.kkkoke.networkrepair.util;

import com.kkkoke.networkrepair.config.COSClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TencentCOSUtil {

    // 同时注意bucketName不能有大写字母
    public static void createBucket(String bucketName) {
        String bucket = bucketName + "-" + COSClientConfig.getAppid(); //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try {
            Bucket bucketResult = COSClientConfig.initCOSClient().createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
    }

    public static List<Bucket> listAllBuckets() {
        List<Bucket> buckets = COSClientConfig.initCOSClient().listBuckets();
        return buckets;
    }

    public static PutObjectResult uploadFile(String localFilePath, String targetFilePath, String bucket) {
        // 指定要上传的文件
        File localFile = new File(localFilePath);
        // 指定文件将要存放的存储桶
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = targetFilePath;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = COSClientConfig.initCOSClient().putObject(putObjectRequest);

        return putObjectResult;
    }

    public static List<COSObjectSummary> listAllObjects(String bucket, String directoryPrefix) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix(directoryPrefix);
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        List<COSObjectSummary> result = new ArrayList<>();
        do {
            try {
                objectListing = COSClientConfig.initCOSClient().listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return null;
            } catch (CosClientException e) {
                e.printStackTrace();
                return null;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                result.add(cosObjectSummary);
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                System.out.println(key);
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                System.out.println(etag);
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                System.out.println(fileSize);
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
                System.out.println(storageClasses);
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

        return result;
    }

    public static void downloadFile(String bucket, String directoryPrefix) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        // 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
        String key = directoryPrefix;
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = COSClientConfig.initCOSClient().getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        try {
            cosObjectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String bucket, String directoryPrefix, String localStoragePath) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        // 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
        String key = directoryPrefix;
        //方法2 下载文件到本地的路径，例如 D 盘的某个目录
        String outputFilePath = localStoragePath;
        File downFile = new File(outputFilePath);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = COSClientConfig.initCOSClient().getObject(getObjectRequest, downFile);
    }

    public static void deleteFile(String bucket, String directoryPrefix) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        String key = directoryPrefix;
        COSClientConfig.initCOSClient().deleteObject(bucketName, key);
    }

    public static URL getObjectUrl(String bucket, String key) {
        // 不需要验证身份信息
        COSCredentials cred = new AnonymousCOSCredentials();

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        String bucketName = bucket + "-" + COSClientConfig.getAppid();

        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(COSClientConfig.getRegion()));

        // 设置生成的 url 的请求协议, http 或者 https
        // 5.6.53 及更低的版本，建议设置使用 https 协议
        // 5.6.54 及更高版本，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        URL url = cosclient.getObjectUrl(bucketName, key);
        System.out.println(cosclient.getObjectUrl(bucketName, key));
        return url;
    }

    public static UploadResult upLoadFileStream(String bucket, String fileKey, InputStream fileStream) {
        // 使用高级接口必须先保证本进程存在一个 TransferManager 实例，如果没有则创建
        TransferManager transferManager = createTransferManager();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String bucketName = bucket + "-" + COSClientConfig.getAppid();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, fileStream, objectMetadata);
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            return uploadResult;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager
        shutdownTransferManager(transferManager);
        return null;
    }

    public static TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = COSClientConfig.initCOSClient();

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        return transferManager;
    }

    public static InputStream fileToInputStream(String fileName) throws IOException {
        File initialFile = new File(fileName);
        InputStream targetStream = new FileInputStream(initialFile);
        return targetStream;
    }

    public static void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }
}
