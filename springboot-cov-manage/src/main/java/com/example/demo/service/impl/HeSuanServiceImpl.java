package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.HeSuan;
import com.example.demo.mapper.HeSuanMapper;
import com.example.demo.service.HeSuanService;
import org.springframework.stereotype.Service;


@Service
public class HeSuanServiceImpl extends ServiceImpl<HeSuanMapper, HeSuan> implements HeSuanService {
}
