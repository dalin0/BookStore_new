package com.ctgu.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Nidol
 * @Date: 2024-3-6
 */
@Data
@ApiModel(value = "返回图书分类数据对象", description = "")
public class ResultBookclassify {

    @ApiModelProperty(value = "每类数目")
    private int value;

    @ApiModelProperty(value = "类目名称")
    private String name;
}
