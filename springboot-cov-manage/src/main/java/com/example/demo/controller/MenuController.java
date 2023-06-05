package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.MenuService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.utils.TreeNode;
import com.example.demo.utils.TreeNodeBuilder;
import com.example.demo.vo.DataView;
import com.example.demo.vo.MenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    @RequestMapping("/toMenu")
    public String toMenu(){
        return "menu/menu";
    }

    /**
     * 加载所有的菜单
     */
    @RequestMapping("/loadAllMenu")
    @ResponseBody
    public DataView loadAllMenu(MenuVo menuVo){
        IPage<Menu> page = new Page<>(menuVo.getPage(),menuVo.getLimit());
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuVo.getTitle()),"title",menuVo.getTitle());
        queryWrapper.orderByAsc("id");
        menuService.page(page,queryWrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    /**
     * 加载下拉菜单数据 //初始化下拉树
     */
    @RequestMapping("loadMenuManagerLeftTreeJson")
    @ResponseBody
    public DataView loadMenuManagerLeftTreeJson(){
        List<Menu> list = menuService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu menu:list){
            Boolean open = menu.getOpen() == 1?true:false;
            Boolean available = menu.getAvailable() == 1?true:false;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),open,available));
        }
        return new DataView(treeNodes);
    }


    /**
     * 赋值最大的排序吗+1
     * 条件查询：倒序排序，取一条数据，  +1
     */
    @RequestMapping("/loadMenu")
    @ResponseBody
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String,Object> map =new HashMap<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<Menu> page = new Page<>(1,1);
        List<Menu> list = menuService.page(page, queryWrapper).getRecords();
        map.put("value",list.get(0).getId()+1);
        return map;
    }

    /**
     * 新增菜单逻辑
     */
    @RequestMapping("/addMenu")
    @ResponseBody
    public DataView addMenu(Menu menu){
        DataView dataView = new DataView();

        boolean save = menuService.save(menu);
        if (!save){
            dataView.setCode(100);
            dataView.setMsg("菜单添加失败");
        }
        dataView.setMsg("菜单添加成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 更新菜单
     */
    @RequestMapping("/updateMenu")
    @ResponseBody
    public DataView updateMenu(Menu menu){
        menuService.updateById(menu);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("更新菜单成功！");
        return dataView;
    }

    /**
     * 判断有没有子类ID
     * 没有子类ID，可以删除【】
     */
    @RequestMapping("/checkMenuHasChildrenNode")
    @ResponseBody
    public Map<String,Object> checkChildrenNode(Menu menu){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",menu.getId());
        List<Menu> list = menuService.list(queryWrapper);
        if (list.size()>0){
            map.put("value",true);
        }else {
            map.put("value",false);

        }
        return map;
    }

    /**
     * 真正的删除
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public DataView deleteMenu(Menu menu){
        menuService.removeById(menu.getId());
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除菜单成功！");
        return dataView;
    }

    /**
     * 加载主页面index的菜单栏，带有层级关系
     * 【不同的用户登录看到不同的菜单栏】查询条件
     */
    @RequestMapping("/loadIndexLeftMenuJson")
    @ResponseBody
    public DataView loadIndexLeftMenuJson(HttpSession session){

        // 查询的所有菜单栏 按照条件查询
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu> list = null;
        // 取出session中的用户ID
        User user = (User) session.getAttribute("user");
        Integer rid = user.getRid();
        // 查找到用户所属角色的角色ID
        Role role = roleService.getById(rid);
        //根据角色ID查询菜单ID
            List<Integer> permissionIds = roleService.queryAllPermissionByRid(rid);
        if (permissionIds.size()>0){
            queryWrapper.in("id",permissionIds);//in语句
            list = menuService.list(queryWrapper);//列出具有访问权限的菜单栏
        }
        // 构造层级关系
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu m : list){
            //构造节点对象列表
            Integer id = m.getId();
            Integer pid = m.getPid();
            String title = m.getTitle();
            String icon = m.getIcon();
            String href = m.getHref();
            Boolean open = m.getOpen().equals(1)?true:false;
            Boolean available = m.getAvailable().equals(1)?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,open,available));
        }
        //生成菜单栏树
        List<TreeNode> nodeList = TreeNodeBuilder.build(treeNodes, 0);//顶层菜单ID为0号
        //返回给前端
        return new DataView(nodeList);
    }

}
