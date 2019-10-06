package com.example.todolist;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

//数据表
public class ToDo extends DataSupport implements Serializable {
    private String todoName;//todo的名称
    private String todoLevel;//todo的等级  1~5⭐级 private int todoLevel;//todo的等级  1~5⭐级
    private String todoSaveTime;//todo保存的时间
    private String todoDetail;//todo的详情描述
    private String todoDate;//闹钟日期
    private String todoTime;//钟时间
    private int id;

    private String isTodo;//是否已完成
    private int todoID;//todo的imageID

    public ToDo(String todoName,int todoID,String todoLevel,String todoTime,String todoSaveTime){
        this.todoName=todoName;
        this.todoID=todoID;
        this.todoLevel=todoLevel;
        this.todoTime=todoTime;
        this.todoSaveTime=todoSaveTime;
    }
    public ToDo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public void setTodoDetail(String todoDetail) {
        this.todoDetail = todoDetail;
    }

    public void setTodoID(int todoID) {
        this.todoID = todoID;
    }

    public void setTodoLevel(String todoLevel) {
        this.todoLevel = todoLevel;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public void setTodoSaveTime(String todoSaveTime) {
        this.todoSaveTime = todoSaveTime;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }

    public void setIsTodo(String isTodo) {
        this.isTodo = isTodo;
    }

    public String getTodoName() {
        return todoName;
    }

    public String getTodoLevel() {
        return todoLevel;
    }

    public int getTodoID() {
        return todoID;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public String getTodoDetail() {
        return todoDetail;
    }

    public String getIsTodo() {
        return isTodo;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public String getTodoSaveTime() {
        return todoSaveTime;
    }
}
