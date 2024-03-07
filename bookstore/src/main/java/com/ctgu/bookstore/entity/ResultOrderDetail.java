package com.ctgu.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返回订单详情数据对象", description = "")
public class ResultOrderDetail {

    @ApiModelProperty(value = "图书名称")
    private String bookName;

    @ApiModelProperty(value = "购买时单价")
    private float price;

    @ApiModelProperty(value = "购买数量")
    private Integer num;

    @ApiModelProperty(value = "图片链接")
    private String bookPicture;
}
