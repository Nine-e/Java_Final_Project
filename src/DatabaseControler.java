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

    private ResultSet rs;//��Ų�ѯ����ļ�¼��
    private static String sqlStr ;

    public void connect(){
        try{
            //����ע����Ӧ����
            Class.forName(driverName);
            //��������
            con = DriverManager.getConnection(url,user,password);
            //��ʾ���
            System.out.println("success");
        }catch (ClassNotFoundException e){
            System.out.println("������������" + e.getMessage());
        }catch (SQLException e) {
            System.out.println("�������ݿ����" + e.getMessage());
        }
    }

    public ResultSet queryInDB(String sql){
        try{
            //����statement����
            sta = con.createStatement();
            //ִ�в�ѯ����
            rs = sta.executeQuery(sql);
        }catch (SQLException e){
            System.out.println("��ѯ����" + e.getMessage());
        }
        return  rs;
    }

    public void updateInDB(String sql){
        try{
            //����statement����
            sta = con.createStatement();
            //ִ�в�ѯ����
            sta.execute(sql);
        }catch (SQLException e){
            System.out.println("���³���" + e.getMessage());
        }
    }

    public void closeConnection() {
        //������Ӳ�Ϊ�գ��ر����Ӷ���
        if (con != null) {
            try {
                //���������Ϊ�գ��ر�������
                if (sta != null){
                    //����������Ϊ�գ��رս����
                    if(rs != null){
                        rs.close();
                    }
                    sta.close();
                }
                con.close();
                System.out.println("���ݿ����ӹر�");
            } catch (SQLException e) {
                System.out.println("�ر����ݿ����" + e.getMessage());
            }
        }
    }
}
