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

/**
 * Created by del on 2018/1/1.
 */
public class ManagerView extends Application {
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 400;

    private MenuBar menuBar;
    private Menu infoMenu,setMenu,teacherMenu,studentMenu;
    private MenuItem queryItem,addtItem,settItem,addsItem,setsItem;
    private MenuItem setNumItem,exitItem;
    private SeparatorMenuItem spa;//�ָ���

    private TableView<Student> tableView;
    private static ObservableList<Student> obsList;
    private String sqlStr;
    private VBox infoBox;

    private VBox teacherBox;
    private VBox studentBox;
    private VBox setNumBox;

    private BorderPane borderPane;

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

        primaryStage.setTitle("����Ա����");
        Scene scene = new Scene(borderPane,this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void buildInfoMenu() {
        infoMenu = new Menu("��Ϣ����");

        queryItem = new MenuItem("��ѯ");
        queryItem.setOnAction(e->{
            borderPane.setCenter(infoBox);
        });

        teacherMenu = new Menu("��ʦ��Ϣ");
        addtItem = new MenuItem("���");
        addtItem.setOnAction(e->{
            borderPane.setCenter(teacherBox);
        });
        settItem = new MenuItem("�޸�");
        settItem.setOnAction(e->{
            borderPane.setCenter(teacherBox);
        });

        teacherMenu.getItems().addAll(addtItem,settItem);

        studentMenu = new Menu("ѧ����Ϣ");
        addsItem = new MenuItem("���");
        addsItem.setOnAction(e->{
            borderPane.setCenter(studentBox);
        });
        setsItem = new MenuItem("�޸�");
        setsItem.setOnAction(e->{
            borderPane.setCenter(studentBox);
        });

        studentMenu.getItems().addAll(addsItem,setsItem);

        infoMenu.getItems().addAll(queryItem,teacherMenu,studentMenu);
    }
    public void buildSetMenu() {
        setMenu = new Menu("����");

        exitItem = new MenuItem("�˳�");
        exitItem.setOnAction(e->System.exit(0));
        setNumItem = new MenuItem("���ÿɴ�ѧ����");
        setNumItem.setOnAction(e->{
            borderPane.setCenter(setNumBox);
        });

        setMenu.getItems().addAll(setNumItem,exitItem);
    }
    public void bulidQueryBox(){
        Label label = new Label("ѧ��״̬");
        Button button = new Button("��ѯ");
        //�����ַ������飬���ѧ��״̬
        String[] states = {" ","δѡ","����","ѡ��"};
        //������Ͽ����
        ComboBox comboBox = new ComboBox<>();
        //������Ͽ�
        comboBox.setPrefWidth(100);
        comboBox.setValue(" ");

        ObservableList<String> items = FXCollections.observableArrayList(states);
        comboBox.setItems(items);
        //Ϊ��Ͽ򴴽��¼���Ӧ
        comboBox.setOnAction(e->{
            if(comboBox.getValue().equals("δѡ")){
                sqlStr = "select * from studentData where state = \"δѡ\" ";
            }else if(comboBox.getValue().equals("����")){
                sqlStr = "select * from studentData where state = \"����\" ";
            }else if(comboBox.getValue().equals("ѡ��")){
                sqlStr = "select * from studentData where state = \"ѡ��\" ";
            }
        });


        //��������ͼ
        tableView = new TableView<Student>();
        //�����ж���
        TableColumn<Student,String> tColId = new TableColumn<Student, String>("ѧ��");
        TableColumn<Student,String> tColName = new TableColumn<Student, String>("����");
        TableColumn<Student,String> tColSex = new TableColumn<Student, String>("�Ա�");
        TableColumn<Student,String> tColMajor = new TableColumn<Student, String>("רҵ");
        TableColumn<Student,String> tColClas = new TableColumn<Student, String>("�༶");
        TableColumn<Student,String> tColPhone = new TableColumn<Student, String>("�绰");
        TableColumn<Student,String> tColState = new TableColumn<Student, String>("״̬");
        TableColumn<Student,String> tColTeacher = new TableColumn<Student, String>("��ʦ");

        //���ж�����ӵ�����ͼ
        tableView.getColumns().addAll(tColId,tColName,tColSex,tColMajor,
                tColClas,tColPhone,tColState,tColTeacher);
        //���������б�
        obsList = FXCollections.observableArrayList();
        //�������б�ͱ���ͼ����
        tableView.setItems(obsList);
        //�������б���ж���������
        tColId.setCellValueFactory(new PropertyValueFactory<Student,String>("s_id"));
        tColName.setCellValueFactory(new PropertyValueFactory<Student,String>("s_name"));
        tColSex.setCellValueFactory(new PropertyValueFactory<Student,String>("s_sex"));
        tColMajor.setCellValueFactory(new PropertyValueFactory<Student,String>("s_major"));
        tColClas.setCellValueFactory(new PropertyValueFactory<Student,String>("s_clas"));
        tColPhone.setCellValueFactory(new PropertyValueFactory<Student,String>("s_phone"));
        tColState.setCellValueFactory(new PropertyValueFactory<Student,String>("s_state"));
        tColTeacher.setCellValueFactory(new PropertyValueFactory<Student,String>("t_name"));

        //���ð�ť����¼�
        /*button.setOnAction(event -> {
            Main jdbcTest = new Main();
            jdbcTest.connect();
            jdbcTest.queryInDB(sqlStr);
            jdbcTest.closeConnection();
        });*/

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().addAll(label,comboBox,button);
        infoBox = new VBox();
        infoBox.setPadding(new Insets(5,10,5,10));
        infoBox.getChildren().addAll(hBox,tableView);

    }
    public void bulidtBox(){
        Label tLabel_1 = new Label("����");
        Label tLabel_2 = new Label("����");
        Label tLabel_3 = new Label("�Ա�");
        Label tLabel_4 = new Label("ְ��");
        Label tLabel_5 = new Label("�о�����");
        Label tLabel_6 = new Label("��ϵ�绰");

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

        Button button = new Button("ȷ��");
        teacherBox = new VBox(10);
        teacherBox.setAlignment(Pos.CENTER);
        teacherBox.getChildren().addAll(gPane,button);
    }
    public void bulidsBox(){
        Label sLabel_1 = new Label("ѧ��");
        Label sLabel_2 = new Label("����");
        Label sLabel_3 = new Label("�Ա�");
        Label sLabel_4 = new Label("רҵ");
        Label sLabel_5 = new Label("�༶");
        Label sLabel_6 = new Label("��ϵ�绰");

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

        Button button = new Button("ȷ��");
        studentBox = new VBox(10);
        studentBox.setAlignment(Pos.CENTER);
        studentBox.getChildren().addAll(gPane,button);
    }
    public void bulidNumBox(){
        Label label = new Label("��ʦ�ɴ�ѧ����Ϊ��");
        TextField  TF = new TextField();
        Button button = new Button("ȷ��");
        setNumBox = new VBox(10);
        setNumBox.setAlignment(Pos.CENTER);
        setNumBox.getChildren().addAll(label,TF,button);
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
        managerStage.setTitle("����Ա����");
        managerStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
