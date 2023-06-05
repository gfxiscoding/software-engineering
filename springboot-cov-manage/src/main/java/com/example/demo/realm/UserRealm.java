package com.example.demo.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //创建空条件构造器，用于数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //借助AuthenticationToken的getPrincipal方法取到登录信息中的校园身份号
        queryWrapper.eq("campus_id",token.getPrincipal().toString());
        //以queryWrapper构造好的条件获取用户信息表中最新的数据
        User user = userService.getOne(queryWrapper);
        //如果有该校园身份号对应的用户
        if (null != user){
            //验证密码的正确性
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            //如果验证成功，最终这里返回的信息info的值即为传入的第一个参数，也即user对象
            return info;
        }
        return null;
    }
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
