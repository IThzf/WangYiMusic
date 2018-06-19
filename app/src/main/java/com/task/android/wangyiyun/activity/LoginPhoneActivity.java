package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.fragement.FriendCircleFragement;
import com.task.android.wangyiyun.util.DBManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xue on 2018/5/16.
 */

public class LoginPhoneActivity extends AppCompatActivity {
    private Button backButtonLogin;
    private Button loginButton;
    private EditText phoneEditText;
    private EditText passEditText;
    private String phoneNumber;
    private String password;
    private String checkPassword;
    private TextView forgetPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phonum_layout);

        //实例化
        backButtonLogin=(Button)findViewById(R.id.backButtonLogin);
        loginButton=(Button)findViewById(R.id.loginButton);
        phoneEditText=(EditText)findViewById(R.id.phoneEditText);
        passEditText=(EditText)findViewById(R.id.passEditText);
        forgetPassword=(TextView)findViewById(R.id.forgetPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=phoneEditText.getText().toString();
                password=passEditText.getText().toString();
                /*setEditable(phoneEditText,11,true);*/

                if(phoneNumber.length()<11 || phoneNumber.length()>11 ){
                    Toast toast= Toast.makeText(getApplicationContext(),"请输入11位数字的手机号", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(password.length()==0){
                    Toast toast= Toast.makeText(getApplicationContext(),"请输入密码", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {

                    login();

                }

            }
        });


        //忘记密码
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPhoneActivity.this,RegisterPhoneActivity.class);
                intent.putExtra("registerOrForget","忘记密码");
                startActivity(intent);
            }
        });
        //监听回退事件
        backButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 建立数据库连接
                    DBManager manager = DBManager.createInstance();
                    Connection conn = manager.getConnection();

                    Statement sql = conn.createStatement();
                    ResultSet res = sql.executeQuery("select * from User");

                    String ID = phoneEditText.getText().toString();
                    String Password = passEditText.getText().toString();

                    String id = "";
                    String password = "";

                    boolean flag = true;
                    while (res.next()){
                        id = res.getString("ID");
                        password = res.getString("password");

                        if (ID.equals(id)){
                            if (Password.equals(password)){
                                toast(1);
                            } else {
                                toast(0);
                            }

                            flag = false;
                        }
                    }

                    if (flag){
                        toast(0);
                    }

                    conn.close();
                    sql.close();
                    res.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void toast(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 0){
                    Toast.makeText(LoginPhoneActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
                } else if (type == 1){
                    Toast.makeText(LoginPhoneActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPhoneActivity.this,MainActivity.class);
                    intent.putExtra("userID",phoneEditText.getText().toString());  // 向登录后的页面发送userId，用来获取用户的头像以及点赞等信息
                    intent.putExtra("fromLogin","login");
                    FriendCircleFragement.userID = phoneEditText.getText().toString();
                    setResult(RESULT_OK,intent);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void delayTime(){
        //延时操作
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

            }
        };
        Timer timer=new Timer();
        timer.schedule(task,2000);
    }

    //实现当输入到最大值时则不允许再输入 没看懂
    /*private void setEditable(EditText editText,int maxLength,boolean value){
        if(value){
            editText.setFilters(new InputFilter[]{ new MyEditFilter(maxLength) });
            editText.setCursorVisible(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }else{
            editText.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    return source.length()<1?dest.subSequence(dstart,end):"";
                }
            }});
            editText.setCursorVisible(false);
            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
        }
    }*/


}
