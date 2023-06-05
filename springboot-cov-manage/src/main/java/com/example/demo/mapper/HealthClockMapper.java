package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.HealthClock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthClockMapper extends BaseMapper<HealthClock> {
}
