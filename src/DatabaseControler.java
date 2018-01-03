import java.sql.*;

/**
 * Created by del on 2017/12/21.
 */
public class DatabaseControler {
    public Connection con;
    public Statement sta;
    private String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/java_project";
    private String user = "root";
    private String password = "";

    private ResultSet rs;//存放查询结果的记录集
    private static String sqlStr ;

    public void connect(){
        try{
            //加载注册相应驱动
            Class.forName(driverName);
            //创建连接
            con = DriverManager.getConnection(url,user,password);
            //显示结果
            System.out.println("success");
        }catch (ClassNotFoundException e){
            System.out.println("加载驱动出错：" + e.getMessage());
        }catch (SQLException e) {
            System.out.println("连接数据库出错：" + e.getMessage());
        }
    }

    public ResultSet queryInDB(String sql){
        try{
            //创建statement对象
            sta = con.createStatement();
            //执行查询操作
            rs = sta.executeQuery(sql);
        }catch (SQLException e){
            System.out.println("查询出错：" + e.getMessage());
        }
        return  rs;
    }

    public void updateInDB(String sql){
        try{
            //创建statement对象
            sta = con.createStatement();
            //执行查询操作
            sta.execute(sql);
        }catch (SQLException e){
            System.out.println("更新出错：" + e.getMessage());
        }
    }

    public void closeConnection() {
        //如果连接不为空，关闭连接对象
        if (con != null) {
            try {
                //如果语句对象不为空，关闭语句对象
                if (sta != null){
                    //如果结果集不为空，关闭结果集
                    if(rs != null){
                        rs.close();
                    }
                    sta.close();
                }
                con.close();
                System.out.println("数据库连接关闭");
            } catch (SQLException e) {
                System.out.println("关闭数据库出错：" + e.getMessage());
            }
        }
    }
}
