package com.zjut.fileservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.R;
import com.zjut.fileservice.entity.EduFile;
import com.zjut.fileservice.entity.vo.FileQuery;
import com.zjut.fileservice.service.EduFileService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import feign.Param;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/fileservice/file")
public class EduFileController {

    @Autowired
    private EduFileService eduFileService;

    @ApiOperation(value = "根据id查询文件信息")
    @GetMapping("/selectFileById/{fileId}")
    public R findById(
            @ApiParam(name = "fileId", value = "修改文件信息时先从后端查询文件基本信息显示到页面", required = true)
            @PathVariable String fileId){
        System.out.println("根据文件id查询课程信息 , 文件id为：" + fileId);
        //EduFile file = eduFileService.getFileById(fileId);
        EduFile eduFile = eduFileService.getById(fileId);
        System.out.println("文件的title为：" + eduFile.getTitle() + "----文件的下载地址为：" + eduFile.getFileUrl());
        return R.ok().data("file", eduFile);
    }

    @ApiOperation(value = "接受前端上传的文件包存到阿里云oss，返回文件下载地址给前端")
    @PostMapping("/uploadFile")
    public R upload(MultipartFile file){
        System.out.println("上传文件到阿里云oss");
        String url = eduFileService.uploadFile(file);
        System.out.println("阿里云文件下载地址=" + url);
        return R.ok().data("url",url);
    }

    @ApiOperation(value = "根据文件id 删除文件，删除阿里云上的文件以及数据表里的数据")
    @PostMapping("/deleteFile/{fileId}")
    public R removeFile(
            @ApiParam(name = "fileId", value = "要删除的文件id", required = true)
            @PathVariable String fileId){
        // 删除阿里云上的文件
        System.out.println("请到阿里云控制台删除文件");

        // 删除数据库表里的文件
        boolean remove = eduFileService.removeById(fileId);
        if (remove == false){
            throw new MyException(20001,"删除文件失败");
        }else {
            return R.ok().data("info","数据库已删除文件信息，请到阿里云删除源文件...");
        }
    }


    @ApiOperation(value = "接受前端的文件相关信息，将其录入数据库")
    @PostMapping("/saveDB")
    public R saveFileToDB(@RequestBody(required = false) EduFile eduFile){

        eduFileService.saveDB(eduFile);

        return R.ok();
    }

    @ApiOperation(value = "根据文件id修改文件信息")
    @PostMapping("/updateFile")
    public R updateFile(@RequestBody FileQuery fileQuery){
        System.out.println("修改文件信息");

        eduFileService.updateFile(fileQuery);
        System.out.println("更新文件信息成功");
        return R.ok();
    }

    @ApiOperation(value = "查询所有文件列表")
    @GetMapping("/getFiles")
    public R getAllFiles(){
        List<EduFile> eduFiles = eduFileService.list(null);
        int size = eduFiles.size();
        Map map = new HashMap();
        map.put("total", size);
        map.put("list",eduFiles);

        return R.ok().data(map);
    }

    @ApiOperation(value = "分页查询文件列表")
    @GetMapping("/{current}/{limit}")
    public R getFileList(@PathVariable long current,
                         @PathVariable long limit){

        System.out.println("分页查询问价列表，当前页=" + current + "每页条数=" + limit);
        //Page<EduFile> page = new Page<>(current,limit);
        Page<EduFile> page = new Page<>(current,limit);
        QueryWrapper<EduFile> wrapper = new QueryWrapper<>();
        // 根据创建时间排序
        wrapper.orderByDesc("gmt_create");

        eduFileService.page(page, wrapper);
        List<EduFile> fileList = page.getRecords();
        long total = fileList.size();
        System.out.println("文件总数=" + total);
        System.out.println("文件列表" + fileList);
        long pageCurrent = page.getCurrent();
        long pages = page.getPages();
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();
        System.out.println("当前页：" + pageCurrent + "pages：" + pages + "hasPrevious:" + hasPrevious + "hasNext:" + hasNext);

        Map map = new HashMap();
        map.put("fileList", fileList);
        map.put("total", total);
        map.put("current",pageCurrent);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        // 上面方法查不到数据，手写dao
        //Map<String, Object> mapList = eduFileService.getList(current,limit);


        return R.ok().data(map);
    }

    @ApiOperation(value = "分页查询所有文件")
    @PostMapping("/findFileList/{current}/{limit}")
    public R findFileList(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录条数", required = true)
            @PathVariable long limit,
            @RequestBody(required = false) FileQuery fileQuery){

        // 查询条件有：文件title的模糊查询 / 文件创建的时间段
        Page<EduFile> page = new Page<>(current, limit);
        QueryWrapper<EduFile> wrapper = new QueryWrapper<>();
        String title = fileQuery.getTitle();
        Date begin = fileQuery.getBegin();
        Date end = fileQuery.getEnd();
        System.out.println("当前页"+ current + "每页记录数" + limit + "文件标题" + title + "起始日期" + begin + "结束日期" + end);


        // 以上查询方法查不到数据手动写mapper
        Map<String,Object> mapFile = eduFileService.getFileByInfo(current,limit,fileQuery);
        return R.ok().data(mapFile);


    }

}

