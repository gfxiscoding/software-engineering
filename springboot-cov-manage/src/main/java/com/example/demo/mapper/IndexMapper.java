package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ProvinData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndexMapper extends BaseMapper<ProvinData> {

    @Select("select * from provin_data order by id desc limit 34")
    List<ProvinData> listOrderByIdLimit34();
}
