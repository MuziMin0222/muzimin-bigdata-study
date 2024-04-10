package com.hypers.JUC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : 李煌民
 * @date : 2020-11-26 11:52
 **/
public class LoadDataToMysql {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        BufferedReader br = new BufferedReader(new FileReader("/home/cdp_etl/data/lhm/data/campaign_mapping.txt"));
        Connection conn = DriverManager.getConnection("jdbc:mysql://172.25.5.38:3306/test_dashboard", "cdp-dashboard", "2O3BN5XJHD0AOM1MA0SW!");

        String sql = "insert into " +
                "campaign_mapping(campaign_name,campaign_org_name,industry_org_name,product_org_name,industry_tag,product_tag,solution_tag,topic_tag,campaign_type,city,campaign_total_name,bg) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int sum = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split("\t");
            System.out.println(arr.length);
            for (int i = 0; i < arr.length; i++) {
                ps.setString(i + 1, arr[i]);
            }
            int count = ps.executeUpdate();
            sum += count;
        }

        System.out.println(sum);
        br.close();

        ps.close();
        conn.close();
    }
}
