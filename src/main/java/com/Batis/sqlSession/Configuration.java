package com.Batis.sqlSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Configuration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /*
    read and deal with xml
     */
    public Connection build(String resource){
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        }catch (Exception e){
            throw  new RuntimeException("error in read xml " + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if(!node.getName().equals("database")){
            throw new RuntimeException("RuntimeException in <database>");
        }

        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        for(Object item:node.elements("property")){
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            if(name==null || value==null){
                throw new RuntimeException("[database]: error in <property>");
            }
            switch (name){
                case "url": url = value; break;
                case "username": username = value; break;
                case "password": username = value; break;
                case "driverClassName": username = value; break;
                default: throw new RuntimeException("[database]: unkown <property>");
            }
        }

        Class.forName(driverClassName);
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return connection;
    }



    private String getValue(Element node) {
        return node.hasContent()?node.getText():node.attributeValue("value");
    }
}
