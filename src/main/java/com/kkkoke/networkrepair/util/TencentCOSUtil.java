package com.kkkoke.networkrepair.util;

import com.kkkoke.networkrepair.config.COSClientConfig;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
