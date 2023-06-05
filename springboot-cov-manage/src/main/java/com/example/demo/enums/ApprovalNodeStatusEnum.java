package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 请假审批节点的枚举类型
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ApprovalNodeStatusEnum {

    TEACHER_ING(1,"教师正在审核中"),
    TEACHER_REJECTED(2,"教师驳回"),
    COLLEGE_ING(3,"院系正在审核中"),
    COLLEGE_REJECTED(4,"院系驳回"),
    PASSED(5,"审批通过")
    ;

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
