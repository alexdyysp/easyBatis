package com.Batis.sqlSession;

import com.Batis.config.Function;
import com.Batis.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Configuration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /*
    read and deal with xml
     */
    public Connection build(String resource){
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            //int i;while((i = stream.read()) != -1){System.out.print((char) i);}System.out.println();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        }catch (Exception e){
            throw new RuntimeException("error in read xml " + resource);
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
            //System.out.println(name + " : " + value);
            if(name==null || value==null){
                throw new RuntimeException("[database]: error in <property>");
            }
            switch (name){
                case "url": url = value; break;
                case "username": username = value; break;
                case "password": password = value; break;
                case "driverClassName": driverClassName = value; break;
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

    @SuppressWarnings("rawtypes")
    public MapperBean readMapper(String path){
        MapperBean mapper = new MapperBean();
        try{
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<Function>();
            for(Iterator rootIter = root.elementIterator(); rootIter.hasNext();) {
                Function fun = new Function();
                Element e = (Element) rootIter.next();
                String sqlType = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqlType(sqlType);
                fun.setFuncName(funcName);
                Object newInstance = null;

                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException exception) {
                    exception.printStackTrace();
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException exception) {
                    exception.printStackTrace();
                }

                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }

            mapper.setList(list);

        }catch (DocumentException e){
            e.printStackTrace();
        }
        return mapper;
    }

    private String getValue(Element node) {
        return node.hasContent()?node.getText():node.attributeValue("value");
    }
}
