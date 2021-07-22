package com.example.test.gecco;

import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.spider.JsonBean;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@ResponseBody
public class JDComment implements JsonBean {

    private static final long serialVersionUID = 5656399878723726198L;

    @JSONPath("$.CommentsCount")
    private List<JSONObject> number;

    @JSONPath("$.CommentsCount.GoodRateShow")
    private String goodrate;

}
