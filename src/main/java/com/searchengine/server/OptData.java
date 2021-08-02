package com.searchengine.server;

import com.searchengine.gecco.JDGoodList;
import com.searchengine.gecco.ProductDetail;
import com.searchengine.utils.ApplicationContextHelperUtil;
import com.searchengine.utils.HBaseResult;
import com.searchengine.utils.HBaseTemplate;
import com.searchengine.utils.ObjectUtils;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptData {

     //引入hbaseTemplate模板
     private static HBaseTemplate hBaseTemplate =(HBaseTemplate) ApplicationContextHelperUtil.getBean(HBaseTemplate.class);
     private  int length;

     /**
      * @Description:
      * 插入一条数据
      * @Time: 2021/7/26 11:48
      * @param jdGoodList
      * @Return: void
      */

    public  void tPutData(JDGoodList jdGoodList){
        //rowkey
        Put put = new Put(Bytes.toBytes(jdGoodList.getCode()));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("code"), Bytes.toBytes(jdGoodList.getCode()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getTitle()));
    /*    put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getComment().getNumber()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getComment().getGoodrate()));*/
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("price"), Bytes.toBytes(jdGoodList.getPrice().getPrice()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("image"), Bytes.toBytes(jdGoodList.getImage()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("shop"), Bytes.toBytes(jdGoodList.getDetail()));

        hBaseTemplate.putRowData("jdgoodlist",put);

    }

    /**
     * @Description:
     * 存储商品代码列表
     * @Time: 2021/7/26 14:40
     * @param productDetail
     * @param keyword
     * @Return: void
     */

    public  void cPutData(ProductDetail productDetail, String keyword){
        Append append = new Append(Bytes.toBytes(keyword));
        //去除多余字符串
        String newvalue= HBaseResult.trimFirstAndLastChar(HBaseResult.trimFirstAndLastChar(productDetail.getCodeList().toString(),"]"),"[");
        append.addColumn(Bytes.toBytes("2"), Bytes.toBytes("codelist"), Bytes.toBytes(newvalue));
        hBaseTemplate.appendRowData("jdgoodlist",append);
    }

    /**
     * @Description:
     * 分词进行倒排索引
     * @Time: 2021/7/28 10:58
     * @param key
     * @Return: java.util.List<com.example.test.gecco.JDGoodList>
     */

    public List<JDGoodList>  kGetData(String key) throws IOException {
        List<JDGoodList> jps = new ArrayList<>();
        String  goodlist = hBaseTemplate.keyRowData("jdgoodlist", key);
        String[] strArr = goodlist.split(", ");
        System.out.println(strArr.length);
        for (String s : strArr) {
            JDGoodList goodlist1 = hBaseTemplate.getOneData("jdgoodlist", s,key);
            if(ObjectUtils.isNotEmpty(goodlist1)) {
                jps.add(goodlist1);
            }
        }
        System.out.println(jps.size());
        return jps;

    }

    /**
     *返回指定页数的数据
     * @Time: 2021/7/30 14:55
     * @param key          搜索框中的关键字
     * @param pageNum      当前页数
     * @param pageSize     单页表长度
     * @Return: java.util.List<com.example.test.gecco.JDGoodList>
     */
    public List<JDGoodList>  keyGetData(String key,Integer pageNum,Integer pageSize) throws IOException {
        List<JDGoodList> jps = new ArrayList<>();
        String  goodlist = hBaseTemplate.keyRowData("jdgoodlist", key);
        String[] strArr = goodlist.split(", ");
        length=strArr.length;
        int oldto = (pageNum-1)*pageSize;
        int newto = pageNum*pageSize;
        if(length<newto){
            newto=length;
        }
        String[] newArr = Arrays.copyOfRange(strArr, oldto, newto);
        for (String s : newArr) {
            JDGoodList goodlist1 = hBaseTemplate.getOneData("jdgoodlist", s,key);
            goodlist1.setLength(length);
            if(ObjectUtils.isNotEmpty(goodlist1)) {
                jps.add(goodlist1);
            }
        }
        System.out.println(jps.size());
        return jps;
    }

}

