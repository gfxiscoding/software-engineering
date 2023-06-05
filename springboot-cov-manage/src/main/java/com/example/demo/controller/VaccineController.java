package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.HeSuan;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.Vaccine;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.VaccineService;
import com.example.demo.vo.DataView;
import com.example.demo.vo.HeSuanVo;
import com.example.demo.vo.VaccineVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VaccineService vaccineService;

    @RequestMapping("/toVaccine")
    public String toVaccine(){
        return "vaccine/vaccine";
    }

    @RequestMapping("/loadAllVaccine")
    @ResponseBody
    public DataView loadAllVaccine(VaccineVo vaccineVo, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotEmpty(user.getCampusId())) {
            //获得用户id
            Integer uid = user.getId();
            //查到对应角色
            Role role = roleService.getById(user.getRid());
            IPage<Vaccine> page = new Page<>(vaccineVo.getPage(), vaccineVo.getLimit());
            QueryWrapper<Vaccine> queryWrapper = new QueryWrapper();


            //模糊查询，以校园身份号为条件
            //先模糊查询校园身份号对应的用户,如果校园身份号为空则查询不发生效力
            if(vaccineVo.getCampusId() != null){
                List<Integer> userVague = userService.getUserVague(vaccineVo.getCampusId());
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

            vaccineService.page(page, queryWrapper);
            // 添加连接查询后的属性
            List<Vaccine> records = page.getRecords();
            for (Vaccine a : records) {
                //获取uid，查到user记录
                User tuser = userService.getById(a.getUid());
                //再把相关信息更新到实体里面
                a.setName(tuser.getUsername());
                a.setAge(tuser.getAge());
                a.setPhone(tuser.getPhone());
                a.setIdentiCard(tuser.getIdentiCard());
                a.setCampusId(tuser.getCampusId());
            }
            return new DataView(page.getTotal(), page.getRecords());
        }
        return new DataView();
    }

    @RequestMapping("/addVaccine")
    @ResponseBody
    public DataView addHeSuan(Vaccine vaccine,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        vaccine.setUid(uid);
        vaccineService.save(vaccine);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加疫苗成功！");
        return dataView;
    }

    @RequestMapping("/updateVaccine")
    @ResponseBody
    public DataView updateHeSuan(Vaccine vaccine){
        vaccine.setUid(vaccineService.getById(vaccine.getId()).getUid());
        vaccineService.updateById(vaccine);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改疫苗成功！");
        return dataView;
    }

    @RequestMapping("/deleteVaccine")
    @ResponseBody
    public DataView deleteVaccine(Integer id){
        vaccineService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除疫苗成功！");
        return dataView;
    }
}
