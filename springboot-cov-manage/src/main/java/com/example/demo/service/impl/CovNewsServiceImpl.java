package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.CovNews;
import com.example.demo.mapper.CovNewsMapper;
import com.example.demo.service.CovNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CovNewsServiceImpl extends ServiceImpl<CovNewsMapper, CovNews> implements CovNewsService {

    @Autowired
    private CovNewsMapper covNewsMapper;

    @Override
    public List<CovNews> listNewsLimit5() {
        return covNewsMapper.listNewsLimit5();
    }
}
