package com.Batis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestByJdbc {
    public static void main(String[] args) throws Exception {
        // 1注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2获取连接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testForBatis", "root", "123456");
        // 3获得预处理对象
        String sql = "select * from empuser where id = ?";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1, "1".toString());
        // 4 SQL语句占位符设置实际参数
        // 5执行SQL语句
        ResultSet rs = stat.executeQuery();
        System.out.println(rs);
        // 6处理结果集(遍历结果集合)
        while (rs.next()) {
            //System.out.println(1);
            // 获取当前行的分类ID
            String sid = rs.getString(1);// 方法参数为数据库表中的列名
            // 获取当前行的分类名称
            String pwd = rs.getString(2);

            String sname = rs.getString(3);
            // 显示数据
            System.out.println(sid + "--" + pwd + "--" + sname );
        }
        // 7释放资源
        rs.close();
        stat.close();
        conn.close();

    }
}