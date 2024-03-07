package com.ctgu.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@ApiModel(value="Orderdetail对象", description="")
public class Orderdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    @TableId(value = "order_id", type = IdType.ID_WORKER)
    private Integer orderId;

    @ApiModelProperty(value = "图书编号")
    @TableField("ISBN")
    private String isbn;

    @ApiModelProperty(value = "购买单价")
    private Float price;

    @ApiModelProperty(value = "购买数量")
    private Integer num;


}
