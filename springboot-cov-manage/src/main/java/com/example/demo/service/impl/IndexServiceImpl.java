package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ProvinData;
import com.example.demo.mapper.IndexMapper;
import com.example.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IndexServiceImpl extends ServiceImpl<IndexMapper, ProvinData> implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<ProvinData> listOrderByIdLimit34() {
        return indexMapper.listOrderByIdLimit34();
    }
}
