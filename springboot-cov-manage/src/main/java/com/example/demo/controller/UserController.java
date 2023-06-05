package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.XueYuan;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.XueYuanService;
import com.example.demo.vo.DataView;
import com.example.demo.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private XueYuanService xueYuanService;
    @Autowired
    private RoleService roleService;



    @RequestMapping("/toUser")
    public String toUser(){
        return "user/user";
    }
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "user/changepassword";
    }
    @RequestMapping("/toUserInfo")
    public String toUserInfo(){
        return "user/userInfo";
    }

    /**
     * 分页查询用户数据，带有模糊查询条件(右模糊)
     */
    @RequestMapping("/loadAllUser")
    @ResponseBody
    public DataView getAllUser(UserVo userVo, HttpSession session){
        //查到对应角色
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        Role role = roleService.getById(user.getRid());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        //模糊查询，以校园身份号为条件
        //先模糊查询userVo中校园身份号对应的用户,如果校园身份号为空则查询不发生效力
        if(userVo.getCampusId() != null){
            List<Integer> userVague = userService.getUserVague(userVo.getCampusId());
            if(userVague.size() == 0){  //如果查询不到，则直接返回
                return new DataView();
            }
            queryWrapper.in("id",userVague);
        }
        //学生级只能看到自己的信息
        if(StringUtils.equals(role.getLevel(),"学生级")){
            queryWrapper.eq(uid != null, "id", uid);
        }
        //院级和教师级用户能看到本院的信息，管理员级能看到所有信息
        else if(StringUtils.equals(role.getLevel(),"教师级") || StringUtils.equals(role.getLevel(),"院级")){
            Integer xueYuanId = user.getXueYuanId();
            List<Integer> sameXueYuan = userService.getSameXueYuan(xueYuanId);    //找到同学院的用户id集合
            queryWrapper.in("uid",sameXueYuan);
        }
        IPage<User> iPage = userService.page(page, queryWrapper);

        for (User tuser : iPage.getRecords()){
            // 对学院名字进行赋值
            if (tuser.getXueYuanId()!=null){
                XueYuan xueYuan = xueYuanService.getById(tuser.getXueYuanId());
                tuser.setXueYuanName(xueYuan.getName());
            }
            // 对角色名进行赋值
            if (tuser.getRid()!=null){
                role = roleService.getById(tuser.getRid());
                tuser.setRoleName(role.getName());
            }
        }
        return new DataView(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 返回当前用户
     */

    @RequestMapping("/getNowUser")
    @ResponseBody
    public User getNowUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        int uid = user.getId();
        User dbUser = userService.getOne(new QueryWrapper<User>().eq("id", uid));
        return dbUser;
    }

    /**
     * 新增用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public DataView addUser(User user){
        user.setUsername(user.getCampusId());//初始时用户名和校园身份号一致
        user.setPassword("123456");     //默认的登录密码为123456
        user.setImg("/images/login.jpg");   //默认为/images/login.jpg
        DataView dataView = new DataView();
        //获取所有的用户
        List<User> list = userService.list();
        for(User tuser : list){
            if(user.getCampusId().equals(tuser.getCampusId())){  //如果修改后的校园身份号和其他冲突，则报错
                dataView.setMsg("添加失败，校园身份号不得重复！");
                dataView.setCode(100);
                return dataView;
            }
        }
        boolean flag = userService.save(user);
        if(flag){
            dataView.setMsg("添加用户成功！");
            dataView.setCode(200);
            return dataView;
        }
        dataView.setMsg("添加失败，请重试！");
        dataView.setCode(100);
        return dataView;

    }
    /**
     * 修改用户 update table where id = ?
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public DataView updateUser(User user){
        //获取所有的用户
        List<User> list = userService.list();
        //取得修改前的校园身份号
        String tcampusID = userService.getById(user.getId()).getCampusId();
        DataView dataView = new DataView();
        if(!user.getCampusId().equals(tcampusID)){    //只要修改前后的用户校园身份号一致，就一定没问题，只有不相等的时候才会出错
            for(User tuser : list){
                if(user.getCampusId().equals(tuser.getCampusId())){  //如果修改后的校园身份号和其他冲突，则报错
                    dataView.setMsg("修改失败，校园身份号不得重复！");
                    dataView.setCode(100);
                    return dataView;
                }
            }
        }
        boolean flag = userService.updateById(user);
        if(flag){
            dataView.setMsg("修改用户成功！");
            dataView.setCode(200);
            return dataView;
        }
        dataView.setMsg("修改用户失败，请重试！");
        dataView.setCode(100);
        return dataView;

    }

    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public DataView deleteUser(@PathVariable("id") Integer id){
        userService.removeById(id);
        DataView dataView = new DataView();
        dataView.setMsg("删除用户成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 初始化下拉列表的数据【学院】
     */
    @RequestMapping("/listAllXueYuan")
    @ResponseBody
    public List<XueYuan> listAllXueYuan(){
        List<XueYuan> list = xueYuanService.list();
        return list;
    }

    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd/{id}")
    @ResponseBody
    public DataView resetPwd(@PathVariable("id") Integer id){
        User user = new User();
        user.setId(id);
        user.setPassword("123456");
        userService.updateById(user);
        DataView dataView = new DataView();
        dataView.setMsg("用户重置密码成功！");
        dataView.setCode(200);
        return dataView;
    }

    /**
     * 修改密码
     * 首先判断密码对不对,如果对的话，走下面修改密码的逻辑【两次输入是否匹配，如果匹配则更新用户数据】
     */
    @RequestMapping("/changePassword")
    @ResponseBody
    public DataView changePassword(User user, String newPwdOne, String newPwdTwo){

        // 1.查询数据库旧密码对不对
        User byId = userService.getById(user.getId());
        if (StringUtils.equals(byId.getPassword(),user.getPassword())
                && StringUtils.equals(newPwdOne,newPwdTwo)){
            user.setPassword(newPwdOne);
            userService.updateById(user);
            DataView dataView = new DataView();
            dataView.setMsg("用户修改密码成功！");
            dataView.setCode(200);
            return dataView;
        }
        DataView dataView = new DataView();
        dataView.setMsg("用户修改密码失败，可能旧密码输错，可能两次密码输入不一致！");
        dataView.setCode(500);
        return dataView;

    }

    /**
     * 点击分配时候 初始化用户角色
     * 打开分配角色的弹出层
     * 根据ID查询所拥有的角色
     */
    @RequestMapping("/initRoleByUserId")
    @ResponseBody
    public DataView initRoleByUserId(Integer uid){  //这里的uid一定要和前端参数名相同
        // 1.查询所有角色
        List<Map<String, Object>> listMaps = roleService.listMaps();
        // 2.查询当前登录用户所拥有的角色
        User user = userService.getById(uid);
        Integer rid = user.getRid();
//        List<Integer> currentUserRoleIds = roleService.queryUserRoleById(id);
        // 3.让前端变为选中状态
        for (Map<String,Object> map : listMaps){
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
//            for (Integer rid : currentUserRoleIds){
            if (rid != null && rid.equals(roleId)){ //一定要判断rid是否为空
                LAY_CHECKED = true;
                break;
            }
//            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataView(Long.valueOf(listMaps.size()),listMaps);
    }

    /**
     * 保存用户所分配的角色
     */
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public DataView saveUserRole(Integer uid,Integer[] rid){
        User user = userService.getById(uid);
        user.setRid(rid[0]);
        boolean flag = userService.updateById(user);
//        userService.saveUserRole(uid,ids);
        DataView dataView = new DataView();
        if(flag){
            dataView.setCode(200);
            dataView.setMsg("用户的角色分配成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("用户的角色分配失败！");
        return dataView;
    }
}
