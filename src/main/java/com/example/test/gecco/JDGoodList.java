package com.example.test.gecco;


import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.spider.HtmlBean;


@Gecco(matchUrl="https://item.jd.com/{code}.html", pipelines={"consolePipeline","jdgoodlistpip"})
public class JDGoodList implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    /**
     * 商品代码
     */
    @RequestParameter
    private String code;

    /**
     * 标题
     */

    @Text
    //@HtmlField(cssPath="#name > h1")
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
     * 商品规格参数
     */

    //@HtmlField(cssPath="#product-detail-2")
    @HtmlField(cssPath=".p-parameter > ul")
    private String detail;

    /*
     * 商品图片,第一张
     */

    @Image("data-origin")
    @HtmlField(cssPath="#spec-img")
    private String image;

    @Override
    public String toString() {
        return "JDGoodList{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", jdAd='" + jdAd + '\'' +
                ", detail='" + detail + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public JDPrice getPrice() {
        return price;
    }

    public void setPrice(JDPrice price) {
        this.price = price;
    }

    public JDad getJdAd() {
        return jdAd;
    }

    public void setJdAd(JDad jdAd) {
        this.jdAd = jdAd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}