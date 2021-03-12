package com.lhm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @author : 李煌民
 * @date : 2021-01-22 18:00
 * 测试Hbase的admin对象
 **/
public class TestHbaseAdmin {
    public static void main(String[] args) throws IOException {
        //创建配置
        Configuration conf = HBaseConfiguration.create();

        //创建连接
        Connection conn = ConnectionFactory.createConnection(conf);

        //创建admin对象来管理Hbase，相当于Hmaster
        Admin admin = conn.getAdmin();

        new TestHbaseAdmin().createTb(admin);

        //关闭资源操作
        admin.close();
        conn.close();
    }

    public void deleteData(Connection conn) throws IOException {
        TableName tableName = TableName.valueOf("tb_demo");
        Table table = conn.getTable(tableName);

        Delete delete = new Delete(Bytes.toBytes("row_key"));

        table.delete(delete);

        table.close();
    }

    public void getData(Connection conn) throws IOException {
        TableName tableName = TableName.valueOf("tb_demo");
        Table table = conn.getTable(tableName);

        Get get = new Get(Bytes.toBytes("row_key"));

        Result result = table.get(get);

        //针对每一个单元格做操作
        List<Cell> cells = result.listCells();

        for (Cell cell : cells) {
            //拿到列族
            String familyArr = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            //拿到列名
            String columnName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            //拿到value
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
        }

        table.close();
    }

    public void putDataToTb(Connection conn) throws IOException {
        //将表名转为TableName对象
        TableName tableName = TableName.valueOf("tb_demo");
        //通过连接获取table对象
        Table table = conn.getTable(tableName);

        //获取put对象用于添加数据
        Put put = new Put(Bytes.toBytes("rowkey"));
        put.addColumn(Bytes.toBytes("colunmFamily"), Bytes.toBytes("colunmName"), Bytes.toBytes("value"));

        //表对象put封装的数据
        table.put(put);

        //关闭table对象，线程不安全且轻量级，conn是重量级且线程安全
        table.close();
    }

    //删除表操作
    public void dropTb(Admin admin) throws IOException {
        TableName tableName = TableName.valueOf("tb_demo");
        if (!admin.tableExists(tableName)) {
            return;
        }

        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    //创建表的操作
    public void createTb(Admin admin) throws IOException {
        //构建表名
        TableName table = TableName.valueOf("tb_demo");
        if (admin.tableExists(table)) {
            return;
        }

        //创建表描述器构建器
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(table);
        //创建列族描述器构建器，Hbase包下的Bytes工具类用于Java类型转字节数组，也可以将数组转为Java类型
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("c1"));
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder1 = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("c2"));
        //创建列族描述器
        ColumnFamilyDescriptor columnFamilyDescriptor = columnFamilyDescriptorBuilder.build();
        ColumnFamilyDescriptor columnFamilyDescriptor1 = columnFamilyDescriptorBuilder1.build();

        //建立表和列族之间的关系
        tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor);
        tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor1);

        //创建表描述器对象
        TableDescriptor tableDescriptor = tableDescriptorBuilder.build();

        //管理员来创建表
        admin.createTable(tableDescriptor);
    }
}
