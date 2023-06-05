package com.example.demo.vo;

import com.example.demo.entity.Approval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApprovalVo extends Approval {
    private Integer page;
    private Integer limit;
}
