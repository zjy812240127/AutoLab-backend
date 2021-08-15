package com.zjut.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zjut.oss.service.OssService;
import com.zjut.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Joya Zou
 * @date 2021年07月01日 9:15
 */
@Service
public class OssServiceImpl implements OssService {


    @Override
    public String upLoaderFileAvatar(MultipartFile file) {
        // 调用阿里云官网上传文件流的方式

        // 获取oss信息
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret  = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件的名称
            String fileName  = file.getOriginalFilename();
            // 获取一个随机数
            String uuid = UUID.randomUUID().toString();
            // 为了文件名不重复
            fileName =  uuid+ fileName;

            // 为了在oss中对文件进行管理，按照上传日期不同建立路径存放文件
            // 利用引入的依赖joda-time获取指定格式的当前时间
            // 2021/7/1  这也是在oss中建立文件夹的路径
            final String datePath = new DateTime().toString("yyyy/MM/dd");

            // 2021/7/1/xxx.jap
            fileName = datePath + "/" + fileName;

            // 调用oss方法实现上传
            // 第一个参数: buckeetName
            // 第二个参数：要上传到oss中建立的文件夹路径以及保存的文件名称
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传到oss后的文件的url路径输出
            // https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/1%20%288%29.jpg

            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public String upLoaderSources(MultipartFile file) {
        // 调用阿里云官网上传文件流的方式

        // 获取oss信息
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret  = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件的名称
            String fileName  = file.getOriginalFilename();
            // 获取一个随机数
            String uuid = UUID.randomUUID().toString();
            // 为了文件名不重复
            fileName =  uuid+ fileName;

            // 为了在oss中对文件进行管理，按照上传日期不同建立路径存放文件
            // 利用引入的依赖joda-time获取指定格式的当前时间
            // 2021/7/1  这也是在oss中建立文件夹的路径
            final String datePath = new DateTime().toString("yyyy/MM/dd");

            // 2021/7/1/xxx.jap
            fileName = datePath + "/" + fileName;

            // 调用oss方法实现上传
            // 第一个参数: buckeetName
            // 第二个参数：要上传到oss中建立的文件夹路径以及保存的文件名称
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传到oss后的文件的url路径输出
            // https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/1%20%288%29.jpg

            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String uploadBanners(MultipartFile file) {

        // 调用阿里云官网上传文件流的方式

        // 获取oss信息
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret  = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件的名称
            String fileName  = file.getOriginalFilename();
            // 获取一个随机数
            String uuid = UUID.randomUUID().toString();
            // 为了文件名不重复
            fileName =  uuid+ fileName;

            // 为了在oss中对文件进行管理，按照上传日期不同建立路径存放文件
            // 利用引入的依赖joda-time获取指定格式的当前时间
            // 2021/7/1  这也是在oss中建立文件夹的路径
            final String datePath = new DateTime().toString("yyyy/MM/dd");

            // 2021/7/1/xxx.jap
            fileName = datePath + "/" + fileName;

            // 调用oss方法实现上传
            // 第一个参数: buckeetName
            // 第二个参数：要上传到oss中建立的文件夹路径以及保存的文件名称
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 把上传到oss后的文件的url路径输出
            // https://gulideu-2021.oss-cn-hangzhou.aliyuncs.com/1%20%288%29.jpg

            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
