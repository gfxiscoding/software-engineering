package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.ProvinData;

import java.util.List;


public interface IndexService extends IService<ProvinData> {

    List<ProvinData> listOrderByIdLimit34();
}
