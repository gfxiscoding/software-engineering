package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("xue_yuan")
@Data
public class XueYuan {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
}
