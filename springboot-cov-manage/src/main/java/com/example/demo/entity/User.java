package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("user")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String campusId;
    private String username;
    private String password;

    private String sex;
    private Integer age;
    private String address;
    private String img;
    private String phone;
    private String identiCard;

    /*作为id外键使用*/

    private Integer rid;
    private Integer xueYuanId;

    // 非表属性
    @TableField(exist = false)
    private String xueYuanName;

    @TableField(exist = false)
    private String roleName;


}
