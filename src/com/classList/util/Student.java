package com.classList.util;

import com.classList.AddressList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class Student{
    private final AddressList ad=new AddressList();
    private int id;
    private String ids;
    private String name,names;
    private String cNum,cNums;
    private String tel,tels;
    private String mail,mails;
    private String address,addresses;
    private String code,codes;
    private SimpleObjectProperty<DeleteButton> deleteButton;
    private SimpleObjectProperty<EditButton> editButton;

    public Student(){
        super();
        deleteButton = new SimpleObjectProperty(new DeleteButton());
        editButton= new SimpleObjectProperty(new EditButton());
    }

    public Student(String name, String cNum, String tel,String mail, String address, String code) {
        this.name = name;
        this.cNum = cNum;
        this.tel = tel;
        this.mail = mail;
        this.address = address;
        this.code = code;
    }
    public Student(int id,String name, String cNum, String tel,String mail, String address, String code) {
        this.id=id;
        this.name = name;
        this.cNum = cNum;
        this.tel = tel;
        this.mail = mail;
        this.address = address;
        this.code = code;
    }

    public String setString(String s1,int length,String s2){
        int len=s1.length();
        int lens=length-len;
        String s=s1;
        if(lens>0){
            for(int i=0;i<lens;i++){
                s+=s2;
            }
        }
        return s;
    }

    public void setIds(){
        int n=0;
        int id=this.id;
        String s=" ";
        while(id>=10){
            id/=10;
            n++;
        }
        for(int i=0;i<4-n;i++){
            s+=" ";
        }
        this.ids=s;
    }

    public void setNames() {
        this.names =setString(name,4,"  ");
    }

    public void setcNums() {
        this.cNums = setString(cNum,4," ");
    }

    public void setTels() { this.tels = setString(tel,11," "); }

    public void setMails() { this.mails = setString(mail,19," "); }

    public void setAddresses() {
        this.addresses = setString(address,7,"  ");
    }

    public void setCodes() {
        this.codes = code;
    }

    class DeleteButton extends Button {
        public DeleteButton() {
            super("删除");
            setOnAction(e -> {
                try {
                    ad.deleteStudent(id);
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            });
        }
    }

    class EditButton extends Button {
        public EditButton() {
            super("修改");
            setOnAction(e -> ad.updateStudent(id));
        }
    }

    @Override
    public String toString() {
        setNames();setcNums();setAddresses();setCodes();setMails();setTels();setIds();
        return id + ids + names + "  " +
                cNums + "  " + tels + "  " + mails + "  " +
                    addresses + "  " +codes;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getCNum() { return cNum; }
    public void setCNum(String cNum) { this.cNum = cNum; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public DeleteButton getDeleteButton() { return deleteButton.get(); }
    public void setDeleteButton(DeleteButton deleteButton) { this.deleteButton.set(deleteButton); }
    public ObjectProperty<DeleteButton> deleteButtonProperty() { return deleteButton; }
    public EditButton getEditButton() { return editButton.get(); }
    public void setEditButton(EditButton editButton) { this.editButton.set(editButton); }
    public ObjectProperty<EditButton> editButtonProperty() { return editButton; }
}