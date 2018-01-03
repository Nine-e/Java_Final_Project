import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by del on 2018/1/1.
 */
public class ManagerView extends Application {
    private final int WINDOW_WIDTH = 750;
    private final int WINDOW_HEIGHT = 400;

    private MenuBar menuBar;
    private Menu infoMenu,setMenu,teacherMenu,studentMenu;
    private MenuItem queryItem,addtItem,settItem,addsItem,setsItem;
    private MenuItem setNumItem,exitItem;
    private SeparatorMenuItem spa;//分割线

    private TableView<Student> tableView;
    private static ObservableList<Student> obsList;
    private String sqlStr;
    private VBox infoBox;

    private VBox teacherBox;
    private VBox studentBox;
    private VBox setNumBox;
    private HBox numHBox;

    private BorderPane borderPane;

    private String todo;
    private int studentNum;

    @Override
    public void start(Stage primaryStage) throws Exception {
        borderPane = new BorderPane();
        menuBar = new MenuBar();
        buildInfoMenu();
        buildSetMenu();
        bulidQueryBox();
        bulidtBox();
        bulidsBox();
        bulidNumBox();
        menuBar.getMenus().add(infoMenu);
        menuBar.getMenus().add(setMenu);

       borderPane.setTop(menuBar);

        primaryStage.setTitle("管理员界面");
        Scene scene = new Scene(borderPane,this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void buildInfoMenu() {
        infoMenu = new Menu("信息管理");

        queryItem = new MenuItem("查询");
        queryItem.setOnAction(e->{
            borderPane.setCenter(infoBox);
        });

        teacherMenu = new Menu("导师信息");
        addtItem = new MenuItem("添加");
        addtItem.setOnAction(e->{
            borderPane.setCenter(teacherBox);
            todo="addT";
        });
        settItem = new MenuItem("修改");
        settItem.setOnAction(e->{
            borderPane.setCenter(teacherBox);
            todo="setT";
        });

        teacherMenu.getItems().addAll(addtItem,settItem);

        studentMenu = new Menu("学生信息");
        addsItem = new MenuItem("添加");
        addsItem.setOnAction(e->{
            borderPane.setCenter(studentBox);
            todo="addS";
        });
        setsItem = new MenuItem("修改");
        setsItem.setOnAction(e->{
            borderPane.setCenter(studentBox);
            todo="setS";
        });

        studentMenu.getItems().addAll(addsItem,setsItem);

        infoMenu.getItems().addAll(queryItem,teacherMenu,studentMenu);
    }
    public void buildSetMenu() {
        setMenu = new Menu("设置");

        exitItem = new MenuItem("退出");
        exitItem.setOnAction(e->System.exit(0));
        setNumItem = new MenuItem("设置可带学生数");
        setNumItem.setOnAction(e->{
            borderPane.setCenter(setNumBox);
        });

        setMenu.getItems().addAll(setNumItem,exitItem);
    }
    public void bulidQueryBox(){
        Label label = new Label("学生状态");
        Button button = new Button("查询");
        //创建字符串数组，存放学生状态
        String[] states = {" ","未选","待定","选定"};
        //创建组合框对象
        ComboBox comboBox = new ComboBox<>();
        //设置组合框
        comboBox.setPrefWidth(100);
        comboBox.setValue(" ");

        ObservableList<String> items = FXCollections.observableArrayList(states);
        comboBox.setItems(items);
        //为组合框创建事件响应
        comboBox.setOnAction(e->{
            if(comboBox.getValue().equals("未选")){
                sqlStr = "select * from student where s_state = \"未选\" ";
            }else if(comboBox.getValue().equals("待定")){
                sqlStr = "select * from student where s_state = \"待定\" ";
            }else if(comboBox.getValue().equals("选定")){
                sqlStr = "select * from student where s_state = \"选定\" ";
            }
        });


        //创建表视图
        tableView = new TableView<Student>();
        //创建列对象
        TableColumn<Student,String> tColId = new TableColumn<Student, String>("学号");
        TableColumn<Student,String> tColName = new TableColumn<Student, String>("姓名");
        TableColumn<Student,String> tColSex = new TableColumn<Student, String>("性别");
        TableColumn<Student,String> tColMajor = new TableColumn<Student, String>("专业");
        TableColumn<Student,String> tColClas = new TableColumn<Student, String>("班级");
        TableColumn<Student,String> tColPhone = new TableColumn<Student, String>("电话");
        TableColumn<Student,String> tColState = new TableColumn<Student, String>("状态");
        TableColumn<Student,String> tColTeacher = new TableColumn<Student, String>("导师");

        //设置最小列宽
        tColId.setMinWidth(130);
        tColPhone.setMinWidth(120);

        //把列对象添加到表视图
        tableView.getColumns().addAll(tColId,tColName,tColSex,tColMajor,
                tColClas,tColPhone,tColState,tColTeacher);
        //创建数据列表
        obsList = FXCollections.observableArrayList();
        //把数据列表和表视图关联
        tableView.setItems(obsList);
        //把数据列表和列对象建立关联
        tColId.setCellValueFactory(new PropertyValueFactory<Student,String>("s_id"));
        tColName.setCellValueFactory(new PropertyValueFactory<Student,String>("s_name"));
        tColSex.setCellValueFactory(new PropertyValueFactory<Student,String>("s_sex"));
        tColMajor.setCellValueFactory(new PropertyValueFactory<Student,String>("s_major"));
        tColClas.setCellValueFactory(new PropertyValueFactory<Student,String>("s_clas"));
        tColPhone.setCellValueFactory(new PropertyValueFactory<Student,String>("s_phone"));
        tColState.setCellValueFactory(new PropertyValueFactory<Student,String>("s_state"));
        tColTeacher.setCellValueFactory(new PropertyValueFactory<Student,String>("t_name"));

        //设置按钮点击事件
        button.setOnAction(event -> {
            obsList.clear();
            DatabaseControler jdbcTest = new DatabaseControler();
            jdbcTest.connect();
            ResultSet rs = jdbcTest.queryInDB(sqlStr);
            try{
                //显示结果
                while(rs.next()){
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

                    System.out.println(s_name);

                    obsList.add(new Student(s_id,s_name,s_sex,s_major,s_clas,s_phone,s_password,s_state,t_id,t_name));
                }

            }catch (SQLException e){
                System.out.println("查询出错：" + e.getMessage());
            }

            jdbcTest.closeConnection();
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().addAll(label,comboBox,button);
        infoBox = new VBox();
        infoBox.setPadding(new Insets(5,10,5,10));
        infoBox.getChildren().addAll(hBox,tableView);

    }
    public void bulidtBox(){
        Label tLabel_1 = new Label("*工号");
        Label tLabel_2 = new Label("*姓名");
        Label tLabel_3 = new Label("*性别");
        Label tLabel_4 = new Label("*职称");
        Label tLabel_5 = new Label("*研究方向");
        Label tLabel_6 = new Label("*联系电话");

        TextField TF_1 = new TextField();
        TextField TF_2 = new TextField();
        TextField TF_3 = new TextField();
        TextField TF_4 = new TextField();
        TextField TF_5 = new TextField();
        TextField TF_6 = new TextField();

        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(12,12,12,12));
        gPane.setHgap(20);
        gPane.setVgap(10);

        gPane.add(tLabel_1,0,0);
        gPane.add(tLabel_2,0,1);
        gPane.add(tLabel_3,0,2);
        gPane.add(tLabel_4,0,3);
        gPane.add(tLabel_5,0,4);
        gPane.add(tLabel_6,0,5);

        gPane.add(TF_1,1,0);
        gPane.add(TF_2,1,1);
        gPane.add(TF_3,1,2);
        gPane.add(TF_4,1,3);
        gPane.add(TF_5,1,4);
        gPane.add(TF_6,1,5);

        Button button = new Button("确定");
        button.setPrefSize(200,30);
        teacherBox = new VBox(10);
        teacherBox.setAlignment(Pos.CENTER);
        teacherBox.getChildren().addAll(gPane,button);

        //确定按钮点击事件
        button.setOnAction(e->{
            String id = TF_1.getText();
            String name = TF_2.getText();
            String sex = TF_3.getText();
            String title = TF_4.getText();
            String major = TF_5.getText();
            String phone = TF_6.getText();

            if (id.length()==0 || name.length()==0 || sex.length()==0
                    || title.length()==0 || major.length()==0 || phone.length()==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("请填写完整");
                alert.showAndWait();
                return;
            }

            if(todo.equals("addT")){
                //sql="insert into 数据表 (字段1,字段2,字段3 …) values (值1,值2,值3 …)"
                if(!isNumeric(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("账号只能由数字组成");
                    alert.showAndWait();
                    return;
                }

                //账号已存在报错
                if(isHaveId(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("该账号已存在");
                    alert.showAndWait();
                    return;
                }

                sqlStr="insert into teacher (t_id,t_name,t_sex,t_title,t_major,t_phone,t_password,t_count,t_isfull) values ("
                        + id +","
                        + "'"+name +"'" + ","
                        + "'"+sex+"'" +","
                        + "'"+title +"'"+ ","
                        + "'"+major+"'" + ","
                        + phone
                        +",123456,0,'未满额' )";
            }else if(todo.equals("setT")){
                //账号不存在报错
                if(!isHaveId(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("该账号不存在");
                    alert.showAndWait();
                    return;
                }

                sqlStr =  "update teacher set t_id="+ id + ",t_name="+ "'"+ name + "'"
                        + ",t_sex="+ "'"+ sex + "'"
                        + ",t_title="+ "'"+ title + "'"
                        + ",t_major="+ "'"+ major + "'"
                        + ",t_phone="+ "'"+ phone + "'"
                        +" where t_id="+id ;
            }
            //连接数据库
            DatabaseControler controler = new DatabaseControler();
            controler.connect();
            controler.updateInDB(sqlStr);

            //关闭数据库连接
            controler.closeConnection();
        });
    }
    public void bulidsBox(){
        Label sLabel_1 = new Label("*学号");
        Label sLabel_2 = new Label("*姓名");
        Label sLabel_3 = new Label("*性别");
        Label sLabel_4 = new Label("*专业");
        Label sLabel_5 = new Label("*班级");
        Label sLabel_6 = new Label("*联系电话");

        TextField TF_1 = new TextField();
        TextField TF_2 = new TextField();
        TextField TF_3 = new TextField();
        TextField TF_4 = new TextField();
        TextField TF_5 = new TextField();
        TextField TF_6 = new TextField();

        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(12,12,12,12));
        gPane.setHgap(20);
        gPane.setVgap(10);

        gPane.add(sLabel_1,0,0);
        gPane.add(sLabel_2,0,1);
        gPane.add(sLabel_3,0,2);
        gPane.add(sLabel_4,0,3);
        gPane.add(sLabel_5,0,4);
        gPane.add(sLabel_6,0,5);

        gPane.add(TF_1,1,0);
        gPane.add(TF_2,1,1);
        gPane.add(TF_3,1,2);
        gPane.add(TF_4,1,3);
        gPane.add(TF_5,1,4);
        gPane.add(TF_6,1,5);

        Button button = new Button("确定");
        button.setPrefSize(200,30);
        studentBox = new VBox(10);
        studentBox.setAlignment(Pos.CENTER);
        studentBox.getChildren().addAll(gPane,button);

        //确定按钮点击事件
        button.setOnAction(e->{
            String id = TF_1.getText();
            String name = TF_2.getText();
            String sex = TF_3.getText();
            String major = TF_4.getText();
            String clas = TF_5.getText();
            String phone = TF_6.getText();

            if (id.length()==0 || name.length()==0 || sex.length()==0
                    || clas.length()==0 || major.length()==0 || phone.length()==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("请填写完整");
                alert.showAndWait();
                return;
            }

            if(todo.equals("addS")){
                if(!isNumeric(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("账号只能由数字组成");
                    alert.showAndWait();
                    return;
                }

                //账号已存在报错
                if(isHaveId(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("该账号已存在");
                    alert.showAndWait();
                    return;
                }

                //sql="insert into 数据表 (字段1,字段2,字段3 …) values (值1,值2,值3 …)"
                sqlStr="insert into student (s_id,s_name,s_sex,s_major,s_clas,s_phone,s_password,s_state,t_id,t_name) values ("
                        + id +","
                        + "'"+name +"'" + ","
                        + "'"+sex+"'" +","
                        + "'"+major +"'"+ ","
                        + "'"+clas+"'" + ","
                        + phone
                        +",123456,'未选',null,null)";
            }else if(todo.equals("setS")){
                //账号不存在报错
                if(!isHaveId(id)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("该账号不存在");
                    alert.showAndWait();
                    return;
                }

                sqlStr =  "update student set s_name="+ "'"+ name + "'"
                        + ",s_sex="+ "'"+ sex + "'"
                        + ",s_major="+ "'"+ major + "'"
                        + ",s_clas="+ "'"+ clas + "'"
                        + ",s_phone="+ "'"+ phone + "'"
                        +" where s_id="+id ;
            }
            //连接数据库
            DatabaseControler controler = new DatabaseControler();
            controler.connect();
            controler.updateInDB(sqlStr);

            //关闭数据库连接
            controler.closeConnection();
        });
    }
    public void bulidNumBox(){
        Label label = new Label("导师可带学生数为：");
        label.setStyle("-fx-font-size: 15");
        TextField  TF = new TextField();
        TF.setPrefSize(100,30);
        Button button = new Button("确定");
        button.setPrefSize(200,30);

        numHBox = new HBox(20);
        numHBox.setAlignment(Pos.CENTER);
        numHBox.getChildren().addAll(label,TF);

        setNumBox = new VBox(10);
        setNumBox.setAlignment(Pos.CENTER);
        setNumBox.getChildren().addAll(numHBox,button);

        //确定按钮点击事件
        button.setOnAction(event -> {
            //连接数据库
            DatabaseControler controler = new DatabaseControler();
            controler.connect();

            int count = Integer.parseInt(TF.getText().toString());
            int count_num = 0;

            sqlStr =  "select * from count where count_id =1";
            ResultSet rs = controler.queryInDB(sqlStr);
            try {
                while (rs.next()){
                    count_num = rs.getInt(2);
                }
            }catch (Exception e){
                System.out.println("出错：" + e.getMessage());
            }

            //为避免冲突，导师所带学生数，只可改大，不可改小
            if(count>=count_num) {
                sqlStr = "update count set count_num=" + "'" + count + "'"
                        + " where count_id=1";
                controler.updateInDB(sqlStr);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("修改成功");
                alert.showAndWait();

                //更新教师状态
                sqlStr =  "update teacher set t_isfull='未满额' where t_count<"+ count ;
                controler.updateInDB(sqlStr);

            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("导师所带学生数不可改小");
                alert.showAndWait();
                return;
            }

            //关闭数据库连接
            controler.closeConnection();

        });
    }

    public void bulidManagerStage(){
        borderPane = new BorderPane();
        menuBar = new MenuBar();
        buildInfoMenu();
        buildSetMenu();
        bulidQueryBox();
        bulidtBox();
        bulidsBox();
        bulidNumBox();
        menuBar.getMenus().add(infoMenu);
        menuBar.getMenus().add(setMenu);

        borderPane.setTop(menuBar);

        Stage managerStage = new Stage();
        Scene scene = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        managerStage.setScene(scene);
        managerStage.setTitle("管理员界面");
        managerStage.show();
    }

    public static boolean isNumeric(String str)
    {
        for(int i=str.length();--i>=0;)
        {
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }

    //判断账号是否存在
    public boolean isHaveId(String s){
        if(todo.equals("addT")|| todo.equals("setT")){
            sqlStr =  "select * from teacher where t_id =" + s;
        }else if(todo.equals("addS")|| todo.equals("setS")){
            sqlStr =  "select * from student where s_id =" + s;
        }

        //连接数据库
        DatabaseControler controler = new DatabaseControler();
        controler.connect();
        ResultSet rs = controler.queryInDB(sqlStr);

        //判断结果集是否为空
        try {
            if (!rs.next()) {
                //关闭数据库连接
                controler.closeConnection();
                return false;//账号不存在
            }
        }catch (Exception e){
            System.out.println("出错：" + e.getMessage());
        }

        //关闭数据库连接
        controler.closeConnection();
        return true;//账号存在
    }

    public static void main(String[] args) {
        launch(args);
    }
}
