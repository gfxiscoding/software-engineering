package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.ChinaTotal;


public interface ChinaTotalService extends IService<ChinaTotal> {

    Integer maxID();
}
