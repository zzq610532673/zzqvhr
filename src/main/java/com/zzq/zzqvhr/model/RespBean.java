package com.zzq.zzqvhr.model;

import lombok.Data;
import org.springframework.context.annotation.Bean;
@Data
public class RespBean {
    private Integer status;
    private String msg;
    private Object object;

    public RespBean() {
    }

    public RespBean(Integer status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }

    public static RespBean build(){
        return new RespBean();
    }

    public static RespBean ok(String msg){
        return new RespBean(200,msg,null);
    }

    public static RespBean ok(String msg,Object object){
        return new RespBean(200,msg,object);
    }

    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }

    public static RespBean error(String msg,Object object){
        return new RespBean(500,msg,object);
    }
}
