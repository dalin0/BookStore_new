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
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职员编号")
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    @ApiModelProperty(value = "密码")
    private String empPassword;

    @ApiModelProperty(value = "电话")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "工资")
    private Float salary;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "居住地址")
    private String address;

    @ApiModelProperty(value = "头像,默认头像url")
    private String avatar;

    @ApiModelProperty(value = "是否为超级管理员")
    private String isAdmin;



}
