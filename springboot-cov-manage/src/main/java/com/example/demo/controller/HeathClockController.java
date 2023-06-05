package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.HeSuan;
import com.example.demo.entity.HealthClock;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.vo.HealthClockVo;
import com.example.demo.service.HealthClockService;
import com.example.demo.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
public class HeathClockController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthClockService healthClockService;

    // 跳转面
    @RequestMapping("/toHealthClock")
    public String toHealthClock(){
        return "healthclock/healthclock";
    }


    /**
     * 查询所有打卡记录 带有模糊查询条件 带有分页
     */
    @RequestMapping("/listHealthClock")
    @ResponseBody
    public DataView listHealthClock(HealthClockVo healthClockVo, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotEmpty(user.getCampusId())) {
            Integer uid = user.getId();
            //查到对应角色
            Role role = roleService.getById(user.getRid());
            IPage<HealthClock> page = new Page<>(healthClockVo.getPage(), healthClockVo.getLimit());
            QueryWrapper<HealthClock> queryWrapper = new QueryWrapper<>();

            //模糊查询，以校园身份号为条件
            //先模糊查询校园身份号对应的用户,如果校园身份号为空则查询不发生效力
            if(healthClockVo.getCampusId() != null){
                List<Integer> userVague = userService.getUserVague(healthClockVo.getCampusId());
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


            healthClockService.page(page, queryWrapper);
            // 添加连接查询后的属性
            List<HealthClock> records = page.getRecords();
            for (HealthClock a : records) {
                //获取uid，查到user记录
                User tuser = userService.getById(a.getUid());
                //再把相关信息更新到实体里面
                a.setUsername(tuser.getUsername());
                a.setAge(tuser.getAge());
                a.setPhone(tuser.getPhone());
                a.setCampusId(tuser.getCampusId());
                a.setSex(tuser.getSex());
            }
            return new DataView(page.getTotal(), records);
        }
        return new DataView();
    }

    /**
     * 添加或者修改健康打卡记录数据
     */
    @RequestMapping("/addHealthClock")
    @ResponseBody
    public DataView addHealthClock(HealthClock healthClock,HttpSession session){
        //获取uid
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        DataView dataView = new DataView();
        if(healthClockService.getById(healthClock.getId()) == null){
            healthClock.setUid(uid);
            dataView.setMsg("添加成功！");
        }
        else{
            healthClock.setUid(healthClockService.getById(healthClock.getId()).getUid());
            dataView.setMsg("修改成功！");
        }
        healthClock.setCreateTime(new Date());//更新打卡时间
        //保存
        boolean b = healthClockService.saveOrUpdate(healthClock);
        if (b){
            dataView.setCode(200);
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("添加或修改失败！");
        return dataView;
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteHealthClockById")
    @ResponseBody
    public DataView deleteHealthClockById(Integer id){
        healthClockService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除数据成功！");
        return dataView;
    }

}
