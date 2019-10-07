package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Sort extends AppCompatActivity {


    private List<ToDo> toDoList=new ArrayList<>();//todo的数组

    TodoAdapter adapter=new TodoAdapter(toDoList);//将todolist数据传到适配器中


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);


        List<ToDo> toDos= DataSupport.findAll(ToDo.class);
        ToDo mtoDo;

        for (ToDo toDo:toDos){
            if (toDo.getTodoLevel().equals("等级五")){
                mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
                toDoList.add(mtoDo);
            }
        }
        for (ToDo toDo:toDos){
            if (toDo.getTodoLevel().equals("等级四")){
                mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
                toDoList.add(mtoDo);
            }
        }
        for (ToDo toDo:toDos){
            if (toDo.getTodoLevel().equals("等级三")){
                mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
                toDoList.add(mtoDo);
            }
        }
        for (ToDo toDo:toDos){
            if (toDo.getTodoLevel().equals("等级二")){
                mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
                toDoList.add(mtoDo);
            }
        }
        for (ToDo toDo:toDos){
            if (toDo.getTodoLevel().equals("等级一")){
                mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
                toDoList.add(mtoDo);
            }
        }


        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//layoutManager指定recycler view的布局方式为LinearLayout
        recyclerView.setAdapter(adapter);

    }
}
