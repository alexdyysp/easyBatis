package com.Batis.sqlSession;


import com.Batis.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExcutorInstance implements Excutor {

    private Configuration configuration = new Configuration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try{
            pre = connection.prepareStatement(sql);
            pre.setString(1, parameter.toString());
            System.out.println(pre);
            set = pre.executeQuery();
            System.out.println(set.next());
            User user = new User();
            while(set.next()){
                System.out.println(1);
                user.setId(set.getString(1));
                user.setPassword(set.getString(2));
                user.setUsername(set.getString(3));
            }

            return (T) user;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(set!=null){
                    set.close();
                }
                if(pre!=null){
                    pre.close();
                }
                if(connection!=null){
                    connection.close();
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        return null;
    }

    public Connection getConnection(){
        try{
            Connection connection = configuration.build("config.xml");
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
