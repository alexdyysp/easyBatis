package com.Batis.sqlSession;

import com.Batis.config.Function;
import com.Batis.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;
    private Configuration configuration;

    public MapperProxy(Configuration configuration, SqlSession sqlSession){
        this.configuration = configuration;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = configuration.readMapper("UserMapper.xml");

        if(!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }

        List<Function> list = mapperBean.getList();

        if(list != null || list.size() != 0){
            for(Function function:list){
                if(method.getName().equals(function.getFuncName())){
                    return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }

        return null;
    }
}
