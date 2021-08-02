package com.searchengine.gecco;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;

public class Main {
    public static void main(String[] args) {
        System.out.println("=======start========");
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
