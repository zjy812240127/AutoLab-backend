package com.zjut.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author Joya Zou
 * @date 2021年07月01日 19:51
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    // 从第二行开始一行一行读取数据
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("*****" + demoData);
    }

    // 读取表头（第一行）信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("*****" + headMap);
    }

    // 读取完成后做的事
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
