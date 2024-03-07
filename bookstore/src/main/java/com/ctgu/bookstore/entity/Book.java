package com.ctgu.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Nidol
 * @since 2024-3-6
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Book对象", description="")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图书编号")
    @TableId(value = "ISBN", type = IdType.ID_WORKER)
    private String isbn;

    @ApiModelProperty(value = "书名")
    private String bookName;

    @ApiModelProperty(value = "图片地址")
    private String bookPicture;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "价格")
    private Float price;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "类型")
    private Integer bookType;

    @ApiModelProperty(value = "库存")
    private Integer repertory;

    @ApiModelProperty(value = "出版社")
    private String press;

    @ApiModelProperty(value = "收藏量")
    private Integer cllectNum;

    @ApiModelProperty(value = "月销量")
    private Integer saleNum;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "出版日期")
    private String publicDate;


}
