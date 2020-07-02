package com.Batis;

import com.Batis.bean.User;
import com.Batis.mapper.UserMapper;
import com.Batis.sqlSession.SqlSession;

public class TesteasyBatis {

    public static void main(String[] args){
        SqlSession sqlSession = new SqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById("2");
        System.out.println("----- Get User DATA By easyBatis -----");
        System.out.println("Object: " + user.toString());
        System.out.println(user.getId() + " - " + user.getPassword() +  " - "  + user.getUsername());
    }

}
