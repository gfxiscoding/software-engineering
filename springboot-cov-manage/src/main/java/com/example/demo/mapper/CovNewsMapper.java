package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CovNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CovNewsMapper extends BaseMapper<CovNews> {

    @Select("select * from cov_news order by create_time desc limit 5")
    List<CovNews> listNewsLimit5();
}
