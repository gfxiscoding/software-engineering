package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@TableName("approval_process")
public class Approval {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String reason;
    private String address;
    private Integer day;


    // 和枚举类型对应
    private Integer nodeStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;            //审批时间

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String campusId;
    @TableField(exist = false)
    private String phone;
}
