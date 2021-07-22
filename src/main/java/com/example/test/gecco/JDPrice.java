package com.example.test.gecco;

import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.spider.JsonBean;

public class JDPrice implements JsonBean {

    private static final long serialVersionUID = -5696033709028657709L;

    @JSONPath("$.id[0]")
    private String code;

    @JSONPath("$.p[0]")
    private String price;

    @JSONPath("$.m[0]")
    private float srcPrice;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getSrcPrice() {
        return srcPrice;
    }

    public void setSrcPrice(float srcPrice) {
        this.srcPrice = srcPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JDPrice{" +
                "code='" + code + '\'' +
                ", price=" + price +
                ", srcPrice=" + srcPrice +
                '}';
    }
}
