package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("menu")
public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Integer open;
    private Integer available;
}
