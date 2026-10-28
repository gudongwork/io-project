package com.heima.vo;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
    public static Result success(Object data){
        Result result = new Result();
        result.code = 1;
        result.msg = "请求成功";
        result.setData(data);
        return result;
    }
    public static Result success(String msg){
        Result result = new Result();
        result.code = 1;
        result.msg = msg;
        return result;
    }
    public static Result fail(){
        Result result = new Result();
        result.code = 0;
        result.msg = "请求失败";
        return result;
    }
}
