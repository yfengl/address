package com.classList;

import com.classList.util.Student;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.classList.util.StudentDao;

public class AddressList extends Application {
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<?,?> deleteCol,updateCol;
    @FXML
    private TableColumn<?,?> numberCol;
    @FXML
    private TableColumn<?,?> classCol,mailCol,telCol,nameCol;
    @FXML
    private Button add,check,about,file,updateStudent;
    @FXML
    private TextField fileName,checkID,message;

    private final StudentDao sDao=new StudentDao();

    private static Stage stage;

    public int updateId;

    public void initialize() {
        try {
            getStudentList("getStudentsList()");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        updateStudent.setOnAction(e->{
            message.setText("updateStudent");
            try {
                getStudentList("getStudentsList()");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });

        file.setOnAction(e->{
            message.setText("openFile");
            try {
                saveFile();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });

        add.setOnAction(e-> addStudent());

        check.setOnAction(e-> {
            message.setText("check: "+"” "+checkID.getText()+" “");
            try {
                checkStudent();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });

        about.setOnAction(e->{
            Alert aboutDialog = createAboutDialog();
            aboutDialog.show();
        });

    }
    public void show(){
        stage=new Stage();
        start(stage);
    }

    @Override
    public void start(Stage stage) {
        AddressList.stage =stage;
        try {
            Parent root= FXMLLoader.load(getClass().getResource("resources/fxml/Address.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("班级通讯录系统");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void getStudentList(String key) throws SQLException {
        ObservableList<Student> list= FXCollections.observableArrayList();
        List<Student> listDao;
        if(key.equals("getStudentsList()")) listDao=sDao.getStudentsList();
        else listDao=sDao.getCheckStudentsList(key);

        list.addAll(listDao);

        numberCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        classCol.setCellValueFactory(
                new PropertyValueFactory<>("cNum"));
        telCol.setCellValueFactory(
                new PropertyValueFactory<>("tel"));
        mailCol.setCellValueFactory(
                new PropertyValueFactory<>("mail"));
        deleteCol.setCellValueFactory(
                new PropertyValueFactory<>("deleteButton"));
        updateCol.setCellValueFactory(
                new PropertyValueFactory<>("editButton"));

        tableView.setItems(list);
    }

    public void addStudent() {
        stage.close();
        AddController addController = new AddController();
        addController.show();
    }

    public void deleteStudent(int id) throws SQLException {
        String name=sDao.getStudentById(id).getName();
        Optional<ButtonType> result = showDeleteConfirmationDialog(name);
        switch (Objects.requireNonNull(result.orElse(null)).getButtonData()) {
            case YES:
                try {
                    sDao.delete(id);
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                stage.close();
                stage.show();
            case CANCEL_CLOSE:
            default:
        }

    }

    public void checkStudent() throws SQLException {
        String keyword=checkID.getText();
        if(keyword.equals("")) message.setText("查询输入姓名不能为空！");
        else getStudentList(keyword);
    }

    public void updateStudent(int id){
        stage.close();
        updateId=id;
        UpdateController updateController = new UpdateController();
        updateController.show(id);
    }

    private void saveFile() throws SQLException {
        File textFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("导出到文件");
        fileChooser.setInitialFileName("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
        textFile = fileChooser.showSaveDialog(stage);
        if(textFile!=null){
            List<Student> listDao=sDao.getStudentsList();
            fileName.setText(textFile.getName());
            try(PrintWriter writer = new PrintWriter(textFile)) {
                writer.write("编号   姓名    班级     电话            " +
                        "邮箱               地址          邮编\n");
                for(Student s:listDao){
                    writer.write(s.toString()+"\n");
                }
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Optional<ButtonType> showDeleteConfirmationDialog(String name) {
        ButtonType sure = new ButtonType("确定", ButtonBar.ButtonData.YES);
        ButtonType cancel = ButtonType.CANCEL;

        Alert alert = new Alert(Alert.AlertType.NONE, "你确定删除“ " + name +" ”及其相关信息吗？", sure, cancel);
        alert.setTitle("“删除”");
        alert.initOwner(stage);
        alert.initStyle(StageStyle.UTILITY);
        return alert.showAndWait();
    }

    private Alert createAboutDialog() {
        message.setText("about");
        Alert aboutDialog = new Alert(Alert.AlertType.NONE);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setMaxHeight(Region.USE_PREF_SIZE);
        dialogPane.setMaxWidth(Region.USE_PREF_SIZE);
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setMinWidth(Region.USE_PREF_SIZE);
        dialogPane.setPadding(new Insets(10));
        dialogPane.setHeaderText("通讯录系统\n"
                + "版本 1.0 \n"
                + "© 2020 yfl。保留所有权利。\n");
        dialogPane.getButtonTypes().add(ButtonType.OK);

        aboutDialog.initOwner(stage);
        aboutDialog.initStyle(StageStyle.UTILITY);
        aboutDialog.setTitle("关于“通讯录系统”");
        aboutDialog.setDialogPane(dialogPane);

        return aboutDialog;
    }
}
