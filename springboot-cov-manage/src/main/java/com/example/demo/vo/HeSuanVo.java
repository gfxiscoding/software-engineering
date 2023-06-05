package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.HeSuan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HeSuanVo extends HeSuan {

    private Integer page;
    private Integer limit;
}
