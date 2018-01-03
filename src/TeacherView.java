import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by del on 2017/12/19.
 */
public class TeacherView extends Application {
    private final int WINDOW_WIDTH = 650;
    private final int WINDOW_HEIGHT = 500;

    private HBox hBox;
    private HBox btnHbox;
    private Label nameLabel;
    private Label stateLabel;
    private Label label1;
    private Label label2;
    private Button yesBtn;
    private Button noBtn;

    private TableView<Student> tableView;//已确定学生所在视图
    private TableView<Student> untableView;//未确定学生所在视图
    private static ObservableList<Student> obsList;//已确定学生列表
    private static ObservableList<Student> unobsList;//未确定学生列表

    private VBox vBox;

    private String t_id;
    private String sqlStr;
    private Student student;
    private Teacher teacher;

    @Override
    public void start(Stage primaryStage) throws Exception {

        bulidVBox();

        Scene scene = new Scene(vBox,WINDOW_WIDTH,WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("教师界面");
        primaryStage.show();
    }
    public void bulidTeacherStage(){
        bulidVBox();

        Stage teacherStage = new Stage();
        Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
        teacherStage.setScene(scene);
        teacherStage.setTitle("教师界面");
        teacherStage.show();

        initTV();
    }
    public void bulidVBox(){
        Label label_1 = new Label("姓名:");
        label_1.setStyle("-fx-font-size: 15");
        Label label_2 = new Label("状态:");
        label_2.setStyle("-fx-font-size: 15");
        nameLabel = new Label("姓名");
        nameLabel.setStyle("-fx-font-size: 15");
        stateLabel = new Label("状态");
        stateLabel.setStyle("-fx-font-size: 15");
        label1 = new Label("已确定");
        label1.setStyle("-fx-font-weight: bold");
        label2 = new Label("待确定");
        label2.setStyle("-fx-font-weight: bold");
        yesBtn = new Button("确定");
        noBtn = new Button("淘汰");

        //创建表视图
        tableView = new TableView<Student>();
        untableView = new TableView<Student>();
        //创建列对象
        TableColumn<Student,String> tColId = new TableColumn<Student, String>("学号");
        TableColumn<Student,String> tColName = new TableColumn<Student, String>("姓名");
        TableColumn<Student,String> tColSex = new TableColumn<Student, String>("性别");
        TableColumn<Student,String> tColMajor = new TableColumn<Student, String>("专业");
        TableColumn<Student,String> tColClas = new TableColumn<Student, String>("班级");
        TableColumn<Student,String> tColPhone = new TableColumn<Student, String>("联系电话");

        TableColumn<Student,String> tColId2 = new TableColumn<Student, String>("学号");
        TableColumn<Student,String> tColName2 = new TableColumn<Student, String>("姓名");
        TableColumn<Student,String> tColSex2 = new TableColumn<Student, String>("性别");
        TableColumn<Student,String> tColMajor2 = new TableColumn<Student, String>("专业");
        TableColumn<Student,String> tColClas2 = new TableColumn<Student, String>("班级");
        TableColumn<Student,String> tColPhone2 = new TableColumn<Student, String>("联系电话");
        //设置最小列宽
        tColId.setMinWidth(130);
        tColPhone.setMinWidth(130);
        tColMajor.setMinWidth(100);
        tColId2.setMinWidth(120);
        tColPhone2.setMinWidth(130);
        //把列对象添加到表视图
        tableView.getColumns().addAll(tColId,tColName,tColSex,tColClas,tColMajor,tColPhone);
        untableView.getColumns().addAll(tColId2,tColName2,tColSex2,tColClas2,tColMajor2,tColPhone2);
        //创建数据列表
        obsList = FXCollections.observableArrayList();
        unobsList = FXCollections.observableArrayList();
        //把数据列表和表视图关联
        tableView.setItems(obsList);
        untableView.setItems(unobsList);
        //把数据列表和列对象建立关联
        tColId.setCellValueFactory(new PropertyValueFactory<Student,String>("s_id"));
        tColName.setCellValueFactory(new PropertyValueFactory<Student,String>("s_name"));
        tColSex.setCellValueFactory(new PropertyValueFactory<Student,String>("s_sex"));
        tColMajor.setCellValueFactory(new PropertyValueFactory<Student,String>("s_major"));
        tColClas.setCellValueFactory(new PropertyValueFactory<Student,String>("s_clas"));
        tColPhone.setCellValueFactory(new PropertyValueFactory<Student,String>("s_phone"));

        tColId2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_id"));
        tColName2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_name"));
        tColSex2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_sex"));
        tColMajor2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_major"));
        tColClas2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_clas"));
        tColPhone2.setCellValueFactory(new PropertyValueFactory<Student,String>("s_phone"));

        //tableView监听事件
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                student = null;
            }
        });
        untableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                student = untableView.getSelectionModel().getSelectedItem();
                System.out.println(student.getS_name());
            }
        });

        //确定按钮点击事件
        yesBtn.setOnAction(e->{
            sayYes();
        });
        //淘汰按钮点击事件
        noBtn.setOnAction(e->{
            sayNo();
        });


        hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBox.getChildren().addAll(label_1,nameLabel,label_2,stateLabel);
        btnHbox = new HBox(10);
        btnHbox.setAlignment(Pos.CENTER_RIGHT);
        btnHbox.setPadding(new javafx.geometry.Insets(10,10,10,10));
        btnHbox.getChildren().addAll(yesBtn,noBtn);
        vBox = new VBox(10);
        vBox.setPadding(new javafx.geometry.Insets(5,10,5,10));
        vBox.getChildren().addAll(hBox,label1,tableView,label2,untableView,btnHbox);
    }

    //设置当前教师id
    public void setId(String s){
        t_id = s;
    }
    //初始化教师界面数据
    public void initTV(){
        getTeacher();
        getStudentList();
    }
    //得到当前教师信息
    public void getTeacher(){
        //连接数据库
        DatabaseControler controler = new DatabaseControler();
        controler.connect();
        sqlStr =  "select * from teacher where t_id =" + t_id;
        ResultSet rs = controler.queryInDB(sqlStr);
        try {
            while (rs.next()){
                String t_id = rs.getString(1);
                String t_name = rs.getString(2);
                String t_sex = rs.getString(3);
                String t_title = rs.getString(4);
                String t_major = rs.getString(5);
                String t_phone = rs.getString(6);
                String t_password = rs.getString(7);
                int t_count = rs.getInt(8);
                String t_isfull = rs.getString(9);

                teacher = new Teacher(t_id,t_name,t_sex,t_title,t_major,t_phone,t_password,t_count,t_isfull);
            }
        }catch (Exception e){
            System.out.println("出错：" + e.getMessage());
        }

        nameLabel.setText(teacher.getT_name());
        stateLabel.setText(teacher.getT_isfull());

        //关闭数据库连接
        controler.closeConnection();
    }
    //获取学生信息列表
    public void getStudentList(){
        obsList.clear();
        unobsList.clear();
        //连接数据库
        DatabaseControler controler = new DatabaseControler();
        controler.connect();

        //查找该导师已选定的学生
        sqlStr =  "select * from student where t_id="+ t_id + " and s_state="+ "'选定'";
        ResultSet rs = controler.queryInDB(sqlStr);

        try{
            //显示结果
            while(rs.next()){
                String s_id = rs.getString(1);
                String s_name = rs.getString(2);
                System.out.println(s_name);
                String s_sex = rs.getString(3);
                String s_major = rs.getString(4);
                String s_clas = rs.getString(5);
                String s_phone = rs.getString(6);
                String s_password = rs.getString(7);
                String s_state = rs.getString(8);
                String t_id = rs.getString(9);
                String t_name = rs.getString(10);

                obsList.add(new Student(s_id,s_name,s_sex,s_major,s_clas,s_phone,s_password,s_state,t_id,t_name));
            }

        }catch (SQLException e){
            System.out.println("查询出错：" + e.getMessage());
        }


        //查找该导师待选定的学生
       sqlStr =  "select * from student where t_id="+ t_id + " and s_state="+ "'待定'";
        ResultSet rs2 = controler.queryInDB(sqlStr);

        try{
            //显示结果
            while(rs2.next()){
                String s_id = rs2.getString(1);
                String s_name = rs2.getString(2);
                String s_sex = rs2.getString(3);
                String s_major = rs2.getString(4);
                String s_clas = rs2.getString(5);
                String s_phone = rs2.getString(6);
                String s_password = rs2.getString(7);
                String s_state = rs2.getString(8);
                String t_id = rs2.getString(9);
                String t_name = rs2.getString(10);

                unobsList.add(new Student(s_id,s_name,s_sex,s_major,s_clas,s_phone,s_password,s_state,t_id,t_name));
            }

        }catch (SQLException e){
            System.out.println("查询出错：" + e.getMessage());
        }
        //关闭数据库连接
        controler.closeConnection();
    }

    public void sayYes(){
        getTeacher();
        if(student.equals(null))
            return;
        if(teacher.getT_isfull().equals("已满额")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("名额已满，不可再选");
            alert.showAndWait();
            return;
        }
        //连接数据库
        DatabaseControler controler = new DatabaseControler();
        controler.connect();

        //更新学生状态为选定
        sqlStr =  "update student set s_state='选定'"+" where s_id="+student.getS_id() ;
        controler.updateInDB(sqlStr);

        getTeacher();
        getStudentList();

        //更新教师学生数
        int count = teacher.getT_count()+1;
        sqlStr =  "update teacher set t_count="+ count +" where t_id="+ t_id ;
        controler.updateInDB(sqlStr);
        System.out.println(teacher.getT_count());

        int count_num = 2;
        sqlStr =  "select * from count where count_id =1";
        ResultSet rs = controler.queryInDB(sqlStr);
        try {
            while (rs.next()){
                count_num = rs.getInt(2);
            }
        }catch (Exception e){
            System.out.println("出错：" + e.getMessage());
        }

        if (count >= count_num) {
            sqlStr = "update teacher set t_isfull='已满额'" + " where t_id=" + t_id;
            controler.updateInDB(sqlStr);
            stateLabel.setText("已满额");
        }

        //关闭数据库连接
        controler.closeConnection();


    }
    public void sayNo(){
        if(student.equals(null))
            return;

        //连接数据库
        DatabaseControler controler = new DatabaseControler();
        controler.connect();

        //更新学生状态为未选
        sqlStr =  "update student set t_id=null,t_name=null,s_state='未选'"+" where s_id="+student.getS_id() ;
        controler.updateInDB(sqlStr);

        getStudentList();

        //关闭数据库连接
        controler.closeConnection();


    }

}
