package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

public class AddTodo extends AppCompatActivity implements Serializable {

    private String saveLevel;//保存等级
    private String saveIsTodo;//保存todo完成状态
    private String saveName;//保存todo名称
    private String saveDetail;//保存todo的详情描述
    private String saveAlarmTime;//
    private String saveAlarmDate;
    private String date;//保存的日期

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Calendar calendar=Calendar.getInstance();

    Button button1;
    Button button;

    EditText editName;
    EditText editDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        seletTodoLevel();//选择todo等级
        selectIsTodo();//选择todo的完成状态
        initDate();//生成todo创建日期
        initAlarm();//设置闹钟提醒的时间

        //保存数据
        Button saveBtn=findViewById(R.id.add_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                finish();
                Intent intent1=new Intent(AddTodo.this,MainActivity.class);
                intent1.putExtra("num",1);
                Intent intent=new Intent(AddTodo.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //不保存   取消
        Button notSaveBtn=findViewById(R.id.not_add_btn);
        notSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //设置闹钟提醒的时间
    public void initAlarm(){
        //选择时间
        button=findViewById(R.id.alarm_time_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                hour=calendar.get(Calendar.HOUR_OF_DAY);
                minute=calendar.get(Calendar.MINUTE);
                new TimePickerDialog(AddTodo.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY,i );
                        calendar.set(Calendar.MINUTE, i1);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        // 建立Intent和PendingIntent来调用目标组件
                        Intent intent=new Intent(AddTodo.this,AlarmReceiver.class);
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(AddTodo.this,0,intent,0);

                        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);// 获取闹钟管理的实例
                        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis() + (10 * 1000),
                                (24 * 60 * 60 * 1000), pendingIntent);// 设置周期闹钟
                        String alarm_time=i + ":" + i1;
                        saveAlarmTime=alarm_time;
                        button.setText(alarm_time);
                    }
                },hour,minute,true).show();

            }
        });

        //选择日期
        button1=findViewById(R.id.alarm_date_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH)+1;
                day=calendar.get(Calendar.DATE);
                new DatePickerDialog(AddTodo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String alarm_date=i+"年"+i1+"月"+i2+"日";
                        saveAlarmDate=alarm_date;
                        button1.setText(alarm_date);
                    }
                },year,month,day).show();

            }
        });
    }

    //显示当前闹钟创建的时间
    public void initDate(){
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        TextView textView=findViewById(R.id.data_text);
        textView.setText(year+" 年 "+month+" 月 "+day+" 日 ");
        date=month+"-"+day+"   "+hour+":"+minute;
    }

    //选择todo等级
    public void seletTodoLevel(){
        final String[] level=new String[]{"等级五","等级四","等级三","等级二","等级一"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,level);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Spinner spinner=super.findViewById(R.id.spinner_level);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddTodo.this, "你点击的是" + isTodo[i], Toast.LENGTH_SHORT).show();
                saveLevel=level[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //选择todo的完成状态
    public void selectIsTodo(){
        final String[] isTodo=new String[]{"待完成","已完成"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,isTodo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);// //设置下拉列表的风格
        Spinner spinner=super.findViewById(R.id.spinner_is_todo);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddTodo.this, "你点击的是" + isTodo[i], Toast.LENGTH_SHORT).show();
                saveIsTodo=isTodo[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void saveData(){
        editName=findViewById(R.id.edit_name);
        editDetail=findViewById(R.id.edit_statement);
        saveName=editName.getText().toString();
        saveDetail=editDetail.getText().toString();
        if (editName!=null){
            ToDo todo=new ToDo();
            todo.setTodoName(saveName);
            todo.setTodoSaveTime(date);
            todo.setTodoDetail(saveDetail);
            todo.setTodoLevel(saveLevel);
            todo.setIsTodo(saveIsTodo);
            todo.setTodoDate(saveAlarmDate);
            todo.setTodoTime(saveAlarmTime);
            todo.save();
            finish();
        }else{
            Toast.makeText(AddTodo.this,"Todo的内容不能为空",Toast.LENGTH_SHORT).show();
        }

    }

}
