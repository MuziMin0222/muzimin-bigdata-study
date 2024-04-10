package com.lhm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @author : 李煌民
 * @date : 2021-01-25 11:14
 **/
public class ScanFilterDemo {
    public static void main(String[] args) throws IOException {
        //创建配置
        Configuration conf = HBaseConfiguration.create();

        //创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        TableName tableName = TableName.valueOf("tb_demo");
        Table table = conn.getTable(tableName);

        //取columnName中日期在6月份的所有数据
        Scan scan = new Scan();
        SingleColumnValueFilter startFilter = new SingleColumnValueFilter(
                Bytes.toBytes("familyColumn"),
                Bytes.toBytes("columnName"),
                CompareOperator.GREATER_OR_EQUAL,
                Bytes.toBytes("2020-06-01"));

        SingleColumnValueFilter endFilter = new SingleColumnValueFilter(
                Bytes.toBytes("familyColumn"),
                Bytes.toBytes("columnName"),
                CompareOperator.GREATER_OR_EQUAL,
                Bytes.toBytes("2020-06-30"));

        //将过滤器连接起来
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, startFilter, endFilter);

        scan.setFilter(filterList);

        ResultScanner scanRes = table.getScanner(scan);
        for (Result result : scanRes) {
            String rowKey = Bytes.toString(result.getRow());

            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String familyColumn = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                String columnName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            }
        }

        scanRes.close();
        table.close();
    }
}
