package com.zjut.serviceBase.exceptionHandler;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zjut.commonutils.R;

/**
 * @author Joya Zou
 * @date 2021年06月27日 20:33
 */
@ControllerAdvice
@Slf4j  // 将异常的处理信息打印到日志中
public class GlobalExceptionHandler  {


    // 指定发生什么类型的异常时触发该方法
    // 因为不在controller中，只有加了@ResponseBody才能返回数据给前端
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常...");
    }


    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常...");
    }

    // 自定义异常处理
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        log.error(e.getMessage());  // 将异常信息打印到日志
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }



}
