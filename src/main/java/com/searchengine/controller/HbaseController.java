package com.searchengine.controller;


import com.searchengine.utils.HBaseTemplate;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class HbaseController {
    @Autowired
    private HBaseTemplate hBaseTemplate;

    @GetMapping("getdata")
    public void getData()  {
        Map<String,String> hmap=null;
        hmap=hBaseTemplate.getRowData("jdgoodlist","12690647");
        System.out.println(hmap);
    }
    @GetMapping("/seachByName")
    public void geccodata(@RequestParam("key") String key) {
        System.out.println("=======start========");
        String surl="https://search.jd.com/Search?keyword="+key+"&page=3";
        HttpGetRequest startUrl = new HttpGetRequest(surl);
        startUrl.setCharset("GBK");
        GeccoEngine.create()
                //Gecco搜索的包路径
                .classpath("com/searchengine/gecco")
                //开始抓取的页面地址
                .start(startUrl)
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(1000)
                .run();
    }

}
