package com.example.test.controller;


import com.example.test.gecco.JDGoodList;
import com.example.test.server.OptData;
import com.example.test.utils.HBaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class HController {
    @Autowired
    private HBaseTemplate hBaseTemplate;
    private OptData optData;
    private static int length;


    //get数据
    @GetMapping("/getByName")
    public List<JDGoodList> getByName(@RequestParam("key") String key) throws IOException {
        long start = System.currentTimeMillis();
        List<JDGoodList> goodLists = hBaseTemplate.scanDataByKey("jdgoodlist",key);
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        return goodLists;
    }

/*    //vue页面获取的数据
    @GetMapping("/getAll/{keyword}")
    public List<JDGoodList> getAll(@PathVariable("keyword") String keyword) throws IOException {
        long start = System.currentTimeMillis();
        List<JDGoodList> maps = hBaseTemplate.seachHighlightData("jdgoodlist",keyword);
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        return maps;
    }*/

    //bs页面获取的数据
    @GetMapping("/getAll")
    public List<JDGoodList> getAllData() throws IOException {
        long start = System.currentTimeMillis();
        List<JDGoodList> maps = hBaseTemplate.scanData("jdgoodlist");
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        return maps;
    }

    //测试数据
    @GetMapping("/getAll/{keyword}")
    public List<JDGoodList> test(@PathVariable("keyword") String keyword) throws IOException {
        long start = System.currentTimeMillis();
        optData=new OptData();
        List<JDGoodList> goodLists = optData.kGetData(keyword);
        System.out.println(goodLists);
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
         return goodLists;
    }

    //测试数据
    @GetMapping("/getKeyData/{keyword}")
    public List<JDGoodList> test1(@PathVariable("keyword") String keyword,
                                  @RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize) throws IOException {
        long start = System.currentTimeMillis();
        optData=new OptData();
        List<JDGoodList> goodLists = optData.keyGetData(keyword,pageNum,pageSize);
        length=optData.getLength();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        return goodLists;
    }
}

