package cn.xqhuang.dps.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class UploadFileToOssUtil {

    private static String endPoint = "";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String bucketNameCorp = "";
    private static String host = "";


    public static String getPath() {
        return host + endPoint + "/";
    }

    public static String uploadCorpFile(File file, String fileName) {
        return uploadOSSFile(file, fileName, endPoint, accessKeyId, accessKeySecret, bucketNameCorp, host);
    }

    public static String uploadCorpFile(File file, String name, String serverEnvironment) {
        String fileName = String.format("%s_%s_%s", serverEnvironment, new Date().getTime(), name);
        return uploadOSSFile(file, fileName, endPoint, accessKeyId, accessKeySecret, bucketNameCorp, host);
    }

    public static String uploadFile (final InputStream inputStream, String fileName) {
        return uploadOSSFile(inputStream, fileName, endPoint, accessKeyId, accessKeySecret, bucketNameCorp, host);
    }


    public static String uploadFile(final InputStream inputStream, String name, String serverEnvironment) {
        String fileName = String.format("%s_%s", serverEnvironment, name);
        String path = uploadOSSFile(inputStream, fileName, endPoint, accessKeyId, accessKeySecret, bucketNameCorp, host);
        return path;
    }

    private static String uploadOSSFile(final InputStream inputStream, String fileName, String ossEndPoint, String ossAccessKeyId, String ossAccessKeySecret, String ossBucketName, String ossHost) {
       // log.info("------OSS文件上传开始--------" + fileName);
        OSSClient client = new OSSClient(ossEndPoint, ossAccessKeyId, ossAccessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(ossBucketName)) {
                client.createBucket(ossBucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(ossBucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 上传文件
            client.putObject(new PutObjectRequest(ossBucketName, fileName, inputStream));
            // 设置权限(公开读)
            client.setBucketAcl(ossBucketName, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
         //   log.error("OSS文件上传 error", e);
            throw new ServiceException(e);
        } finally {
            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                   // log.error("关闭文件流 error", e);
                }
            }
            client.shutdown();
        }
       // log.info("------OSS文件上传结束--------" + ossHost + ossEndPoint + "/" + fileName);
        return ossHost + ossEndPoint + "/" + fileName;
    }


    private static String uploadOSSFile(File file, String fileName, String ossEndPoint, String ossAccessKeyId, String ossAccessKeySecret, String ossBucketName, String ossHost) {
        // log.info("------OSS文件上传开始--------" + fileName);

        OSSClient client = new OSSClient(ossEndPoint, ossAccessKeyId, ossAccessKeySecret);
        try (final InputStream inputStream = new FileInputStream(file)) {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(ossBucketName)) {
                client.createBucket(ossBucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(ossBucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 上传文件
            client.putObject(new PutObjectRequest(ossBucketName, fileName, inputStream));
            // 设置权限(公开读)
            client.setBucketAcl(ossBucketName, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
          //  log.error("OSS文件上传 error", e);
            throw new ServiceException(e);
        } finally {
            client.shutdown();
        }
        //log.info("------OSS文件上传结束--------" + ossHost + ossEndPoint + "/" + fileName);
        return ossHost + ossEndPoint + "/" + fileName;
    }

    public static InputStream getObject(String key) {
        // 初始化OSSClient
        OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        try {
            // 获取Object，返回结果为OSSObject对象
            OSSObject object = client.getObject(bucketNameCorp, key);

            // 获取ObjectMeta
            //ObjectMetadata meta = object.getObjectMetadata();

            // 获取Object的输入流
            InputStream objectContent = object.getObjectContent();

            return objectContent;
        } catch (Exception e) {
           // log.error("OSS文件 error", e);
        }
        return null;
    }

}
