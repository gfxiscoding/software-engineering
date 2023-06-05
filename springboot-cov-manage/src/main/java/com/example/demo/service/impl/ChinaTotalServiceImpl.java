package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ChinaTotal;
import com.example.demo.mapper.ChinaTotalMapper;
import com.example.demo.service.ChinaTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper, ChinaTotal> implements ChinaTotalService {

    @Autowired
    private ChinaTotalMapper chinaTotalMapper;

    @Override
    public Integer maxID() {
        return chinaTotalMapper.maxID();
    }
}
