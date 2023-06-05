package com.example.demo.vo;

import com.example.demo.entity.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VaccineVo extends Vaccine {
    private Integer page;
    private Integer limit;
}
