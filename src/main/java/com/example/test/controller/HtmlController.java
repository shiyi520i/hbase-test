package com.example.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HtmlController {
    @RequestMapping("/")
    public String getini(){
          return "index";
      }
    @RequestMapping("/test")
    public String gett(){
        return "test";
    }
}
