package com.work.studentmangersystem.execption;

import com.work.studentmangersystem.entity.resp.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @FileName SCMS
 * @Description
 * @Author chaos
 * @Date 2024/6/6 下午1:34
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//捕获所有异常
    public ResultData<String> ex(Exception e){
        log.error("异常:{}",e.getMessage(),e);
        return ResultData.fail("对不起，操作失败，请联系管理员");
    }
}
