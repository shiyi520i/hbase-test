package com.example.test;

import com.example.test.utils.HBaseTemplate;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    HBaseTemplate hBaseTemplate;

    @Test
    void contextLoads() {
        System.out.println(hBaseTemplate);
        Put put = new Put(Bytes.toBytes("1000"));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("titel"),Bytes.toBytes("jdGoodList.getTitle())"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("price"),Bytes.toBytes("jdGoodList.getPrice().getPrice())"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("image"),Bytes.toBytes("jdGoodList.getImage())"));
/*        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("type"),Bytes.toBytes("剧情"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("number"),Bytes.toBytes("1872125"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("score"),Bytes.toBytes("9.4"));*/
        hBaseTemplate.putRowData("jdgoodlist",put);
        System.out.println(hBaseTemplate);
    }

}
