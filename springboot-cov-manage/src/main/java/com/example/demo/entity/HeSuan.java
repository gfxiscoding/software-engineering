package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("hesuan")
@Data
public class HeSuan {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;// 【混检，单检，咽喉，鼻式...】
    private String result;// 核酸结果【未出结果，阴性，阳性】

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;// 核酸检测时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resultTime;// 报告出来时间
    //外键
    private Integer uid;
    @TableField(exist = false)
    private String name;//姓名
    @TableField(exist = false)
    private Integer age;//年龄
    @TableField(exist = false)
    private String phone;//手机
    @TableField(exist = false)
    private String campusId;//校园身份号
}
