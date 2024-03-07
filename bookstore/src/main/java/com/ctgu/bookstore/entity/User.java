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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员编号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "会员等级,默认等级为1 ")
    private Integer userLevel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "电话")
    private String phoneNumber;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "积分，默认积分为0 ")
    private Integer integral;

    @ApiModelProperty(value = "姓名")
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "头像,默认头像url")
    private String avatar;

    @ApiModelProperty(value = "加盐")
    private String salt;
}
