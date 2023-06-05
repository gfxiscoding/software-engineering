package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Vaccine;
import com.example.demo.mapper.VaccineMapper;
import com.example.demo.service.VaccineService;
import org.springframework.stereotype.Service;


@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {
}
