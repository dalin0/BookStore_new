package com.ctgu.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Bookclassify对象", description="")
public class Bookclassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    @ApiModelProperty(value = "类目名称")
    private String className;

    @ApiModelProperty(value = "分类图片")
    private String classPicture;


}
