package com.zzq.zzqvhr.exception;

import com.zzq.zzqvhr.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public RespBean sqlException(Exception e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据");
        }
        return RespBean.error("数据库异常，操作失败");
    }
}
