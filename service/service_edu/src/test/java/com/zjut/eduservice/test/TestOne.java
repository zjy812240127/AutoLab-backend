package com.zjut.eduservice.test;


import com.zjut.commonutils.R;
import com.zjut.eduservice.controller.EduCourseController;
import org.junit.Assert;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class TestOne extends MyTest{
    @Autowired
    private EduCourseController eduCourseController;

    @Test
    public void testEduCourseController(){
        //Assert.assertSame("预期结果不相符", r, eduCourseController.testMyTest());
        Assert.assertSame("预期结果不相符", R.ok(), eduCourseController.deleteById("1"));
    }
}
