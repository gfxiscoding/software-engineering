package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.HealthClock;
import com.example.demo.mapper.HealthClockMapper;
import com.example.demo.service.HealthClockService;
import org.springframework.stereotype.Service;


@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper, HealthClock> implements HealthClockService {
}
