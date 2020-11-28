package com.classList;

import com.classList.util.Student;
import com.classList.util.StudentDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateController {
    @FXML
    private Button updateStudent,back;
    @FXML
    private TextField updateMail,updateClass,updateTel,updateAddress,updateCode,updateMessage;
    @FXML
    private Text updateName;

    private Student stuI;
    private final StudentDao sDao=new StudentDao();
    private static Stage stage;
    private static int id;

    public void initialize() {
        try {
            stuI=sDao.getStudentById(id);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        updateName.setText(stuI.getName());
        updateClass.setText(stuI.getCNum());
        updateTel.setText(stuI.getTel());
        updateMail.setText(stuI.getMail());
        updateAddress.setText(stuI.getAddress());
        updateCode.setText(stuI.getCode());

        updateMessage.setText("请输入修改学生的信息");

        updateStudent.setOnAction(e->{
            try {
                updateStudent();
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

    public void start(Stage stage) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("resources/fxml/Update.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("修改用户");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void show(int id) {
        stage=new Stage();
        UpdateController.id =id;
        start(stage);
    }

    public void updateStudent() throws SQLException {
        String name=updateName.getText();
        String classNum=updateClass.getText();
        String tel=updateTel.getText();
        String mail=updateMail.getText();
        String ad=updateAddress.getText();
        String code=updateCode.getText();
        String s="";
        if(name.equals(s)||
                classNum.equals(s)||ad.equals(s)||
                mail.equals(s)||code.equals(s)||
                tel.equals(s)) {
            updateMessage.setText("信息不能为空！");
        }
        else {
            updateMessage.setText("修改学生信息成功！！");
            stuI=new Student(id,name,classNum,tel,mail,ad,code);
            sDao.update(stuI);
        }
    }
}
