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
@ApiModel(value="Orderlist对象", description="")
public class Orderlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty(value = "会员编号")
    private Integer userId;

    @ApiModelProperty(value = "职员编号")
    private Integer empId;

    @ApiModelProperty(value = "支付金额")
    private Float amount;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "收货人姓名")
    private String orderName;

    @ApiModelProperty(value = "电话")
    private String orderTel;

    @ApiModelProperty(value = "地址")
    private String orderAddress;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "交易时间")
    private Date orderDate;


}
