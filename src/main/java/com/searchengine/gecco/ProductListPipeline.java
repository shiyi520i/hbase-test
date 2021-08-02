package com.searchengine.gecco;



import com.searchengine.server.OptData;
import org.apache.commons.lang3.StringUtils;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("productListPipeline")
public class ProductListPipeline implements Pipeline<ProductDetail> {
    @Override
    public void process(ProductDetail productDetail) {
        //输出爬取到的商品列表总个数
        System.out.println(productDetail.getDetailUrl().size());
        System.out.println(productDetail.getCodeList().size());
        System.out.println(productDetail.getKeyword());
        new OptData().cPutData(productDetail,productDetail.getKeyword());
        HttpRequest currRequest = productDetail.getRequest();
        //输出当前页
        System.out.println(productDetail.getCurrPage());
        System.out.println(productDetail.getTotalPage());
        //下一页继续抓取
        int currPage = productDetail.getCurrPage();
        int totalPage = productDetail.getTotalPage();
        String nextUrl = "";
        //获取当前页面的url
        String currUrl = currRequest.getUrl();
        //下一页地址函数
        nextUrl = StringUtils.replaceOnce(currUrl, "page=" +(2*currPage-1), "page=" + (2*(currPage+1)-1));
        //将下一页地址添加到队列中
        SchedulerContext.into(currRequest.subRequest(nextUrl));

    }}



/*        HttpRequest currRequest = productList.getRequest();
        //下一页继续抓取
        int currPage = productList.getCurrPage();
        int nextPage = currPage + 1;
        int totalPage = productList.getTotalPage();
        if(nextPage <= totalPage) {
            String nextUrl = "";
            String currUrl = currRequest.getUrl();
            if(currUrl.indexOf("page=") != -1) {
                nextUrl = StringUtils.replaceOnce(currUrl, "page=" + currPage, "page=" + nextPage);
            } else {
                nextUrl = currUrl + "&" + "page=" + nextPage;
            }
            SchedulerContext.into(currRequest.subRequest(nextUrl));
        }
    }*/


