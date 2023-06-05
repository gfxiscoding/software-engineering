package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Approval;
import com.example.demo.mapper.ApprovalMapper;
import com.example.demo.service.ApprovalService;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {
}
