package com.classList;

import com.classList.util.Student;
import com.classList.util.StudentDao;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddController extends Application {
    @FXML
    private TextField addName,addTel,addMessage,addMail,addClass,addAddress,addCode;
    @FXML
    private Button addStudent,back;

    private static Stage stage;
    private final StudentDao sDao=new StudentDao();

    public void initialize() {
        addStudent.setOnAction(e->{
            try {
                addStudent();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });
        stage.setOnCloseRequest(e-> back());
        back.setOnAction(e-> back());
    }

    private void back() {
        stage.close();
        AddressList addressList=new AddressList();
        addressList.show();
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("resources/fxml/Add.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("添加用户");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void show() {
        stage=new Stage();
        start(stage);
    }

    public void addStudent() throws SQLException {
        addMessage.setText("请输入添加学生的信息");
        String name = addName.getText();
        String classNum = addClass.getText();
        String tel = addTel.getText();
        String mail = addMail.getText();
        String address = addAddress.getText();
        String code = addCode.getText();
        String s="";
        if(name.equals(s)||
                classNum.equals(s)|| address.equals(s)||
                mail.equals(s)|| code.equals(s)||
                tel.equals(s)) {
            addMessage.setText("信息不能为空！");
        }
        else {
            addMessage.setText("添加学生信息成功！！");
            addName.setText("");
            addClass.setText("");
            addTel.setText("");
            addMail.setText("");
            addAddress.setText("");
            addCode.setText("");
            Student stuI = new Student(name, classNum, tel, mail, address, code);
            sDao.insert(stuI);
        }

    }
}
