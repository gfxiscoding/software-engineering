package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.CovNews;

import java.util.List;


public interface CovNewsService extends IService<CovNews> {
    List<CovNews> listNewsLimit5();
}
