package com.example.todolist;

import java.io.Serializable;

public class TodoItem implements Serializable {
    private int imageID;
    private String name;
    private String level;
    private String date;
    public TodoItem(int imageID,String name,String level,String date){
        this.imageID=imageID;
        this.name=name;
        this.level=level;
        this.date=date;
    }
    public TodoItem(){}

}
