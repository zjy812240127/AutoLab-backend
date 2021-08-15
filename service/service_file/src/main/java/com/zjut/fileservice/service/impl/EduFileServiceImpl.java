package com.zjut.fileservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjut.fileservice.entity.EduFile;
import com.zjut.fileservice.entity.vo.FileQuery;
import com.zjut.fileservice.mapper.EduFileMapper;
import com.zjut.fileservice.service.EduFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.fileservice.utils.ConstantPropertiesUtils;
import com.zjut.serviceBase.exceptionHandler.MyException;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-02
 */
@Service
public class EduFileServiceImpl extends ServiceImpl<EduFileMapper, EduFile> implements EduFileService {

    @Override
    public String uploadFile(MultipartFile file) {

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
    public void saveDB(EduFile eduFile) {
        EduFile file = new EduFile();
        BeanUtils.copyProperties(eduFile,file);
        System.out.println("文件title：" + eduFile.getTitle());
        System.out.println("文件description：" + eduFile.getDescription());
        System.out.println("文件url：" + eduFile.getFileUrl());
        int insert = baseMapper.insert(file);
        if (insert == 0){
            throw new MyException(20001, "文件信息录入数据库失败");
        }

    }

    @Override
    public List<EduFile> getFileList(long current, long limit, String title, String begin, String end) {

        List<EduFile> fileList = baseMapper.findFileList(current, limit);
        return fileList;


    }

    @Override
    public void updateFile(FileQuery fileQuery) {
        String fileId = fileQuery.getId();
        QueryWrapper<EduFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id",fileId);

        EduFile file = new EduFile();
        BeanUtils.copyProperties(fileQuery, file);
        System.out.println("要更新的文件的id：" + file.getId() );
        
        int update = baseMapper.update(file, wrapper);
        if (update == 0){
            throw new MyException(20001, "更新文件信息失败");
        }
    }

    @Override
    public Map<String, Object> getList(long current, long limit) {

        // 再sql中limit的第一个参数
        current = (current -1) * limit;
        System.out.println("前端传入的当前页：" + current + "限制：" + limit + "limit的起点：" + current);

        List<EduFile> list = baseMapper.getFileList(current,limit);
        int size = list.size();
        Map map = new HashMap();
        map.put("total", size);
        map.put("fileList",list);
        System.out.println("查询到的总记录数=" + size );
        System.out.println("查询到的list：" + list);

        return map;
    }

    @Override
    public EduFile getFileById(String fileId) {

        EduFile file = baseMapper.getFileById(fileId);
        return file;
    }

    @Override
    public Map<String, Object> getFileByInfo(long current, long limit, FileQuery fileQuery) {

        String title = fileQuery.getTitle();
        Date begin = fileQuery.getBegin();
        Date end = fileQuery.getEnd();
        List<EduFile> fileList = baseMapper.findFileByInfo(current,limit,title,begin,end);
        int size = fileList.size();
        Map map = new HashMap<>();
        map.put("fileList", fileList);
        map.put("total",size);
        System.out.println("mapper中分页查询的总记录数：" + size);
        return map;

    }
}
