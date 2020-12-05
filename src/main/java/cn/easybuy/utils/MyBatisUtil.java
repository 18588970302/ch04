package cn.easybuy.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory ssf;
    static{
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            ssf = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession createSqlSession(){
        return ssf.openSession(false);
    }
    public static void closeSqlSession(SqlSession ss){
        if(ss != null){
            ss.close();
        }
    }
}
