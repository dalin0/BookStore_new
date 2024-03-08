package com.ctgu.bookstore.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * @author Administrator
 */
@Data
@ApiModel(value = "登陆验证返回对象", description = "")
public class Result {

    @ApiModelProperty(value = "返回code,1为失败，0为成功")
    private int code;

    @ApiModelProperty(value = "返回提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    @ApiModelProperty(value = "返回token")
    private String token;

    public Result(){

    }
    public Result(int code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }
}
