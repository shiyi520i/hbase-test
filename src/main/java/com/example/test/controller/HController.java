package com.example.test.controller;


import com.example.test.gecco.JDGoodList;
import com.example.test.utils.HBaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class HController {
    @Autowired
    private HBaseTemplate hBaseTemplate;

 /*   @ModelAttribute
    public void init() {
        info = new ResultInfo();
    }*/


    //get数据
    @GetMapping("/getByName")
    public List<JDGoodList> getByName(@RequestParam("key") String key) throws IOException {
        List<JDGoodList> goodLists = hBaseTemplate.scanDataByKey(key);
        return goodLists;
    }

    //scan数据
    @GetMapping("/getAll")
    public List<JDGoodList> getAll() throws IOException {
        List<JDGoodList> maps = hBaseTemplate.scanData("jdgoodlist");
        return maps;
    }
}

