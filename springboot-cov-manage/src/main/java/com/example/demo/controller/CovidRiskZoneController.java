package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.crawlerAPI.CovidRiskZoneApi;
import com.example.demo.entity.Communitys;
import com.example.demo.service.CommunitysService;
import com.example.demo.vo.CommunitysVo;
import com.example.demo.vo.DataView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
public class CovidRiskZoneController {

    @Autowired
    private CommunitysService communitysService;

    // /zone/toZone
    @RequestMapping("/zone/toZone")
    public String toZone(){
        return "zone/zone";
    }


    @RequestMapping("/zone/searchRiskZone")
    @ResponseBody
    public DataView searchCovidRiskZoneData(CommunitysVo communitysVo,String province, String city, String county) throws IOException {

        // 必填项
        if (StringUtils.isEmpty(province)){
            province = "所有";//默认为所有地区
        }


        // 1.解析接口数据
        String riskZoneData = CovidRiskZoneApi.getCovidRiskZoneData(province,city,county);

        // 2.拿到社区的数据封装List集合
        List<Communitys> list = new ArrayList<>();

        // fastjson解析实体
        if (StringUtils.isNotEmpty(riskZoneData)){

            JSONObject jsonObject = JSONObject.parseObject(riskZoneData);
            JSONObject data = jsonObject.getJSONObject("data");
            //控制台进行信息打印
            log.info("风险地区接口返回数据：{}",data.toString());
            //Integer high_count = (Integer) data.get("high_count");
            Integer high_count = data.getInteger("high_count");
            String end_update_time = (String) data.get("end_update_time");
            log.info("高风险地区截止统计时间：{}",end_update_time);
            // 解析
            JSONArray high_list = data.getJSONArray("high_list");
            log.info("高风险地区 high_list：{}", high_list);
            for (int i = 0; i < high_list.size(); i++) {
                JSONObject high_listJSONObject = high_list.getJSONObject(i);
                String area_name = (String) high_list.getJSONObject(i).get("area_name");
                log.info("高风险地区 :{}",high_listJSONObject);
                JSONArray communitys = high_listJSONObject.getJSONArray("communitys");
                for (int j = 0; j < communitys.size(); j++) {
                    String s = communitys.get(j).toString();
                    Communitys community = new Communitys();
                    community.setCommunity(s);
                    community.setTime(end_update_time);
                    community.setArea_name(area_name);
                    list.add(community);
                }
            }

            return new DataView(list);
        }
        return null;
    }

}
