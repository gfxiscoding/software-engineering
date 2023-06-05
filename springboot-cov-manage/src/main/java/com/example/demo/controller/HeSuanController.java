package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Approval;
import com.example.demo.entity.HeSuan;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.HeSuanService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.vo.DataView;
import com.example.demo.vo.HeSuanVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.List;


@Controller
@RequestMapping("/hesuan")
public class HeSuanController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HeSuanService heSuanService;

    @RequestMapping("/toHeSuan")
    public String toHeSuan(){
        return "hesuan/hesuan";
    }

    @RequestMapping("/loadAllHeSuan")
    @ResponseBody
    public DataView loadAllHeSuan(HeSuanVo heSuanVo,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotEmpty(user.getCampusId())) {
            //获得用户id
            Integer uid = user.getId();
            //查到对应角色
            Role role = roleService.getById(user.getRid());

            IPage<HeSuan> page = new Page<>(heSuanVo.getPage(), heSuanVo.getLimit());
            QueryWrapper<HeSuan> queryWrapper = new QueryWrapper();

            //模糊查询，以校园身份号为条件
            //先模糊查询校园身份号对应的用户,如果校园身份号为空则查询不发生效力
            if(heSuanVo.getCampusId() != null){
                List<Integer> userVague = userService.getUserVague(heSuanVo.getCampusId());
                if(userVague.size() == 0){  //如果查询不到，则直接返回
                    return new DataView();
                }
                queryWrapper.in("uid",userVague);
            }
            //学生级只能看到自己的信息
            if(StringUtils.equals(role.getLevel(),"学生级")){
                queryWrapper.eq(uid != null, "uid", uid);
            }
            //院级和教师级用户能看到本院的信息，管理员级能看到所有信息
            else if(StringUtils.equals(role.getLevel(),"教师级") || StringUtils.equals(role.getLevel(),"院级")){
                Integer xueYuanId = user.getXueYuanId();
                List<Integer> sameXueYuan = userService.getSameXueYuan(xueYuanId);    //找到同学院的用户id集合
                queryWrapper.in("uid",sameXueYuan);
            }

            heSuanService.page(page, queryWrapper);
            // 添加连接查询后的属性
            List<HeSuan> records = page.getRecords();
            for (HeSuan a : records) {
                //获取uid，查到user记录
                User tuser = userService.getById(a.getUid());
                //再把相关信息更新到实体里面
                a.setName(tuser.getUsername());
                a.setAge(tuser.getAge());
                a.setPhone(tuser.getPhone());
                a.setCampusId(tuser.getCampusId());
            }
            return new DataView(page.getTotal(),records);
//            return new DataView(page.getTotal(), page.getRecords());
        }
        return new DataView();
    }

    @RequestMapping("/addHeSuan")
    @ResponseBody
    public DataView addHeSuan(HeSuan heSuan,HttpSession session){
        //获取uid
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        heSuan.setUid(uid);
        //保存
        heSuanService.save(heSuan);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加核酸检测成功！");
        return dataView;
    }

    @RequestMapping("/updateHeSuan")
    @ResponseBody
    public DataView updateHeSuan(HeSuan heSuan){
        //获取原先记录的uid
        heSuan.setUid(heSuanService.getById(heSuan.getId()).getUid());
        heSuanService.updateById(heSuan);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改核酸检测成功！");
        return dataView;
    }

    @RequestMapping("/deleteHeSuan")
    @ResponseBody
    public DataView deleteHeSuan(Integer id){
        //从数据库中删除核酸记录
        heSuanService.removeById(id);
        DataView dataView = new DataView();
        //返回状态码
        dataView.setCode(200);
        dataView.setMsg("删除核酸检测成功！");
        return dataView;
    }

}
