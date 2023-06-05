package com.example.demo.vo;

import com.example.demo.entity.CovNews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CovNewsVo extends CovNews {
    private Integer page;
    private Integer limit;
}
