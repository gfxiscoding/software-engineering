package com.example.demo.controller;

import com.example.demo.entity.ChinaTotal;
import com.example.demo.entity.ProvinData;
import com.example.demo.entity.CovNews;
import com.example.demo.service.ChinaTotalService;
import com.example.demo.service.IndexService;
import com.example.demo.service.CovNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;


@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private CovNewsService covNewsService;

    /**
     * 主控页面【嵌套china】
     */
    @RequestMapping("/")
    public String index(Model model) throws ParseException {

        // 1.找到ID最大的那一条数据
        Integer id = chinaTotalService.maxID();
        // 2.根据ID进行查找数据
        ChinaTotal chinaTotal = chinaTotalService.getById(id);
        model.addAttribute("chinaTotal",chinaTotal);
        return "index";
    }

    /**
     * China地图页面
     */
    @RequestMapping("/toChina")
    public String toChina(Model model) throws ParseException {

        // 1.找到ID最大的那一条数据
        Integer id = chinaTotalService.maxID();
        // 2.根据ID进行查找数据
        ChinaTotal chinaTotal = chinaTotalService.getById(id);
        model.addAttribute("chinaTotal",chinaTotal);
        // 3.疫情播报新闻
        List<CovNews> newsList = covNewsService.listNewsLimit5();
        model.addAttribute("newsList",newsList);
        return "china";
    }

    @RequestMapping("/query")
    @ResponseBody
    public List<ProvinData> queryData() throws ParseException {
        List<ProvinData> list = indexService.listOrderByIdLimit34();
        return list;
    }

}
