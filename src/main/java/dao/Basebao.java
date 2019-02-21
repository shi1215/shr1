package dao;

import java.io.InputStream;

import java.sql.*;
import java.util.Properties;

/**
 * Created by SHR on 2018-12-11.
 */
public class Basebao {
    private static  String piv="";
    private  static  String url="";//连接
    private static String user="";//用户密码
    private static String pwd="";//密码
    public Connection conn=null;
    public PreparedStatement pstam=null;
    public ResultSet re=null;
    static {//驱动
        try {
            Properties properties=new Properties();
            InputStream is= Basebao.class.getClassLoader().getResourceAsStream("databases.properties");
            properties.load(is);
            piv=properties.getProperty("pive");
            url=properties.getProperty("url");
            user=properties.getProperty("user");
            pwd=properties.getProperty("pwd");
            Class.forName(piv);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  Connection lainjie(){//连接
        //if (conn==null){
            try {
                conn= DriverManager.getConnection(url,user,pwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //}
        return conn;
    }
    public  void close1(){ //关闭
        try {
            if (conn!=null){
                conn.close();
            }
            if (pstam!=null){
                pstam.cancel();
            }
            if (re!=null){
                re.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //修改
    public int exceuteUpdate(String sql,Object[] param){
        int num=0;
        conn=lainjie();//得到连接
        try {
            pstam=conn.prepareStatement(sql);//创建命令行对象
            if (param!=null){
                for (int i=0;i<param.length;i++){
                    pstam.setObject(i+1,param[i]);
                }
            }
            num=pstam.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close1();//关闭
        }
        return num;
    }

    public static void main(String[] args) {
        Basebao b=new Basebao();
        System.out.println(b.conn);

    }

}
