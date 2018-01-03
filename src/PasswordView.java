import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.*;

/**
 * Created by del on 2018/1/3.
 */
public class PasswordView extends Application{
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 400;

    private Label loginLabel;
    private Label numberLabel;
    private Label passwordLabel;
    private Label newPassLabel;
    private TextField numberTF;
    private PasswordField passwordTF;
    private PasswordField newPassTF;
    private Button button;

    private RadioButton studentRB;
    private RadioButton teacherRB;
    private RadioButton managerRB;
    private ToggleGroup toggleGroup;

    private VBox vBox;
    private BorderPane borderPane;
    private HBox numberHBox;
    private HBox passwordHBox;
    private HBox newPassHBox;
    private HBox radioSelectHBox;

    private Stage studentStage;
    private String sqlStr;
    private String tableName;
    private Boolean isDisplay;
    private Stage passwordStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        bulidVBox();

        //������̨�ͳ���
        Scene scene = new Scene(vBox,WINDOW_WIDTH,WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("�޸�����");
        primaryStage.show();

    }
    public void bulidVBox(){
        //����Label��TextField��Button�����
        loginLabel = new Label("�޸�����");
        loginLabel.setStyle("-fx-font-size: 22px");
        //loginLabel.setPadding(new );
        numberLabel = new Label("   �˺�");
        numberLabel.setStyle("-fx-font-size: 18px");
        passwordLabel = new Label("ԭ����");
        passwordLabel.setStyle("-fx-font-size: 18px");
        newPassLabel = new Label("������");
        newPassLabel.setStyle("-fx-font-size: 18px");
        numberTF = new TextField();
        numberTF.setPrefColumnCount(10);
        numberTF.setPrefHeight(35);
        passwordTF = new PasswordField();
        passwordTF.setPrefColumnCount(10);
        passwordTF.setPrefHeight(35);
        newPassTF = new PasswordField();
        newPassTF.setPrefColumnCount(10);
        newPassTF.setPrefHeight(35);

        button = new Button("ȷ��");
       button.setPrefSize(200,40);
        button.setAlignment(Pos.CENTER);
        //��¼��ť����¼�
        button.setOnAction(e->{
            setPassword();
            //��ʱע�͵�����ɾ������������
            if(isDisplay) {
                passwordStage.close();
            }
        });


        //������ѡ��ť
        toggleGroup = new ToggleGroup();
        studentRB = new RadioButton("ѧ��");
        studentRB.setToggleGroup(toggleGroup);
        teacherRB = new RadioButton("��ʦ");
        teacherRB.setToggleGroup(toggleGroup);
        managerRB = new RadioButton("����Ա");
        managerRB.setToggleGroup(toggleGroup);

        studentRB.setUserData("student");
        teacherRB.setUserData("teacher");
        managerRB.setUserData("manager");

        //���õ�ѡ��ť����
        studentRB.setSelected(true);
        tableName = "student";


        //��ѡ��ť�����¼�
        toggleGroup.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                 Toggle new_Toggle) -> {
                    if (toggleGroup.getSelectedToggle() != null) {
                        tableName = toggleGroup.getSelectedToggle().getUserData().toString();
                        System.out.println(tableName);
                    }
                });

        //����HBox
        numberHBox = new HBox(20);
        numberHBox.getChildren().addAll(numberLabel,numberTF);
        numberHBox.setAlignment(Pos.CENTER);
        passwordHBox = new HBox(20);
        passwordHBox.getChildren().addAll(passwordLabel,passwordTF);
        passwordHBox.setAlignment(Pos.CENTER);
        newPassHBox = new HBox(20);
        newPassHBox.getChildren().addAll(newPassLabel,newPassTF);
        newPassHBox.setAlignment(Pos.CENTER);
        radioSelectHBox = new HBox(20);
        radioSelectHBox.getChildren().addAll(studentRB,teacherRB,managerRB);
        radioSelectHBox.setAlignment(Pos.CENTER);

        //����VBox
        vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(loginLabel,numberHBox,passwordHBox,newPassHBox);
        vBox.getChildren().add(radioSelectHBox);
        vBox.getChildren().add(button);

    }
    public void bulidPasswordStage(){
        bulidVBox();
        //������̨�ͳ���
        Scene scene = new Scene(vBox,WINDOW_WIDTH,WINDOW_HEIGHT);
        passwordStage = new Stage();
        passwordStage.setScene(scene);
        passwordStage.setTitle("�޸�����");
        passwordStage.show();
    }
    public void setPassword(){
        String number = numberTF.getText();
        String password = passwordTF.getText();
        String newPass = newPassTF.getText();
        String p = "";
        isDisplay = true;

        if(number.length()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("�������˺�");
            alert.showAndWait();
            isDisplay = false;
            return;
        }else if(password.length()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("������ԭ����");
            alert.showAndWait();
            isDisplay = false;
            return;
        }else if(newPass.length()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("������������");
            alert.showAndWait();
            isDisplay = false;
            return;
        }

        if(tableName.equals("student")){
            sqlStr = "select * from student where s_id ="+number;
            System.out.println("student login");
        }else if(tableName.equals("teacher")){
            sqlStr = "select * from teacher where t_id ="+number;
            System.out.println("teacher login");
        }else if(tableName.equals("manager")){
            sqlStr = "select * from manager where m_id ="+number;
            System.out.println("manager login");
        }

        //�������ݿ�
        DatabaseControler controler = new DatabaseControler();
        controler.connect();
        System.out.println(sqlStr);
        ResultSet rs = controler.queryInDB(sqlStr);

        //�жϽ�����Ƿ�Ϊ��
        try {
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("���˺Ų�����");
                alert.showAndWait();
                System.out.println("���˺Ų�����");
                isDisplay = false;
                return;
            }
        }catch (Exception e){
            System.out.println("����" + e.getMessage());
        }

        //��ȡ����
        try {
            do{
                if(tableName.equals("manager")){
                    p = rs.getString(2);
                }else {
                    p = rs.getString(7);
                    //System.out.println(p+"44");
                }
            }while (rs.next());
        }catch (Exception e){
            System.out.println("����" + e.getMessage());
        }

        //�ж�ԭ�����Ƿ���ȷ
        //������ȷ���޸�����
        if (password.equals(p)){
            if(tableName.equals("student"))
            {
                sqlStr = "update student set s_password="+ "'"+ newPass + "'"
                        +" where s_id="+ number ;
            }else if(tableName.equals("teacher")){
                sqlStr = "update teacher set t_password="+ "'"+ newPass + "'"
                        +" where t_id="+ number ;
            }else if(tableName.equals("manager")){
                sqlStr = "update manager set m_password="+ "'"+ newPass + "'"
                        +" where m_id="+ number ;
            }
            controler.updateInDB(sqlStr);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("�޸ĳɹ�");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("�������");
            alert.showAndWait();
            System.out.println("�������");
            isDisplay = false;
        }

        //�ر����ݿ�����
        controler.closeConnection();
    }

}
