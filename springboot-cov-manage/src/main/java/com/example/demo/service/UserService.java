package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;

import java.util.List;


public interface UserService extends IService<User> {
    User login(String username, String password);

    List<Integer> getSameXueYuan(Integer xue_yuan_id);

    List<Integer> getUserVague(String campus_id);

}
