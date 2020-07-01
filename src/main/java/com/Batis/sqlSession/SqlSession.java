package com.Batis.sqlSession;


import java.lang.reflect.Proxy;

public class SqlSession {

    private Excutor executor = new ExcutorInstance();

    private Configuration configuration = new Configuration();

    public <T> T selectOne(String statement, Object parameter){
        return executor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> claz){
        return (T) Proxy.newProxyInstance(claz.getClassLoader(), new Class[]{claz},
                new MapperProxy(configuration, this)
                );
    }

}
