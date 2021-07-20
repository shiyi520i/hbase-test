package com.example.test.moudel;


import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;
import lombok.ToString;

/**
 * 创建时间 2021/7/1 23:42
 * 获取详细数据的实体类，排除type1字段
 * @author
 * @version 1.0
 */
@Data
@ToString(exclude = {"type1"})
@Gecco(matchUrl="https://movie.douban.com/subject/{url}/", pipelines={"consolePipeline", "productDetailPipeline"})
public class MovieDao implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    @RequestParameter
    private String url;

    //电影名
    @Text
    @HtmlField(cssPath="#content > h1 > span:nth-child(1)")
    private String title;

    //导演
    @Text
    @HtmlField(cssPath="#info > span:nth-child(1) > span.attrs > a")
    private String director;

    //编剧
    @Text
    @HtmlField(cssPath="#info > span:nth-child(3) > span.attrs > a:nth-child(1)")
    private String writer;

    //类型
    @Text
    @HtmlField(cssPath="#info > span:nth-child(8)")
    private String type;

    //类型
    @Text
    @HtmlField(cssPath="#info > span:nth-child(9)")
    private String type1;

    //评价人数
    @Text
    @HtmlField(cssPath="#interest_sectl > div.rating_wrap.clearbox > div.rating_self.clearfix > div > div.rating_sum > a > span")
    private String number;

    //评价人数
    @Text
    @HtmlField(cssPath="#interest_sectl > div.rating_wrap.clearbox > div.rating_self.clearfix > strong")
    private String score;

    @Override
    public String toString() {
        return "ProductDetail{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", type='" + type + '\'' +
                ", type1='" + type1 + '\'' +
                ", number='" + number + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
