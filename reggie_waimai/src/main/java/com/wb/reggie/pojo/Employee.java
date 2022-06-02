package com.wb.reggie.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @Description: 静态资源配置类
 * @Title: WebMvcConfig
 * @Package com.wb.reggie.config
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/24 18:16
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    //@JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
