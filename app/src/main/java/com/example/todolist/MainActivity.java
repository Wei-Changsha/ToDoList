package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;//滑动菜单
    private List<ToDo> toDoList=new ArrayList<>();//todo的数组
    private int toDoID;
    static int num=0;



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

        //悬浮按钮
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //悬浮按钮点击事件  intent
                //Toast.makeText(MainActivity.this,"添加todo时间",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,AddTodo.class);
                startActivity(intent);
            }
        });
        kickMenu();//滑动菜单的点击事件

        //初始化数据
        initTodo();



        final RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//layoutManager指定recycler view的布局方式为LinearLayout
        recyclerView.setAdapter(adapter);

        //recyclerView点击的监听器
        adapter.setOnItemClickListener(new TodoAdapter.OnItemOnClickListener() {
            @Override//短暂点击事件
            public void onItemOnClick(View view, int pos) {
                Toast.makeText(MainActivity.this,toDoID +" pos "+pos, Toast.LENGTH_SHORT).show();
            }

            @Override//长按点击事件
            public void onItemLongOnClick(View view, int pos) {
//                TodoAdapter.ViewHolder viewHolder = null;
//                adapter.onBindViewHolder(viewHolder,pos);
//                viewHolder.getClass();
                toDoID=pos+1+num;//id和pos的值  一般是相同的
                Log.d("MainActivity","aaatoDoid  "+toDoID);
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
                num=num+1;
                Log.d("MainActivity","aaanum  "+num);
                List<ToDo> toDos= DataSupport.where("id>?",String.valueOf(toDoID)).find(ToDo.class);
                for (ToDo toDo:toDos){
                   int  newID=toDo.getId()-1;
                    Log.d("MainActivity","aaa改前  "+toDo.getId());

                    toDo.setId(newID);
                    toDo.updateAll("id=?",toDo.getId()+"");
                    toDo.save();
                    toDo.update(toDo.getId());
                    Log.d("MainActivity","aaa改后  "+toDo.getId());
                }

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
    private void initTodo(){
        List<ToDo> toDos= DataSupport.findAll(ToDo.class);
        ToDo mtoDo;

        for (ToDo toDo:toDos){
            mtoDo=new ToDo(toDo.getTodoName(),R.drawable.ic_undo,toDo.getTodoLevel(),toDo.getTodoDate()+"  "+toDo.getTodoTime(),toDo.getTodoSaveTime(),"详情："+toDo.getTodoDetail());
            toDoList.add(mtoDo);
            mtoDo.getTodoID();
            Log.d("MainActivity","aaa初始化  "+toDo.getId());
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
                        Intent intent=new Intent(MainActivity.this,Sort.class);
                        startActivity(intent);
                        break;
                    }
                    default:

                }
                return true;
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
