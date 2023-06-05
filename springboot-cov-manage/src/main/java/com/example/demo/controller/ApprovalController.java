package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Approval;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.enums.ApprovalNodeStatusEnum;
import com.example.demo.service.ApprovalService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.vo.ApprovalVo;
import com.example.demo.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private ApprovalService approvalService;


    @RequestMapping("/toApproval")
    public String toHeSuan(){
        return "approval/approval";
    }

    //检查通过/审批是否合法
    @RequestMapping("/checkout")
    @ResponseBody
    public DataView checkout(ApprovalVo approvalVo,HttpSession session){
        //取出session中的user，先前在登录时将user存入过session
        User user = (User) session.getAttribute("user");
        DataView dataView = new DataView();
        if (StringUtils.isNotEmpty(user.getCampusId())){
            Role role = roleService.getById(user.getRid());
            //学生级角色不能进行审批
            if(role.getLevel().equals("学生级"))
            {
                dataView.setCode(100);
                dataView.setMsg(role.getName()+"角色不能进行审批！");
                return dataView;
            }
            //当状态不为在审核时，不能审批
            else if(approvalVo.getNodeStatus() == 2 ||  //教师驳回
                    approvalVo.getNodeStatus() == 4 ||  //院系驳回
                    approvalVo.getNodeStatus() == 5)    //审核通过
            {
                dataView.setCode(100);
                dataView.setMsg("此状态不能够去审批！");
                return dataView;
            }
            //院级和教师级角色不能越级审批
            else if(approvalVo.getNodeStatus() == 1 && role.getLevel().equals("院级") ||  //教师正在审核中
                    approvalVo.getNodeStatus() == 3 && role.getLevel().equals("教师级"))   //院系正在审核中
            {
                dataView.setCode(100);
                dataView.setMsg("此状态不能够去审批！");
                return dataView;
            }
            dataView.setCode(200);
            return dataView;
        }
        dataView.setMsg("用户状态异常，请重试！");
        return dataView;
    }

    /**
     * 加载请假申请
     */
    @RequestMapping("/loadApproval")
    @ResponseBody
    public DataView loadApproval(ApprovalVo approvalVo, HttpSession session){
        //取出session中的user，先前在登录时将user存入过session
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotEmpty(user.getCampusId())){
            Integer uid = user.getId();
            //查到对应角色
            Role role = roleService.getById(user.getRid());
            //构造page对象，approvalVo已经包含要分页的相关属性(一页的记录条数、分页数)
            IPage<Approval> page = new Page<>(approvalVo.getPage(),approvalVo.getLimit());
            QueryWrapper<Approval> queryWrapper = new QueryWrapper();
            //模糊查询，以校园身份号为条件
            //先模糊查询校园身份号对应的用户,如果校园身份号为空则查询不发生效力
            if(approvalVo.getCampusId() != null){
                List<Integer> userVague = userService.getUserVague(approvalVo.getCampusId());
                if(userVague.size() == 0){  //如果查询不到，则返回
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
            approvalService.page(page,queryWrapper);
            //构造实体对象，将其余要显示的属性从user中取到该实体对象里
            List<Approval> records = page.getRecords();
            for (Approval a : records) {
                //获取uid，查到user记录
                User tuser = userService.getById(a.getUid());
                //再把相关信息更新到实体里面
                a.setUsername(tuser.getUsername());
                a.setCampusId(tuser.getCampusId());
                a.setPhone(tuser.getPhone());
            }
            return new DataView(page.getTotal(),records);
        }
        return new DataView();
    }

    @RequestMapping("/addApproval")
    @ResponseBody
    public DataView addApproval(Approval approval,HttpSession session){
        approval.setCreateTime(new Date());
        //取出session中的user，先前在登录时将user存入过session
        User user = (User) session.getAttribute("user");
        //将不直接存在数据表内的显示属性放入实体，用于前端页面显示
        Integer uid = user.getId();
        String phone = user.getPhone();
        approval.setUid(uid);
        approval.setPhone(phone);
        //获取当前用户的角色
        Integer rid = user.getRid();
        Role role = roleService.getById(rid);
        String roleLevel = role.getLevel();
        //不同的角色提交请假申请后的审批状态不同
        if (StringUtils.equals(roleLevel,"学生级")){ //学生的提交结果是待老师审批
            approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_ING.getCode());
        }
        else if (StringUtils.equals(roleLevel,"教师级")){ //老师的提交结果是待学校审核
            approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_ING.getCode());
        }
        else { //管理员级、院级直接通过
            approval.setNodeStatus(ApprovalNodeStatusEnum.PASSED.getCode());
            approval.setUpdateTime(new Date());
        }
        //存入数据库
        approvalService.save(approval);
        DataView dataView = new DataView();
        //返回状态信息
        dataView.setCode(200);
        dataView.setMsg("申请请假成功！");
        return dataView;
    }

    //通过审批
    @RequestMapping("/successApproval")
    @ResponseBody
    public DataView successApproval(Approval approval,HttpSession session){
        DataView dataView = new DataView();
        approval.setUpdateTime(new Date());
        //取出session中的user，先前在登录时将user存入过session
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        //获取当前用户的角色
        Integer rid = user.getRid();
        Role role = roleService.getById(rid);
        String roleLevel = role.getLevel();
        String roleName = role.getName();
        /**由于先前已经通过checkout，所以无需考虑不合法情况*/
        // 查询节点状态
        Approval capproval = approvalService.getById(approval.getId());
        Integer nodeStatus = capproval.getNodeStatus();
        // 教师审核状态
        if (ApprovalNodeStatusEnum.TEACHER_ING.getCode() == nodeStatus){
            //管理员级直接通过审批
            if (StringUtils.equals(roleLevel,"管理员级")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.PASSED.getCode());
            }
            //教师可以执行审批权，变成教师通过审核状态
            else if (StringUtils.equals(roleLevel,"教师级")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_ING.getCode());
            }
        }
        //学院审核状态
        else if(ApprovalNodeStatusEnum.COLLEGE_ING.getCode() == nodeStatus){
            //管理员级、院级可以通过学院审核
            if (StringUtils.equals(roleLevel,"院级") || StringUtils.equals(roleLevel,"管理员级")){
                approval.setNodeStatus(ApprovalNodeStatusEnum.PASSED.getCode());
            }
        }
        // 将新的审批状态更新到数据库中
        approvalService.updateById(approval);
        dataView.setCode(200);
        dataView.setMsg(username+"：角色："+roleName+"审批成功！");
        return dataView;

    }

    //驳回审批
    @RequestMapping("/rejectApproval")
    @ResponseBody
    public DataView rejectApproval(Approval approval,HttpSession session){
        //放入页面显示属性：当前时间
        DataView dataView = new DataView();
        approval.setUpdateTime(new Date());
        //取出session中的user，先前在登录时将user存入过session
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        String username = user.getUsername();
        Integer rid = user.getRid();
        //取到当前用户所属角色
        Role role = roleService.getById(rid);
        String roleLevel = role.getLevel();//角色级别
        String roleName = role.getName();   //角色名
        /**由于先前已经通过checkout，所以无需考虑不合法情况*/
        // 查询节点状态
        Approval serviceById = approvalService.getById(approval.getId());
        Integer nodeStatus = serviceById.getNodeStatus();
        // 如果是审批中状态，才可以进行驳回
        // 教师审核状态
        if (ApprovalNodeStatusEnum.TEACHER_ING.getCode() == nodeStatus){
            if (StringUtils.equals(roleLevel,"管理员级") || StringUtils.equals(roleLevel,"教师级")){  //管理员级可执行任意驳回权，包括教师级驳回权
                approval.setNodeStatus(ApprovalNodeStatusEnum.TEACHER_REJECTED.getCode());
            }
        }
        //学院审核状态
        else if(ApprovalNodeStatusEnum.COLLEGE_ING.getCode() == nodeStatus){
            if (StringUtils.equals(roleLevel,"院级") || StringUtils.equals(roleLevel,"管理员级")){//管理员级可执行任意驳回权，包括院级驳回权
                approval.setNodeStatus(ApprovalNodeStatusEnum.COLLEGE_REJECTED.getCode());
            }
        }
        // 将新的审批状态更新到数据库中
        approvalService.updateById(approval);
        dataView.setCode(200);
        dataView.setMsg(username+"：角色："+roleName+"驳回成功！");
        return dataView;

    }
}
