package com.example.test.gecco;


import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(matchUrl="https://search.jd.com/Search?keyword=java&page={page}", pipelines={"consolePipeline", "productListPipeline"})
public class ProductDetail implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    /**
     *获取商品当前页数
     */
    @Text
    @HtmlField(cssPath="#J_topPage > span > b")
    private int currPage;

    /**
     * 获得商品列表的总页数
     */
    @Text
    @HtmlField(cssPath="#J_topPage > span > i")
    private int totalPage;

    @Request
    private HttpRequest request;

    /**
     *  自动点击商品详情列表
     */
    @Href(click=true)
    @HtmlField(cssPath=".p-name > a")
    private List<String> detailUrl;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<String> getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(List<String> detailUrl) {
        this.detailUrl = detailUrl;
    }


    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "currPage=" + currPage +
                ", totalPage=" + totalPage +
                ", request=" + request +
                ", detailUrl=" + detailUrl +
                '}';
    }

}
