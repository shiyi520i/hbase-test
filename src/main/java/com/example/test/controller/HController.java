package com.example.test.controller;


import com.example.test.gecco.JDGoodList;
import com.example.test.utils.HBaseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HController {
    @Autowired
    private HBaseTemplate hBaseTemplate;

 /*   @ModelAttribute
    public void init() {
        info = new ResultInfo();
    }*/


    //scan数据
    @GetMapping("/getByName")
    public Map<String, String> getByName(@RequestParam("key") String key) {
        Map<String, String> jdgoodllist = hBaseTemplate.getRowData("jdgoodllist", key);
        return jdgoodllist;
    }

    //scan数据
    @GetMapping("/getAll")
    public List<JDGoodList> getAll() throws IOException {
        List<JDGoodList> maps = hBaseTemplate.scanData("jdgoodlist");
        return maps;
    }
}

