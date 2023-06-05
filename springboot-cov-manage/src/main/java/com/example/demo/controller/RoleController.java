package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Role;
import com.example.demo.service.MenuService;
import com.example.demo.service.RoleService;
import com.example.demo.utils.TreeNode;
import com.example.demo.vo.DataView;
import com.example.demo.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    @RequestMapping("/toRole")
    public String toRole(){
        return "role/role";
    }

    /**
     * 查询所有的角色带有分页，带有查询条件
     */
    @RequestMapping("loadAllRole")
    @ResponseBody
    public DataView loadAllRole(RoleVo roleVo){
        IPage<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getLevel()),"level",roleVo.getLevel());
        roleService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加角色
     */
    @RequestMapping("addRole")
    @ResponseBody
    public DataView addRole(Role role, HttpSession session){
        roleService.save(role);
        DataView dataView = new DataView();
        dataView.setMsg("添加角色成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 修改角色
     */
    @RequestMapping("updateRole")
    @ResponseBody
    public DataView updateRole(Role role){
        roleService.saveOrUpdate(role);
        DataView dataView = new DataView();
        dataView.setMsg("修改角色成功！");
        dataView.setCode(200);
        return dataView;
    }


    /**
     * 添加角色
     */
    @RequestMapping("deleteRole")
    @ResponseBody
    public DataView deleteRole(Role role){
        roleService.removeById(role.getId());
        DataView dataView = new DataView();
        dataView.setMsg("删除角色成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 初始化下拉列表的具有权限
     * 根据角色查询菜单
     * select mid from role_menu where rid = ?
     */
    @RequestMapping("initPermissionByRoleId")
    @ResponseBody
    public DataView initPermissionByRoleId(Integer roleId){
        // 1.把所有的菜单权限查出来
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu> allPermissions = menuService.list();
        // 2.根据角色rid查询所有的菜单mid
        List<Integer> currentRolePermissions = roleService.queryAllPermissionByRid(roleId);
        // 3.查询菜单数据
        List<Menu> menus = null;
        if (currentRolePermissions.size() > 0){
            queryWrapper.in("id",currentRolePermissions);
            menus = menuService.list(queryWrapper);
        }else {
            menus = new ArrayList<>();
        }
        // 4.返回前台的格式，带有层级关系
        List<TreeNode> nodes = new ArrayList<>();
        for (Menu allPermission : allPermissions){
            String checkArr = "0";
            for (Menu currentPermission : menus){
                if (allPermission.getId().equals(currentPermission.getId())){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (allPermission.getOpen()==null || allPermission.getOpen() == 1) ?true:false;
            Boolean available = allPermission.getAvailable() == 1 ?true:false;
            nodes.add(new TreeNode(allPermission.getId(),allPermission.getPid(),allPermission.getTitle(),spread,checkArr,available));
        }
        return new DataView(nodes);
    }


    /**
     * 点击确认保存分配权限的时候
     * 插入数据库表【role_menu】
     */
    @RequestMapping("/saveRolePermission")
    @ResponseBody
    public DataView saveRolePermission(Integer rid, Integer[] mids){
        // 1.根据rid删除之前的mid权限
        roleService.deleteRoleByRid(rid);
        // 2.保存权限
        if (mids!=null && mids.length>0){
            for (Integer mid : mids){
                roleService.saveRoleMenu(rid,mid);
            }
        }
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("菜单权限分配成功！");
        return dataView;
    }

}
