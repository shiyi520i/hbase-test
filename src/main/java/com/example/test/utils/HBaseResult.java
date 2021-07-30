package com.example.test.utils;

import com.example.test.gecco.JDGoodList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

/**
 * @Author  cym
 * @Data 2021/7/19
 * @Version 1.0
 */
@Slf4j
public class HBaseResult {

    public static JDGoodList toHBaseBean(Result rs){
        List<Cell> cells = rs.listCells();
        JDGoodList jdGoodList=new JDGoodList();
        cells.forEach(cell -> {
          /* jdGoodList.setCode(Bytes.toString(CellUtil.cloneRow(cell)));
           jdGoodList.setTitle(Bytes.toString(CellUtil.cloneFamily(cell)));
           jdGoodList.setImage(Bytes.toString(CellUtil.cloneQualifier(cell)));
           jdGoodList.setDirector(cell.getTimestamp());
           jdGoodList.setType(cell.getType().toString());*/
           jdGoodList.setTitle(Bytes.toString(CellUtil.cloneValue(cell)));
        });
        return jdGoodList;
    }

    public static List<JDGoodList> toHBaseBeans(Result rs){
        List<JDGoodList>  hBaseBeans=new ArrayList<>();
        List<Cell> cells = rs.listCells();
        cells.forEach(cell -> {
            JDGoodList  hBaseBean=new JDGoodList();
          /*  hBaseBean.setRowKey(Bytes.toString(CellUtil.cloneRow(cell)));
            hBaseBean.setColumnFamily(Bytes.toString(CellUtil.cloneFamily(cell)));
            hBaseBean.setColumnQualifier(Bytes.toString(CellUtil.cloneQualifier(cell)));
            hBaseBean.setTimeStamp(cell.getTimestamp());
            hBaseBean.setType(cell.getType().toString());*/
            hBaseBean.setTitle(Bytes.toString(CellUtil.cloneValue(cell)));
            hBaseBeans.add(hBaseBean);
        });
        return hBaseBeans;
    }

    public static List<JDGoodList> toHBaseBeans(Iterator<Result> resultIterator){
        List<JDGoodList>  hBaseBeans=new ArrayList<>();
        while (resultIterator.hasNext()){
            Result rs = resultIterator.next();
            List<Cell> cells = rs.listCells();
            cells.forEach(cell -> {
                JDGoodList  hBaseBean=new JDGoodList();
           /*     hBaseBean.setRowKey(Bytes.toString(CellUtil.cloneRow(cell)));
                hBaseBean.setColumnFamily(Bytes.toString(CellUtil.cloneFamily(cell)));
                hBaseBean.setColumnQualifier(Bytes.toString(CellUtil.cloneQualifier(cell)));
                hBaseBean.setTimeStamp(cell.getTimestamp());
                hBaseBean.setType(cell.getType().toString());*/
                hBaseBean.setTitle(Bytes.toString(CellUtil.cloneValue(cell)));
                hBaseBeans.add(hBaseBean);
            });
        }
        return hBaseBeans;
    }

    /**
     * 获取单行结果
     * @return
     */
    public static  Map<String, String> getRowData(Result rs) {
        Map<String, String> column = new HashMap<>();
        column.put("rowKey", Bytes.toString(rs.getRow()));
        List<Cell> cells = rs.listCells();
        for (Cell cell : cells) {
            String columnName = Bytes.toString(CellUtil.cloneQualifier(cell));
            String columnValue =  Bytes.toString(CellUtil.cloneValue(cell));
            column.put(columnName, columnValue);
        }
        return column;
    }

    public static void setStartAndStop(String startRow, String stopRow, Scan scan) {
        if (!StringUtils.isEmpty(startRow)) {
            scan.withStartRow(Bytes.toBytes(startRow));
        }

        if (!StringUtils.isEmpty(stopRow)) {
            scan.withStopRow(Bytes.toBytes(stopRow));
        }
    }

    public static String trimFirstAndLastChar(String str, String element) {
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

}

