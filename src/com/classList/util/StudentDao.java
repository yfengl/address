package com.classList.util;

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;

import utils.DBUtil;
import java.sql.SQLException;
import java.util.List;

public class StudentDao {

    public List<Student> getStudentsList() throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql="select * from student order by id";
        return r.query(sql, new BeanListHandler<>(Student.class));
    }

    public List<Student> getCheckStudentsList(String keyword) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql="select * from student where name like ? order by id";
        return r.query(sql, new BeanListHandler<>(Student.class),"%"+keyword+"%");
    }

    public void insert(Student s) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql0="select count(*) from student";
        String sql = "insert into Student(id,name,cNum,tel,mail,address,code) values(?,?,?,?,?,?,?)";
        String sql1="select * from student where id=(select max(id) from student)";

        int id;
        if( r.query(sql0, new ScalarHandler<Long>()).intValue()>0)
            id=r.query(sql1, new BeanHandler<>(Student.class)).getId()+1;
        else id=1;
        r.update(sql,id,s.getName(),s.getCNum(),s.getTel(),s.getMail(),s.getAddress(),s.getCode());
    }

    public void update(Student s) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "update student set name=?,cNum=?,tel=?,mail=?,address=?,code=? where id=?";
        r.update(sql,s.getName(),s.getCNum(),s.getTel(),s.getMail(),s.getAddress(),s.getCode(),s.getId());
    }

    public void delete(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "delete from student where id = ?";
        r.update(sql,id);
    }

    public Student getStudentById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select * from student where id=?";
        return r.query(sql, new BeanHandler<>(Student.class),id);
    }
}