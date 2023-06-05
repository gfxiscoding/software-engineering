package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

    private Integer id;
    @JsonProperty("parentId") //返回的json的名称 parentId ,为了确定层级关系
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private Boolean available;
    //子菜单栏列表
    private List<TreeNode> children = new ArrayList<TreeNode>();

    /**
     * 0为不选中  1为选中
     */
    private String checkArr="0";
    /**
     * 首页左边导航菜单的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread,Boolean available) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
        this.available = available;
    }
    /**
     *  dtree的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread,Boolean available) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.available = available;
    }

    /**
     * 给角色分配权限的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr,Boolean available) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
        this.available = available;
    }
}
