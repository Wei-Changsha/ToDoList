package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private Button registerBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit=findViewById(R.id.account);
        passwordEdit=findViewById(R.id.password);

        loginBtn=findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                checkDataValid(account,password);//检查是否密码是否为空
                List<User> loginList=DataSupport.where("account=?",account).find(User.class);
                if (loginList.isEmpty()){
                    Toast.makeText(LoginActivity.this,"用户未注册",Toast.LENGTH_SHORT).show();
                    return;
                }else if(loginList.get(0).getPassword().equals(password)){
                    //tiaozhuan
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    return;
                }else{
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        registerBtn=findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                List<User> users=DataSupport.findAll(User.class);
//                for (User user:users){
//                    if (user.getAccount().equals(account)&&user.getPassword().equals(password)){
//                        Toast.makeText(LoginActivity.this,"该账户已被注册过，请重新注册",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }

                User user_2=new User();
                user_2.setAccount(account);
                user_2.setPassword(password);
                user_2.save();//使用save保存
                for (User user:users){
                    if (user.getAccount().equals(account)&&user.getPassword().equals(password)){
                        Toast.makeText(LoginActivity.this,"该账户已被注册过，请重新注册",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        }
        );

    }

    //检查账号或者密码是否为空
    private String checkDataValid(String account,String password){
        if(TextUtils.isEmpty(account)|TextUtils.isEmpty(password))
            return "账号或者密码不能为空";
        return "";
    }
}
