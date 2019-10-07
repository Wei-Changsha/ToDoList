package com.example.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public  void onReceive(Context context, Intent intent){
        Toast.makeText(context, "时间到了，您有todo未完成哦！",
                Toast.LENGTH_SHORT).show();
    }
}
