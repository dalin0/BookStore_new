package com.ctgu.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论编号")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    @ApiModelProperty(value = "发表评论的用户id")
    private Integer userId;

    @ApiModelProperty(value = "评论的目标用户id")
    private Integer parentUserId;

    @ApiModelProperty(value = "评论的目标图书id")
    @TableField("ISBN")
    private String isbn;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "该评论被点赞的数量")
    private Long likeCount;

    @ApiModelProperty(value = "创建时间")
    private Date creatTime;


}
