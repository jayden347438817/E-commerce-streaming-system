package com.bupt.ecommercestreamingsystem.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//生成get set方法
@NoArgsConstructor//生成无参构造方法
@AllArgsConstructor//生成有参构造方法
public class Result<T> {//泛型类
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }
    public static <T> Result<T> success(T data,String message){
        return new Result<>(20000,message,data);
    }
    public static <T> Result<T> success(String message){
        return new Result<>(20000,message,null );
    }

    public static <T> Result<T> fail(){
        return new Result<>(20001,"fail",null);
    }
    public static <T> Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }
    public static <T> Result<T> fail(Integer code,String message){
        return new Result<>(code,message,null);
    }
    public static <T> Result<T> fail(String message){
        return new Result<>(20001,message,null);
    }
}
