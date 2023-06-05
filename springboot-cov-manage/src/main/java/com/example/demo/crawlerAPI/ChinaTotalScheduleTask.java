package com.example.demo.crawlerAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ChinaTotal;
import com.example.demo.entity.ProvinData;
import com.example.demo.service.ChinaTotalService;
import com.example.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ChinaTotalScheduleTask {

    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private IndexService indexService;

    /**
     * 定时执行，更新全国数据情况
     */
    @Scheduled(fixedDelay = 3600000)//定时触发器，设置定时时间为3600000ms，也即1小时
    public void updateChinaTotalToDB() throws Exception {
        HttpUtils httpUtils = new HttpUtils();
        //调用编写好的httpUtils类发送网络请求，接收返回的字符类型数据
        String string = httpUtils.getData();
        //通过分析返回来的数据结构进行json转换与内层抽取，取到想要的数据部分
        JSONObject jsonObject = JSONObject.parseObject(string);
        Object data = jsonObject.get("data");

        JSONObject jsonObjectData = JSONObject.parseObject(data.toString());
        Object chinaTotal = jsonObjectData.get("chinaTotal");
        Object lastUpdateTime = jsonObjectData.get("lastUpdateTime");

        JSONObject jsonObjectTotal = JSONObject.parseObject(chinaTotal.toString());
        Object total = jsonObjectTotal.get("total");
        //抽取到确诊人数、输入人数等
        JSONObject totalData = JSONObject.parseObject(total.toString());
        Object confirm = totalData.get("confirm");
        Object input = totalData.get("input");
        Object heal = totalData.get("heal");
        Object dead = totalData.get("dead");
        // 为程序实体赋值
        ChinaTotal dataEntity = new ChinaTotal();
        dataEntity.setConfirm((Integer) confirm);
        dataEntity.setInput((Integer) input);
        dataEntity.setHeal((Integer) heal);
        dataEntity.setDead((Integer) dead);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataEntity.setUpdateTime(format.parse(String.valueOf(lastUpdateTime)));

        // 写入数据库
        chinaTotalService.save(dataEntity);

        /**
         * 中国各个省份数据的自动刷新
         */
        // 拿到areaTree
        JSONArray areaTree = jsonObjectData.getJSONArray("areaTree");
        Object[] objects = areaTree.toArray();

        // 拿到中国的数据,中国各省数据位于第2个位置
        JSONObject jsonObject1 = JSONObject.parseObject(objects[2].toString());
        JSONArray children = jsonObject1.getJSONArray("children");
        Object[] objects1 = children.toArray();


        List<ProvinData> list = new ArrayList<>();
        for (int i = 0; i < objects1.length; i++) {
            ProvinData provinData = new ProvinData();
            JSONObject jsonObject2 = JSONObject.parseObject(objects1[i].toString());
            Object name = jsonObject2.get("name");// 省份名字
            Object timePro = jsonObject2.get("lastUpdateTime");// 省份更新数据时间
            Object total1 = jsonObject2.get("total");
            JSONObject jsonObject3 = JSONObject.parseObject(total1.toString());// total

            // 累计确诊人数
            Object confirm1 = jsonObject3.get("confirm");

            // 获取累计死亡、治愈人数
            Object heal1 = jsonObject3.get("heal");// 累计治愈数量
            Object dead1 = jsonObject3.get("dead");// 累计死亡数量

            // 现存确诊: 累计确诊-累计死亡-累计治愈
            int nowConfirm = Integer.parseInt(confirm1.toString()) - Integer.parseInt(heal1.toString()) - Integer.parseInt(dead1.toString());
            if(nowConfirm < 0 ){   //某些省份在网易上的数据有误，累计治愈和死亡人数大于了累计确诊人数，此时的现存确诊当做0来处理
                nowConfirm = 0;
            }
            // 赋值
            provinData.setName(name.toString());
            provinData.setValue(nowConfirm);

            if (timePro == null){
                provinData.setUpdateTime(new Date());
            }else {
                //时间格式转化，将字符串转换为日期
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                provinData.setUpdateTime(format1.parse(String.valueOf(timePro)));
            }
            list.add(provinData);
        }

        // 在IndexMapper中构建了实体类到各省现有确诊统计表的映射，所以调用其服务层的接口将各个省份的数据写入表
        indexService.saveBatch(list);
    }
}
