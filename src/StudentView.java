import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by del on 2017/12/19.
 */
public class StudentView extends Application {
    private final int WINDOW_WIDTH = 650;
    private final int WINDOW_HEIGHT = 400;

    private HBox hBox1;
    private HBox hBox2;
    private Label nameLabel;
    private Label stateLabel;
    private javafx.scene.control.Button chooseBtn;

    private TableView<Teacher> tableView;
    private static ObservableList<Teacher> obsList;

    private VBox vBox;
   // private Stage studentStage;
    private String s_id;
    private String sqlStr;
    private Student student;
    private int index;
    private Teacher teacher;

    @Override
    public void start(Stage primaryStage) throws Exception {

        bulidVBox();

        Scene scene = new Scene(vBox,WINDOW_WIDTH,WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("学生界面");
        primaryStage.show();
    }
   public void bulidStudentStage() {
       bulidVBox();
       Stage studentStage = new Stage();
       Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
       studentStage.setScene(scene);
       studentStage.setTitle("学生界面");
       studentStage.show();

       initSV();
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

       chooseBtn = new javafx.scene.control.Button("选择该导师");
       // chooseBtn.setPreferredSize();

       //创建表视图
       tableView = new TableView<Teacher>();
       //创建列对象
       TableColumn<Teacher,String> tColId = new TableColumn<Teacher, String>("工号");
       TableColumn<Teacher,String> tColName = new TableColumn<Teacher, String>("姓名");
       TableColumn<Teacher,String> tColSex = new TableColumn<Teacher, String>("性别");
       TableColumn<Teacher,String> tColTitle = new TableColumn<Teacher, String>("职称");
       TableColumn<Teacher,String> tColMajor = new TableColumn<Teacher, String>("研究方向");
       TableColumn<Teacher,String> tColPhone = new TableColumn<Teacher, String>("联系电话");
       TableColumn<Teacher,String> tColState = new TableColumn<Teacher, String>("状态");
       //设置最小列宽
       tColId.setMinWidth(100);
       tColPhone.setMinWidth(130);
       //把列对象添加到表视图
       tableView.getColumns().addAll(tColId,tColName,tColSex,tColTitle,tColMajor,tColPhone,tColState);
       //创建数据列表
       obsList = FXCollections.observableArrayList();
       //把数据列表和表视图关联
       tableView.setItems(obsList);
       //把数据列表和列对象建立关联
       tColId.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_id"));
       tColName.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_name"));
       tColSex.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_sex"));
       tColTitle.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_title"));
       tColMajor.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_major"));
       tColPhone.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_phone"));
       tColState.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_isfull"));
       //tableView监听事件
       tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               index = tableView.getSelectionModel().getSelectedIndex();
               teacher = tableView.getSelectionModel().getSelectedItem();
               System.out.println(index);
               System.out.println(teacher.getT_id());
               System.out.println(teacher.getT_name());
           }
       });

       //选定按钮点击事件
       chooseBtn.setOnAction(e->{
           choose();
       });

       hBox1 = new HBox(10);
       hBox1.setAlignment(Pos.CENTER_LEFT);
       hBox1.setPadding(new javafx.geometry.Insets(10,10,10,10));
       hBox1.getChildren().addAll(label_1,nameLabel,label_2,stateLabel);

       hBox2 = new HBox();
       hBox2.setAlignment(Pos.CENTER_RIGHT);
       hBox2.setPadding(new javafx.geometry.Insets(10,10,10,10));
       hBox2.getChildren().add(chooseBtn);

       vBox = new VBox();
       vBox.setPadding(new javafx.geometry.Insets(5,10,5,10));
       vBox.getChildren().addAll(hBox1,tableView);
       vBox.getChildren().add(hBox2);
   }

   //初始化StudentView数据
   public void initSV(){
       getStudent();
       getTeachers();
   }

   //设置当前学生Id
   public void setId(String s){
       s_id = s;
       //System.out.println(s_id);
   }
   //获取当前学生信息
   public void getStudent(){
       //System.out.println(s_id);
       //连接数据库
       DatabaseControler controler = new DatabaseControler();
       controler.connect();
       sqlStr =  "select * from student where s_id ="+ s_id;
       ResultSet rs = controler.queryInDB(sqlStr);
       try {
           while (rs.next()){
               String s_id = rs.getString(1);
               String s_name = rs.getString(2);
               String s_sex = rs.getString(3);
               String s_major = rs.getString(4);
               String s_clas = rs.getString(5);
               String s_phone = rs.getString(6);
               String s_password = rs.getString(7);
               String s_state = rs.getString(8);
               String t_id = rs.getString(9);
               String t_name = rs.getString(10);
               student = new Student(s_id,s_name,s_sex,s_major,s_clas,s_phone,s_password,s_state,t_id,t_name);
           }
       }catch (Exception e){
           System.out.println("出错：" + e.getMessage());
       }

       nameLabel.setText(student.getS_name());
       stateLabel.setText(student.getS_state());

       System.out.println(student.getS_name());

       //关闭数据库连接
       controler.closeConnection();
   }
   //获取导师信息列表
   public void getTeachers(){
       obsList.clear();
       //连接数据库
       DatabaseControler controler = new DatabaseControler();
       controler.connect();
       sqlStr =  "select * from teacher ";
       ResultSet rs = controler.queryInDB(sqlStr);

       try{
           //显示结果
           while(rs.next()){

               String id = rs.getString(1);
               String name = rs.getString(2);
               String sex = rs.getString(3);
               String title = rs.getString(4);
               String major = rs.getString(5);
               String phone = rs.getString(6);
               String password = rs.getString(7);
               String isfull = rs.getString(9);

               obsList.add(new Teacher(id,name,sex,title,major,phone,password,isfull));
           }

       }catch (SQLException e){
           System.out.println("查询出错：" + e.getMessage());
       }

       //关闭数据库连接
       controler.closeConnection();
   }
   public void choose(){
       if(student.getS_state().equals("选定")){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText(null);
           alert.setContentText("处于选定状态，不能再选");
           alert.showAndWait();
           System.out.println("该账号不存在");
           return;
       }
       if(teacher.getT_isfull().equals("已满额")){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText(null);
           alert.setContentText("该导师已满额，请选择其他导师");
           alert.showAndWait();
           System.out.println("该账号不存在");
           return;
       }


        String id = teacher.getT_id();
        String name = teacher.getT_name();

       //连接数据库
       DatabaseControler controler = new DatabaseControler();
       controler.connect();

       //sql="update 数据表 set 字段1=值1,字段2=值2 …… 字段n=值n where 条件表达式"
       sqlStr =  "update student set t_id="+ id + ",t_name="+ "'"+ name + "'"
               + ",s_state='待定'"+" where s_id="+student.getS_id() ;
       controler.updateInDB(sqlStr);

       //关闭数据库连接
       controler.closeConnection();

       stateLabel.setText("待定");

   }
}
