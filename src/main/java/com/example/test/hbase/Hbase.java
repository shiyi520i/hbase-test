package com.example.test.hbase;


import com.example.test.gecco.JDGoodList;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 创建时间 2021/7/2 21:47
 * hbase工具类
 * @author Ye
 * @version 1.0
 */
/*public class Hbase {
    public static void putData(JDGoodList jdGoodList) throws IOException {
        //获取连接
        Connection connection = ConnectionFactory.createConnection(HbaseConfig1.configuration());
        //   Admin admin = connection.getAdmin();
        //    System.out.println(admin.tableExists(TableName.valueOf("question")));
        //    admin.close();
        //获取表
        Table table = connection.getTable(TableName.valueOf("jdgoodlist"));
        //新建一个为1000的rowkey
        Put put = new Put(Bytes.toBytes(jdGoodList.getCode()));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("url"),Bytes.toBytes(jdGoodList.getImage()));

        table.put(put);
        //关闭表连接
        table.close();
    }


    }*/


