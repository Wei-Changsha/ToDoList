package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int num;

    private DrawerLayout mDrawerLayout;//滑动菜单
    private List<ToDo> toDoList=new ArrayList<>();//todo的数组
    private int toDoID;

    TodoAdapter adapter=new TodoAdapter(toDoList);//将todolist数据传到适配器中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //创建数据库
//        Button createDataBtn;
//        createDataBtn=findViewById(R.id.create_database);
//        createDataBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LitePal.getDatabase();
//            }
//        });

        //主页面导航
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=findViewById(R.id.draw_layout);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu1);//设置菜单图标
        }

        kickMenu();//滑动菜单的点击事件

        //悬浮按钮
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //悬浮按钮点击事件  intent
                num=0;
                Toast.makeText(MainActivity.this,"添加todo时间",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,AddTodo.class);
                startActivity(intent);
            }
        });


        //初始化数据
        initTodo(0);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//layoutManager指定recycler view的布局方式为LinearLayout
        recyclerView.setAdapter(adapter);

        //recyclerView点击的监听器
        adapter.setOnItemClickListener(new TodoAdapter.OnItemOnClickListener() {
            @Override//短暂点击事件
            public void onItemOnClick(View view, int pos) {
                Toast.makeText(MainActivity.this, "This is"+pos, Toast.LENGTH_SHORT).show();

            }

            @Override//长按点击事件
            public void onItemLongOnClick(View view, int pos) {
                //Toast.makeText(MainActivity.this, "LongClick"+pos, Toast.LENGTH_SHORT).show();
                toDoID=pos+1;//id和pos的值  一般是相同的
                showPopMenu(view,pos);
            }
        });

    }


    //弹出menu
    public void showPopMenu(View view,final int pos){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.delete_menu,popupMenu.getMenu());

        //弹出删除菜单
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                adapter.removeItem(pos);
                DataSupport.deleteAll(ToDo.class, "id = ?", toDoID+"");
                return false;
            }
        });
        //关闭删除菜单
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }


    //初始化todo事件的各项数据
    private void initTodo(int i){
        List<ToDo> toDos= DataSupport.findAll(ToDo.class);
        ToDo mtoDo;

        switch (i){
            case 0:{//
                for (ToDo toDo:toDos){
                    mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                    toDoList.add(mtoDo);
                    Toast.makeText(this,"创建todo",Toast.LENGTH_SHORT).show();
//                    if (toDo.getIsTodo().equals("已完成")&&(!toDo.getTodoName().equals(""))){
//                        String time=toDo.getTodoDate()+"  "+toDo.getTodoTime();
//                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
//                        toDoList.add(mtoDo);
//                        Toast.makeText(this,"创建一个已完成todo",Toast.LENGTH_SHORT).show();
//                    }else if (toDo.getIsTodo().equals("待完成")&&(!toDo.getTodoName().equals(""))){
//                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
//                        toDoList.add(mtoDo);
//                        Toast.makeText(this,"创建一个待完成todo",Toast.LENGTH_SHORT).show();
//                    }
                }
            }
                break;
            case 1:{
                for (ToDo toDo:toDos){
                    if (toDo.getTodoLevel().equals("⭐⭐⭐⭐⭐")){
                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                        toDoList.add(mtoDo);
                    }else if (toDo.getTodoLevel().equals("⭐⭐⭐⭐")){
                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                        toDoList.add(mtoDo);
                    }else if (toDo.getTodoLevel().equals("⭐⭐⭐")){
                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                        toDoList.add(mtoDo);
                    }else if (toDo.getTodoLevel().equals("⭐⭐")){
                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                        toDoList.add(mtoDo);
                    }else if (toDo.getTodoLevel().equals("⭐")){
                        mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_done,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime());
                        toDoList.add(mtoDo);
                    }
                }
            }
                break;
                default:
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);//打开隐藏的滑动菜单
                break;
                default:
        }
        return true;
    }

    public void kickMenu(){
        //滑动菜单
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_person);//设置默认选中项
        //menu监听器
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch(menuItem.getItemId()) {
                    case R.id.nav_person: {

                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.nav_todo:{
                        num=1;
                        initTodo(1);
                        break;
                    }
                    case R.id.nav_undo:
                        break;
                    case R.id.nav_done:
                        break;
                    default:

                }
                return true;
            }
        });

    }



}
