package com.example.test.server;

import com.example.test.gecco.JDGoodList;
import com.example.test.utils.ApplicationContextHelperUtil;
import com.example.test.utils.HBaseTemplate;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;




public class OptData {

     //引入hbaseTemplate模板
     private static HBaseTemplate hBaseTemplate =(HBaseTemplate) ApplicationContextHelperUtil.getBean(HBaseTemplate.class);

    public  void tPutData(JDGoodList jdGoodList){
        //rowkey
        Put put = new Put(Bytes.toBytes(jdGoodList.getCode()));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getCode()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getTitle()));
    /*    put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getComment().getNumber()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("titel"), Bytes.toBytes(jdGoodList.getComment().getGoodrate()));*/
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("price"), Bytes.toBytes(jdGoodList.getPrice().getPrice()));
        put.addColumn(Bytes.toBytes("1"), Bytes.toBytes("image"), Bytes.toBytes(jdGoodList.getImage()));
        hBaseTemplate.putRowData("jdgoodlist",put);

    }

}

