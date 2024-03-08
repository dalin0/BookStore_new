package com.ctgu.bookstore.utils;


import com.ctgu.bookstore.entity.User;
import com.ctgu.bookstore.utils.vo.Result;
import com.ctgu.bookstore.utils.vo.ResultEnum;

public class ResultUtil {
    /**
     * 请求成功，返回自定义消息和数据
     * @param data
     * @param message
     * @return
     */
    User user;

    public static Result success(String message, Object data) {
        Result result = new Result(message,data);
        return result;
    }

    public static Result success(String message, Object data, User user) {
        Result result = new Result(message,data,user);
        return result;
    }

    public static Result error(String message, Object data) {
        Result result = new Result(message, data);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        return result;
    }

    /**
     * 请求成功，返回数据，消息和状态码使用默认值
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result(data);
        return result;
    }

    /**
     * 请求成功，返回数据，消息和状态码使用默认值
     * @param msg
     * @return
     */
    public static Result success(String msg) {
        Result result = new Result(msg);
        return result;
    }

    /**
     * 返回失败的提示信息
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result(resultEnum);
        return result;
    }

    /**
     * 返回错误提示信息
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setCode(-1);
        return result;
    }
}

