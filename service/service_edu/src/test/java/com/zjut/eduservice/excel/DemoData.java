package com.zjut.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Joya Zou
 * @date 2021年07月01日 18:45
 */
@Data
public class DemoData {

    // 对应excel表中的每一列的字段,index指定这是第几列
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;


}
