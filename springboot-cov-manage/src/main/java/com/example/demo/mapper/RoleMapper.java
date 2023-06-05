package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select mid from role_menu where rid = #{roleId}")
    List<Integer> queryMidByRid(Integer roleId);

    // 1.分配菜单栏之前删除所有的rid数据
    @Delete("delete from role_menu where rid = #{rid}")
    void deleteRoleByRid(Integer rid);

    // 2.保存分配 角色 与 菜单 的关系
    @Insert("insert into role_menu(rid,mid) values (#{rid},#{mid})")
    void saveRoleMenu(Integer rid, Integer mid);

}
