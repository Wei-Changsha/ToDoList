package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.db.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private Button registerBtn;
    private Button button;

    //记住密码
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;

    boolean isRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember_password",false);
        rememberPass=findViewById(R.id.remeber_pass);
        accountEdit=findViewById(R.id.account);
        passwordEdit=findViewById(R.id.password);

        rememberOrNot();//是否记住密码

//        boolean isRemember=pref.getBoolean("remember_password",false);
//        String account=pref.getString("account","");
//        String password=pref.getString("password","");
//        if (!account.equals("")&&!password.equals("")){
//            if (isRemember){//记住密码    将账户和密码复制到文本框
//                accountEdit.setText(account);
//                passwordEdit.setText(password);
//                rememberPass.setChecked(true);
//                User user=new User();
//                user.setPassword(password);
//                user.setAccount(account);
//                user.setRemember(true);
//                user.save();
//            }
//        }


        loginBtn=findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _account=accountEdit.getText().toString();
                String _password=passwordEdit.getText().toString();
                checkDataValid(_account,_password);//检查是否密码无效

                Toast.makeText(LoginActivity.this,"aaaa",Toast.LENGTH_SHORT).show();


                List<User> users=DataSupport.findAll(User.class);

                for (User user:users){
                    if (_account.equals("wcs")&&_password.equals("123456")){
                        Toast.makeText(LoginActivity.this,user.getAccount(),Toast.LENGTH_SHORT).show();
                    //if (_account.equals(user.getAccount())&&_password.equals(user.getPassword())){
                        //检查复选框是否被选中
                        if (rememberPass.isChecked()){
                            user.setPassword(_password);
                            user.setAccount(_account);
                            user.setRemember(true);
                            user.setRemember(pref.getBoolean("remember_password",true));
                            user.save();

                        }else{
                            user.setRemember(pref.getBoolean("remember_password",false));
                        }

                        //跳转到主界面,tip登录成功
//                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(intent);
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }

                }
//                else{
//                    Toast.makeText(LoginActivity.this,"登录失败，用户不存在",Toast.LENGTH_SHORT).show();
//                }

            }
        });

        registerBtn=findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rememberOrNot();//是否记住密码

                User user=new User();
                user.setAccount(accountEdit.getText().toString());//将文本编辑框转化成字符串
                user.setPassword(passwordEdit.getText().toString());
                if (accountEdit!=null&&passwordEdit!=null){
                    user=new User();
                    user.setPassword(passwordEdit.getText().toString());
                    user.setAccount(accountEdit.getText().toString());
                    user.setRemember(true);
                    user.save();
                    Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(LoginActivity.this,user.getAccount(),Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);

            }
        });

    }

    //检查账号或者密码是否为空
    private String checkDataValid(String account,String password){
        if(TextUtils.isEmpty(account)|TextUtils.isEmpty(password))
            return "账号或者密码不能为空";
        return "";
    }


    public void rememberOrNot(){
        //boolean isRemember=pref.getBoolean("remember_password",false);
        String account=pref.getString("account","");
        String password=pref.getString("password","");
        if (!account.equals("")&&!password.equals("")){
            if (isRemember){//记住密码    将账户和密码复制到文本框
                accountEdit.setText(account);
                passwordEdit.setText(password);
                rememberPass.setChecked(true);
                // 查找本地数据库中是否已存在当前用户,不存在则新建用户并写入
                User user = DataSupport.where("account=?",account).findFirst(User.class);
                if (user==null){
                    user=new User();
                    user.setPassword(password);
                    user.setAccount(account);
                    user.setRemember(true);
                    user.save();
                }
            }
        }
    }





    public void register(){
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass=findViewById(R.id.remeber_pass);
        accountEdit=findViewById(R.id.account);
        passwordEdit=findViewById(R.id.password);
        boolean isRemember=pref.getBoolean("remember_password",false);
        //将账户和密码复制到文本框
        if (isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);

            }

        registerBtn=findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setAccount(accountEdit.getText().toString());//将文本编辑框转化成字符串
                user.setPassword(passwordEdit.getText().toString());
                //创建数据库
                LitePal.getDatabase();
                //跳转到主界面,tip注册成功
//                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            }
        });

        }


    public void login(){
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass=findViewById(R.id.remeber_pass);
        accountEdit=findViewById(R.id.account);
        passwordEdit=findViewById(R.id.password);
        boolean isRemember=pref.getBoolean("remember_password",false);
        //将账户和密码复制到文本框
        if (isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        loginBtn=findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //User user= new User();
                User user = DataSupport.where("account=?").findFirst(User.class);

                if (user!=null){
                    user.setAccount(accountEdit.getText().toString());//将文本编辑框转化成字符串
                    user.setPassword(passwordEdit.getText().toString());
                    //String password=passwordEdit.getText().toString();
                    //创建数据库
                    LitePal.getDatabase();
                    //跳转到主界面,tip登录成功
//                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败，用户不存在",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
