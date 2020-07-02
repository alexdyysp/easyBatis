# easyBatis
A small framework for connecting to sql and executing sql

## How to use
Step1: config 2 XML file<br>
XML file "congif.xml" is used to connect Mysql
```xml
<?xml version="1.0" encoding="UTF-8"?>

<database>
    <property name = "driverClassName">com.mysql.cj.jdbc.Driver</property>
    <property name = "url">jdbc:mysql://localhost:3306/testForBatis?useUnicode=true</property>
    <property name = "username">root</property>
    <property name = "password">123456</property>
</database>
```
XML file "UserMapper.xml" is used to sql
```xml
<?xml version="1.0" encoding="UTF-8"?>
<mapper nameSpace="com.Batis.mapper.UserMapper">
    <select id="getUserById" resultType="com.Batis.bean.User">
        select * from empuser where id = ?
    </select>
</mapper>
```

Step2: 
```java
// New an interface 
public interface UserMapper {
    public User getUserById(String id);
}

public static void TesteasyBatis(){
    // New Session
    SqlSession sqlSession = new SqlSession();
    // New Mapper and locate Class
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    // call method in Mapper Class
    User user = userMapper.getUserById("2");
    System.out.println("----- Get User DATA By easyBatis -----");
    System.out.println("Object: " + user.toString());
    System.out.println(user.getId() + " - " + user.getPassword() +  " - "  + user.getUsername());
}
```
And Then the result is like this
```cmd
Object: com.Batis.bean.User@6c3f5566
2 - asd - ben
``` 