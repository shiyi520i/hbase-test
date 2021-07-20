package com.example.test.controller;



import com.example.test.utils.HBaseTemplate;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HbaseController {
    @Autowired
    private HBaseTemplate hBaseTemplate;
    @GetMapping("getdata")

    public void getData(){
        System.out.println(hBaseTemplate.getRowData("jdgoodlist","13284888"));

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
