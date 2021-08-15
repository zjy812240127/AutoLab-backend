package com.zjut.serviceBase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author Joya Zou
 * @date 2021年06月27日 21:17
 */

// 建立自定义异常

@Data
@AllArgsConstructor  // 生成有参构造方法
@NoArgsConstructor   // 生成无参构造方法
public class MyException extends RuntimeException {
    private Integer code;  // 状态码

    private String msg;  // 异常信息
}
