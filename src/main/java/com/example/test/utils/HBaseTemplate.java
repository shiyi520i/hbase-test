package com.example.test.utils;


import com.example.test.gecco.JDGoodList;
import com.example.test.gecco.JDPrice;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Hbase 模板类，对hbase 的 DDL、DML操作
 *
 * @Author cym
 * @Data 2021/7/4
 * @Version 1.0
 */
@Slf4j
public class HBaseTemplate {

    /**
     * hbase连接对象
     */
    private Connection connection;

    public HBaseTemplate() {
    }

    public HBaseTemplate(Connection connection) {
        setConnection(connection);
    }

    private Connection getConnection() {
        return connection;
    }

    private Admin getAdmin() throws IOException {
        return connection.getAdmin();
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 获取 {@link Table}
     *
     * @param tableName 表名称
     * @return
     * @throws IOException
     */
    public Table getTable(String tableName) throws IOException {
        return connection.getTable(TableName.valueOf(tableName));
    }

    /**
     * @Description:
     * 模糊搜索
     * @Time: 2021/7/26 15:19
     * @param key
     * @Return: java.util.List<ProductDetail>
     */
    public  List<JDGoodList> scanDataByKey(String key) throws IOException {
        //电影列表
        List<JDGoodList> jps = new ArrayList<>();
        //获取表
        Table table = connection.getTable(TableName.valueOf("movie"));
        //获取scan,可使用过滤器
        Scan scan = new Scan();
        String regex = ".*"+key+".*";
        RegexStringComparator comp = new RegexStringComparator(regex);
        List<Filter> listFilter = new ArrayList<Filter>();
        SingleColumnValueFilter filter0 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("title"), CompareFilter.CompareOp.EQUAL, comp);
        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("director"), CompareFilter.CompareOp.EQUAL, comp);
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("score"), CompareFilter.CompareOp.EQUAL, comp);
        SingleColumnValueFilter filter3 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("type"), CompareFilter.CompareOp.EQUAL, comp);
        SingleColumnValueFilter filter4 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("writer"), CompareFilter.CompareOp.EQUAL, comp);
        SingleColumnValueFilter filter5 = new SingleColumnValueFilter(Bytes.toBytes("1"), Bytes.toBytes("number"), CompareFilter.CompareOp.EQUAL, comp);
        //filter.setFilterIfMissing(true);如果为true，当这一列不存在时，不会返回，如果为false，当这一列不存在时，会返回所有的列信息。
        listFilter.add(filter0);
        listFilter.add(filter1);
        listFilter.add(filter2);
        listFilter.add(filter3);
        listFilter.add(filter4);
        listFilter.add(filter5);
        //“AND”还是“OR”通过``FilterList.Operator.MUST_PASS_ONE来指定，如果是MUST_PASS_ONR，则是“OR”的关系，如果是MUST_PASS_ALL`，则是“AND”的关系，如果不传入这个参数，则默认为“AND”的关系。
        Filter filter = new FilterList(FilterList.Operator.MUST_PASS_ONE, listFilter);
        scan.setFilter(filter);
        //扫描全表
        ResultScanner scanner = table.getScanner(scan);
        //解析scanner
        for (Result result : scanner) {
            //电影实体
            JDGoodList jdGoodList = new JDGoodList();
            JDPrice jp=new JDPrice();
            jdGoodList.setCode(Bytes.toString(CellUtil.cloneValue(result.rawCells()[0])));
            jdGoodList.setTitle(Bytes.toString(CellUtil.cloneValue(result.rawCells()[1])));
            jp.setPrice(Bytes.toString(CellUtil.cloneValue(result.rawCells()[2])));
            jdGoodList.setPrice(jp);
            jdGoodList.setImage(Bytes.toString(CellUtil.cloneValue(result.rawCells()[3])));
            jdGoodList.setGoodurl("https://item.jd.com/"+Bytes.toString(CellUtil.cloneValue(result.rawCells()[0]))+".html");
            jps.add(jdGoodList);

        }


        return jps;
    }


    /**
     * @Description:
     * 获取一条数据
     * @Time: 2021/7/26 11:39
     * @param tab
     * @param rowKey
     * @Return: com.example.test.gecco.JDGoodList
     */

    public  JDGoodList getOneData(String tab,String rowKey) throws IOException {
        JDGoodList jdGoodList = new JDGoodList();
        //获取表
        Table table = connection.getTable(TableName.valueOf(tab));
        //设置需要获取的rowkey
        Get get = new Get(Bytes.toBytes(rowKey));
        //获取一行数据
        Result result = table.get(get);
        JDPrice jp=new JDPrice();
        jdGoodList.setCode(Bytes.toString(CellUtil.cloneValue(result.rawCells()[0])));
        jdGoodList.setTitle(Bytes.toString(CellUtil.cloneValue(result.rawCells()[1])));
        jp.setPrice(Bytes.toString(CellUtil.cloneValue(result.rawCells()[2])));
        jdGoodList.setPrice(jp);
        jdGoodList.setImage(Bytes.toString(CellUtil.cloneValue(result.rawCells()[3])));
        jdGoodList.setGoodurl("https://item.jd.com/"+Bytes.toString(CellUtil.cloneValue(result.rawCells()[0]))+".html");

        return jdGoodList;
    }

    /**
     * @Description:
     * 扫描表获取所有数据
     * @Time: 2021/7/26 11:40
     * @param tab 表名字
     * @Return: java.util.List<com.example.test.gecco.JDGoodList>
     */

    public  List<JDGoodList> scanData(String tab) throws IOException {
        //商品列表
        List<JDGoodList> jps = new ArrayList<>();
        //获取表
        Table table = connection.getTable(TableName.valueOf(tab));
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
            //获得实体
            JDGoodList jdGoodList = new JDGoodList();
            JDPrice jp=new JDPrice();
            jdGoodList.setCode(Bytes.toString(CellUtil.cloneValue(result.rawCells()[0])));
            jdGoodList.setTitle(Bytes.toString(CellUtil.cloneValue(result.rawCells()[3])));
            jp.setPrice(Bytes.toString(CellUtil.cloneValue(result.rawCells()[2])));
            jdGoodList.setPrice(jp);
            jdGoodList.setImage(Bytes.toString(CellUtil.cloneValue(result.rawCells()[1])));
            jdGoodList.setGoodurl("https://item.jd.com/"+Bytes.toString(CellUtil.cloneValue(result.rawCells()[0]))+".html");
            jps.add(jdGoodList);

        }
        return jps;
    }

    /**
     * 创建命名空间
     *
     * @param nameSpace 命名空间名称
     */
    public void createNameSpace(String nameSpace) {
        Assert.hasLength(nameSpace, "命名空间不能为空");
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
        try (Admin admin = getAdmin()) {
            admin.createNamespace(namespaceDescriptor);
        } catch (IOException e) {
            //   log.error("创建命名空间 [{}] 失败", nameSpace, e);
        }
    }

    /**
     * 删除命名空间
     *
     * @param nameSpace 命名空间名称
     */
    public void deleteNameSpace(String nameSpace) {
        Assert.hasLength(nameSpace, "命名空间不能为空");
        try (Admin admin = getAdmin()) {
            admin.deleteNamespace(nameSpace);
        } catch (IOException e) {
            //    log.error("删除命名空间 [{}] 失败", nameSpace, e);
        }
    }


    /**
     * 创建表
     *
     * @param tableName 表名称
     * @param CF        表中的列族
     * @return
     */
    public void createTable(String tableName, String... CF) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.notEmpty(CF, "列族不能为空");
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));

        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        for (String columnFamily : CF) {
            ColumnFamilyDescriptor build = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily)).build();
            columnFamilyDescriptors.add(build);
        }
        TableDescriptor tableDescriptor = tableDescriptorBuilder.setColumnFamilies(columnFamilyDescriptors).build();
        try (Admin admin = getAdmin()) {
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            //   log.error("创建 table => {} 失败", tableName, e);
        }
    }

    /**
     * 禁用表
     *
     * @param tableName 表名称
     * @return
     */

    public void disableTable(String tableName) {
        Assert.hasLength(tableName, "表名不能为空");
        try (Admin admin = getAdmin()) {
            admin.disableTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            //  log.error("禁用 table => {} 失败", tableName, e);
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名称
     * @return
     */
    public void deleteTable(String tableName) {
        Assert.hasLength(tableName, "表名不能为空");
        try (Admin admin = getAdmin()) {
            //禁用表之后才能删除表
            disableTable(tableName);
            //删除表
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            //   log.error("删除 table => {} 失败", tableName, e);
        }
    }


    /**
     * 列出指定命名空间下的所有表
     *
     * @param nameSpace 命名空间名称
     * @return {@link TableName}
     */
    public List<TableName> listTable(String nameSpace) {
        Assert.hasLength(nameSpace, "命名空间不能为空");
        try (Admin admin = getAdmin()) {
            TableName[] tableNames = admin.listTableNamesByNamespace(nameSpace);
            List<TableName> tableNameList = (List<TableName>) CollectionUtils.arrayToList(tableNames);
            return tableNameList;
        } catch (IOException e) {
            //   log.error("获取命名空间 [{}] 下的所有表失败", nameSpace, e);
        }
        return null;
    }

    /**
     * 列出 default 命名空间下的所有表
     *
     * @return {@link TableName}
     */
    public List<TableName> listTableByDefault() {
        return listTable("default");
    }

    /**
     * 扫描表
     *
     * @param tableName 表名
     * @return
     */
    public List<Map<String, String>> scanTable(String tableName) {
        Assert.hasLength(tableName, "表名不能为空");
        Scan scan = new Scan();
        return getResult(tableName, scan);
    }


    /**
     * 向表中添加数据
     *
     * @param tableName
     * @param puts
     */
    public void putRowData(String tableName, Put... puts) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.notEmpty(puts, "添加数据不能为空");
        try (Table table = getTable(tableName)) {
            List<Put> putList = (List<Put>) CollectionUtils.arrayToList(puts);
            table.put(putList);
        } catch (IOException e) {
            //  log.error("添加数据失败");
        }
    }

    /**
     * 向表中添加数据
     *
     * @param tableName
     * @param puts
     */
    public void putRowData(String tableName, List<Put> puts) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.notEmpty(puts, "添加数据不能为空");
        try (Table table = getTable(tableName)) {
            table.put(puts);
        } catch (IOException e) {
            //   log.error("添加数据失败");
        }
    }

    /**
     * 删除数据
     *
     * @param tableName
     * @param rowKeys
     */
    public void deleteRowData(String tableName, String... rowKeys) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.notEmpty(rowKeys, "rowKey 列表不能为空");
        try (Table table = getTable(tableName)) {
            List<Delete> deleteList = new ArrayList<>(rowKeys.length);
            for (String rowKey : rowKeys) {
                Delete delete = new Delete(Bytes.toBytes(rowKey));
                deleteList.add(delete);
            }
            table.delete(deleteList);
        } catch (IOException e) {
            //   log.error("删除数据失败, rowKey => [{}]", rowKeys, e);
        }
    }


    /**
     * 获取单行数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return
     */
    public Map<String, String> getRowData(String tableName, String rowKey) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.hasLength(rowKey, "rowKey 不能为空");
        Get get = new Get(Bytes.toBytes(rowKey));
        Map<String, String> rowData = null;
        try (Table table = getTable(tableName);) {
            Result result = table.get(get);
            rowData = HBaseResult.getRowData(result);
        } catch (IOException e) {
            //   log.error("查询 rowKey 为 [{}] 的数据时出错", rowKey, e);
        }
        return rowData;
    }

    /**
     * @Description:
     * 获取某一列族：列的数据
     * @Author: cym
     * @Time: 2021/7/26 11:23
     * @Param: [tableName, rowKey]
     * @Return: java.lang.String
     */
    public String keyRowData(String tableName, String rowKey) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.hasLength(rowKey, "rowKey 不能为空");
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes("1"), Bytes.toBytes("codelist"));
        try (Table table = getTable(tableName);) {
            Result result = table.get(get);
            for (Cell cell : result.rawCells()) {
                    return Bytes.toString(CellUtil.cloneValue(cell));
            }
        } catch (IOException e) {
            //   log.error("查询 rowKey 为 [{}] 的数据时出错", rowKey, e);
        }
        return "null";
    }


    /**
     * 获取单行数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return
     */
    public JDGoodList getRowData_1(String tableName, String rowKey) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.hasLength(rowKey, "rowKey 不能为空");
        JDGoodList hBaseBean = null;
        Get get = new Get(Bytes.toBytes(rowKey));
        Map<String, String> rowData = null;
        try (Table table = getTable(tableName);) {
            Result result = table.get(get);
            hBaseBean = HBaseResult.toHBaseBean(result);
        } catch (IOException e) {
            //  log.error("查询 rowKey 为 [{}] 的数据时出错", rowKey, e);
        }
        return hBaseBean;
    }


    /**
     * 获取某个Rowkey 的一个列族所有的数据
     * @param tableName 表名称
     * @param rowKey rowkey
     * @param columnFamily 列族
     * @return
     */
    public JDGoodList getRowAndFamily(String tableName, String rowKey,String columnFamily) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.hasLength(rowKey, "rowKey 不能为空");
        JDGoodList hBaseBean = null;
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(Bytes.toBytes(columnFamily));
        Map<String, String> rowData = null;
        try (Table table = getTable(tableName);) {
            Result result = table.get(get);
            hBaseBean = HBaseResult.toHBaseBean(result);
        } catch (IOException e) {
            //    log.error("查询 rowKey 为 [{}] 的数据时出错", rowKey, e);
        }
        return hBaseBean;
    }

    /**
     *  rowKey 范围查找
     *
     * @param tableName
     * @param startRowKey
     * @param stopRowKey
     * @return
     */
    public List<JDGoodList> scanStartAndStopRow(String tableName, String startRowKey, String stopRowKey) {
        Assert.hasLength(tableName, "表名不能为空");
        Assert.hasLength(startRowKey, "startRowKey 不能为空");
        Assert.hasLength(stopRowKey, "startRowKey 不能为空");
        List<JDGoodList> hBaseBeans = null;
        try (Table table = getTable(tableName)) {
            Scan scan = new Scan();
            scan.withStartRow(Bytes.toBytes(startRowKey));
            scan.withStopRow(Bytes.toBytes(stopRowKey));
            ResultScanner scanner = table.getScanner(scan);
            hBaseBeans =HBaseResult.toHBaseBeans( scanner.iterator());
        } catch (IOException e) {
            //  log.error("查询表数据出错", e);
        }
        return hBaseBeans;
    }


    private List<Map<String, String>> getResult(String tableName, Scan scan) {
        List<Map<String, String>> result = null;
        try (Table table = getTable(tableName)) {
            ResultScanner scanner = table.getScanner(scan);
            for (Result rs : scanner) {
                Map<String, String> column = HBaseResult.getRowData(rs);
                result.add(column);
            }
        } catch (IOException e) {
            //  log.error("查询表数据出错", e);
        }
        return result;
    }


}

