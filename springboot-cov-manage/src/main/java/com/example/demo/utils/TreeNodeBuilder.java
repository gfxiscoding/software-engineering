package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

//构造菜单栏树
public class TreeNodeBuilder {
    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topPid) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (TreeNode n1 : treeNodes) {
                //如果父菜单栏ID和顶层菜单栏ID一致，则添加到一级菜单列表
                if (n1.getPid()==topPid){
                    nodes.add(n1);
                }
                for (TreeNode n2 : treeNodes) {
                    //某一菜单栏的父菜单栏ID和某一菜单栏的ID一致
                    if (n1.getId()==n2.getPid()){
                        //添加到TreeNode结构中的子菜单栏列表中
                        n1.getChildren().add(n2);
                    }
                }
            }
        return nodes;
    }
}
