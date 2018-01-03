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
        primaryStage.setTitle("ѧ������");
        primaryStage.show();
    }
   public void bulidStudentStage() {
       bulidVBox();
       Stage studentStage = new Stage();
       Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
       studentStage.setScene(scene);
       studentStage.setTitle("ѧ������");
       studentStage.show();

       initSV();
   }

   public void bulidVBox(){
       Label label_1 = new Label("����:");
       label_1.setStyle("-fx-font-size: 15");
       Label label_2 = new Label("״̬:");
       label_2.setStyle("-fx-font-size: 15");
       nameLabel = new Label("����");
       nameLabel.setStyle("-fx-font-size: 15");
       stateLabel = new Label("״̬");
       stateLabel.setStyle("-fx-font-size: 15");

       chooseBtn = new javafx.scene.control.Button("ѡ��õ�ʦ");
       // chooseBtn.setPreferredSize();

       //��������ͼ
       tableView = new TableView<Teacher>();
       //�����ж���
       TableColumn<Teacher,String> tColId = new TableColumn<Teacher, String>("����");
       TableColumn<Teacher,String> tColName = new TableColumn<Teacher, String>("����");
       TableColumn<Teacher,String> tColSex = new TableColumn<Teacher, String>("�Ա�");
       TableColumn<Teacher,String> tColTitle = new TableColumn<Teacher, String>("ְ��");
       TableColumn<Teacher,String> tColMajor = new TableColumn<Teacher, String>("�о�����");
       TableColumn<Teacher,String> tColPhone = new TableColumn<Teacher, String>("��ϵ�绰");
       TableColumn<Teacher,String> tColState = new TableColumn<Teacher, String>("״̬");
       //������С�п�
       tColId.setMinWidth(100);
       tColPhone.setMinWidth(130);
       //���ж�����ӵ�����ͼ
       tableView.getColumns().addAll(tColId,tColName,tColSex,tColTitle,tColMajor,tColPhone,tColState);
       //���������б�
       obsList = FXCollections.observableArrayList();
       //�������б�ͱ���ͼ����
       tableView.setItems(obsList);
       //�������б���ж���������
       tColId.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_id"));
       tColName.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_name"));
       tColSex.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_sex"));
       tColTitle.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_title"));
       tColMajor.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_major"));
       tColPhone.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_phone"));
       tColState.setCellValueFactory(new PropertyValueFactory<Teacher,String>("t_isfull"));
       //tableView�����¼�
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

       //ѡ����ť����¼�
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

   //��ʼ��StudentView����
   public void initSV(){
       getStudent();
       getTeachers();
   }

   //���õ�ǰѧ��Id
   public void setId(String s){
       s_id = s;
       //System.out.println(s_id);
   }
   //��ȡ��ǰѧ����Ϣ
   public void getStudent(){
       //System.out.println(s_id);
       //�������ݿ�
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
           System.out.println("����" + e.getMessage());
       }

       nameLabel.setText(student.getS_name());
       stateLabel.setText(student.getS_state());

       System.out.println(student.getS_name());

       //�ر����ݿ�����
       controler.closeConnection();
   }
   //��ȡ��ʦ��Ϣ�б�
   public void getTeachers(){
       obsList.clear();
       //�������ݿ�
       DatabaseControler controler = new DatabaseControler();
       controler.connect();
       sqlStr =  "select * from teacher ";
       ResultSet rs = controler.queryInDB(sqlStr);

       try{
           //��ʾ���
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
           System.out.println("��ѯ����" + e.getMessage());
       }

       //�ر����ݿ�����
       controler.closeConnection();
   }
   public void choose(){
       if(student.getS_state().equals("ѡ��")){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText(null);
           alert.setContentText("����ѡ��״̬��������ѡ");
           alert.showAndWait();
           System.out.println("���˺Ų�����");
           return;
       }
       if(teacher.getT_isfull().equals("������")){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText(null);
           alert.setContentText("�õ�ʦ�������ѡ��������ʦ");
           alert.showAndWait();
           System.out.println("���˺Ų�����");
           return;
       }


        String id = teacher.getT_id();
        String name = teacher.getT_name();

       //�������ݿ�
       DatabaseControler controler = new DatabaseControler();
       controler.connect();

       //sql="update ���ݱ� set �ֶ�1=ֵ1,�ֶ�2=ֵ2 ���� �ֶ�n=ֵn where �������ʽ"
       sqlStr =  "update student set t_id="+ id + ",t_name="+ "'"+ name + "'"
               + ",s_state='����'"+" where s_id="+student.getS_id() ;
       controler.updateInDB(sqlStr);

       //�ر����ݿ�����
       controler.closeConnection();

       stateLabel.setText("����");

   }
}
