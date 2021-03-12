package com.lhm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : 李煌民
 * @date : 2021-02-03 18:50
 **/
public class ScanFilterDemo_2 {
    public static void main(String[] args) throws IOException {
        //创建配置
        Configuration conf = HBaseConfiguration.create();

        //创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        TableName tableName = TableName.valueOf("huawei:id_mapping");
        Table table = conn.getTable(tableName);

        Scan scan = new Scan();

        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("C"), Bytes.toBytes("mobile_number"), CompareOperator.EQUAL, Bytes.toBytes("18779700731"));

        ArrayList<Filter> list = new ArrayList<>();
        list.add(filter1);

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, list);
        scan.setFilter(filterList);

        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result res = iterator.next();
            String rowkey = Bytes.toString(res.getRow());

            List<Cell> cells = res.listCells();
            for (Cell cell : cells) {
                String columnName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

                System.out.println(rowkey + "---" + columnName + ":" + value);
            }
        }
    }
}
