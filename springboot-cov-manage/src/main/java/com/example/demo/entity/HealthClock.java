package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@TableName("health_clock")
@Data
public class HealthClock {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String morningTemp;
    private String afternoonTemp;
    private String nightTemp;
    private String recentZone;
    private String riskZone;

    private String healthStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //外键
    private Integer uid;

    @TableField(exist = false)
    private String username;//用户名
    @TableField(exist = false)
    private Integer age;//年龄
    @TableField(exist = false)
    private String phone;//手机
    @TableField(exist = false)
    private String campusId;//学号
    @TableField(exist = false)
    private String sex;//性别
}
