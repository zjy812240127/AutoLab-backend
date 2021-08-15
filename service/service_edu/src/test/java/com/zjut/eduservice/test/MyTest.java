package com.zjut.eduservice.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class MyTest {
    // 因为每一个测试类都要加以上三个注解，为了方便，我们建立这个公共父类，让其他测试类继承该类，就可以不写这几个注解了
    @Before
    public void init(){
        System.out.println("开始测试-----------------");
    }

    @After
    public void after(){
        System.out.println("测试结束----------------");
    }



}
