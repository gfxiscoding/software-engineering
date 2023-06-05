package com.example.demo.vo;

import com.example.demo.entity.Communitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommunitysVo extends Communitys {
    private Integer page;
    private Integer limit;
}
