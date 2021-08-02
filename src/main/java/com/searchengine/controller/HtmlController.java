package com.searchengine.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HtmlController {
    @RequestMapping("/")
    public String getini(){
          return "index";
      }
    @RequestMapping("/vue")
    public String gett(){
        return "index-vue";
    }
    @RequestMapping("/vue1")
    public String gettt(){
        return "test5";
    }
}
