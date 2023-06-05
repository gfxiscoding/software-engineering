package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where campus_id = #{campus_id} and password = #{password}")
    User login(String campus_id, String password);

    @Select("select id from user where campus_id like concat(#{campus_id},'%') ")
    List<Integer> getUserVague(String campus_id);

    @Select("select id from user where xue_yuan_id = #{xue_yuan_id}")
    List<Integer> getSameXueYuan(Integer xue_yuan_id);

}
