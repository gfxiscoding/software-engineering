package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@TableName("vaccine")
@Data
public class Vaccine {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String zhenci;
    private String pici;
    private String changjia;
    private String danwei;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date createTime;
    //外键
    private Integer uid;
    //非表属性
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private String identiCard;
    @TableField(exist = false)
    private String campusId;//校园身份号

}
