package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.CovNews;
import com.example.demo.service.CovNewsService;
import com.example.demo.vo.CovNewsVo;
import com.example.demo.vo.DataView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
@RequestMapping("/news")
public class CovNewsController {
    @Autowired
    private CovNewsService covNewsService;

    @RequestMapping("/toNews")
    public String toNews(){
        return "news/news";
    }


    @RequestMapping("/listNews")
    @ResponseBody
    public DataView listNews(CovNewsVo nocvNewsVo) {
        QueryWrapper<CovNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(nocvNewsVo.getTitle()),"title",nocvNewsVo.getTitle());
        IPage<CovNews> iPage = new Page<>(nocvNewsVo.getPage(),nocvNewsVo.getLimit());
        covNewsService.page(iPage,queryWrapper);
        return new DataView(iPage.getTotal(),iPage.getRecords());
    }



    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        covNewsService.removeById(id);
        DataView dataView = new DataView();
        dataView.setMsg("删除成功！");
        dataView.setCode(200);
        return dataView;
    }


    /**
     * 新增或者修改
     */
    @RequestMapping("/addOrUpdateNews")
    @ResponseBody
    public DataView addOrUpdate(CovNews covNews){
        covNews.setCreateTime(new Date());
        covNewsService.saveOrUpdate(covNews);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或者修改成功！");
        return dataView;
    }
}
