package com.example.test.server;

import com.example.test.gecco.JDGoodList;
import com.example.test.gecco.ProductDetail;
import com.example.test.utils.ApplicationContextHelperUtil;
import com.example.test.utils.HBaseTemplate;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptData {

     //引入hbaseTemplate模板
     private static HBaseTemplate hBaseTemplate =(HBaseTemplate) ApplicationContextHelperUtil.getBean(HBaseTemplate.class);

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
        hBaseTemplate.putRowData("jdgoodlist",put);

    }

    /**
     * @Description:
     * @Time: 2021/7/26 14:40
     * @param productDetail
     * @param keyword
     * @Return: void
     */

    public  void cPutData(ProductDetail productDetail,String keyword){
        //rowkey
        Put put = new Put(Bytes.toBytes(keyword));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("codelist"), Bytes.toBytes(productDetail.getCodeList().toString()));
        hBaseTemplate.putRowData("jdgoodlist",put);

    }

    public List<JDGoodList>  kGetData(String key) throws IOException {
        List<JDGoodList> jps = new ArrayList<>();
        String  goodlist = hBaseTemplate.keyRowData("jdgoodlist", key);
        String[] strArr = goodlist.split(" ");
        for (String s : strArr) {
            JDGoodList goodlist1 = hBaseTemplate.getOneData("jdgoodlist", s);
            jps.add(goodlist1);
        }
        return jps;

    }

}

