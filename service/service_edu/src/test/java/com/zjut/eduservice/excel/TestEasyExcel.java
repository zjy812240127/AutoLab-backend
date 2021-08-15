package com.zjut.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joya Zou
 * @date 2021年07月01日 19:32
 */
public class TestEasyExcel {

    public static void main(String[] args) {

        // 设置写入文件夹地址和文件名 会在本地计算机创建一个表格存放数据
        String fileName = "E:\\vs001\\testWrite.xlsx";

        // 调用easyexcel里的方法实现写操作,第一个参数是文件路径，第二个参数是参数类
        // dowrite放的是一个list
        // 这种方法会自动关闭流
        EasyExcel.write(fileName,DemoData.class).sheet().doWrite(getData());

    }

    public static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i =0; i< 10; i++){
            DemoData demoData = new DemoData();
            demoData.setSname("wang" + i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }

    @Test
    public void readTest(){
        String fileName = "E:\\vs001\\testWrite.xlsx";

        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();

    }







}
