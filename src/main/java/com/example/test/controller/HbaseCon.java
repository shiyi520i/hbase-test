package com.example.test.controller;

import com.example.test.server.PutData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HbaseCon {
    @Autowired
    private PutData putData;
    //插入数据
    @GetMapping("test")
    public String putTest(){
        try {
            putData.putData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "yes";
    }
    //get数据
    @GetMapping("test1")
    public String getTest(){
        try {
            putData.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "yes";
    }
    //scan数据
    @GetMapping("test2")
    public String scanTest(){
        try {
            putData.scanData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "yes";
    }

}
