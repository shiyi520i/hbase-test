package com.example.test.server;

import com.example.test.conf.HbaseConfig;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;


@Service
public class PutData {
    @Autowired
    private HbaseConfig hbaseConfig;
    public void putData() throws IOException {
        //获取连接
        Connection connection = ConnectionFactory.createConnection(hbaseConfig.configuration());
    //   Admin admin = connection.getAdmin();
    //    System.out.println(admin.tableExists(TableName.valueOf("question")));
    //    admin.close();
        //获取表
        Table table = connection.getTable(TableName.valueOf("movie"));
        //新建一个为1000的rowkey
        Put put = new Put(Bytes.toBytes("1000"));
        //列族，列，值
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("titel"),Bytes.toBytes("千里千寻"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("director"),Bytes.toBytes("宫崎骏"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("writer"),Bytes.toBytes("宫崎骏"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("type"),Bytes.toBytes("剧情"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("number"),Bytes.toBytes("1872125"));
        put.addColumn(Bytes.toBytes("1"),Bytes.toBytes("score"),Bytes.toBytes("9.4"));
        //增加数据
        table.put(put);
        //关闭表连接
        table.close();
    }

    //get获取数据
   public void getData() throws IOException {
        //获取连接
       Connection connection = ConnectionFactory.createConnection(hbaseConfig.configuration());
       //获取表
       Table table = connection.getTable(TableName.valueOf("movie"));
       //new Get对象
       Get get = new Get(Bytes.toBytes("1000"));
       //获取指定的keyrow的数据
       Result result = table.get(get);
       //解析result
       for (Cell cell : result.rawCells()) {
           System.out.println("列族   "+Bytes.toString(CellUtil.cloneFamily(cell))+
                              ",列  "+Bytes.toString(CellUtil.cloneQualifier(cell))+
                              "，值   "+Bytes.toString(CellUtil.cloneValue(cell)));
       }
         table.close();
   }
   //扫描表
   public void scanData() throws IOException {
        //获取连接
       Connection connection = ConnectionFactory.createConnection(hbaseConfig.configuration());
       //获取表
       Table table = connection.getTable(TableName.valueOf("movie"));
       //获取scan,可使用过滤器
     /* Scan scan = new Scan();
       RegexStringComparator comp = new RegexStringComparator("you."); // 以 you 开头的字符串
       SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("family"), Bytes.toBytes("qualifier"), CompareOp.EQUAL, comp);
       scan.setFilter(filter);*/
       Scan scan = new Scan();
       //扫描全表
       ResultScanner scanner = table.getScanner(scan);
       //解析scanner
       for (Result result : scanner) {
           //解析result
           for (Cell cell : result.rawCells()) {
               System.out.println("列族   "+Bytes.toString(CellUtil.cloneFamily(cell))+
                       ",列  "+Bytes.toString(CellUtil.cloneQualifier(cell))+
                       "，值   "+Bytes.toString(CellUtil.cloneValue(cell)));
           }

       }
       //关闭表
     table.close();
   }
}
