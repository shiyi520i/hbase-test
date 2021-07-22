package com.example.test.controller;



import com.example.test.utils.HBaseTemplate;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.RegionLocator;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
    @GetMapping("gecco")
    public void geccodata(String keyword){

        System.out.println("=======start========");
        String url="https://search.jd.com/Search?keyword="+keyword+"&page=1";
        HttpGetRequest startUrl = new HttpGetRequest("https://search.jd.com/Search?keyword=java&page=1");
        startUrl.setCharset("GBK");
        GeccoEngine.create()
                //Gecco搜索的包路径
                .classpath("com/example/test/gecco")
                //开始抓取的页面地址
                .start(startUrl)
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(1000)
                .run();
    }

}
