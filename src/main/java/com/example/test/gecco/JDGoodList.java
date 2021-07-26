package com.example.test.gecco;


import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;


@Data
@Gecco(matchUrl="https://item.jd.com/{code}.html", pipelines={"consolePipeline","jdgoodlistpip"})
public class JDGoodList implements HtmlBean {
    private static final long serialVersionUID = 7474876333243181932L;

    /**
     * 商品代码
     *
     */
    @RequestParameter
    private String code;

    /**
     * 标题
     */

    @Text
    @HtmlField(cssPath=".itemInfo-wrap > div.sku-name")
    private String title;

    /**
     * ajax获取商品价格
     */

    //返回的数据格式[{"p":"160.30","op":"160.30","cbf":"0","id":"J_11339988","m":"236.00"}]
    @Ajax(url="https://p.3.cn/prices/mgets?skuIds=J_[code]")
    private JDPrice price;

    /**
     * 商品的推广语
     */

    //返回的数据格式,"ads":[{"ad":"C++、Java中的圣经级著作","id":"AD_11339988"}],
    @Ajax(url="http://cd.jd.com/promotion/v2?skuId={code}&area=1_2805_2855_0&cat=737%2C794%2C798")
    private JDad jdAd;

    /*
     * 商品所在店铺
     */

    @HtmlField(cssPath=".p-parameter > ul > li:nth-child(1) > a")
    private String detail;

    /*
     * 商品图片,第一张
     */

    @Image("data-origin")
    @HtmlField(cssPath="#spec-img")
    private String image;

    /**
     * 评价人数,好评率(返回htmml？？？？？？？？？)
     *
     */

    @Ajax(url="https://club.jd.com/comment/productCommentSummaries.action?referenceIds=[code]")
    //@Ajax(url="https://club.jd.com/comment/skuProductPageComments.action?productId={code}&score=0&sortType=5&page=0&pageSize=1&isShadowSku=0&fold=1")
    private JDComment comment;

    private String goodurl;

}