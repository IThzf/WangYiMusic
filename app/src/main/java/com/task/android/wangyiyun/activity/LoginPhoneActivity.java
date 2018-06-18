package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.task.android.wangyiyun.R;

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
    private MyDatabaseHelper dbHelper;
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
        //


       //每个程序都有自己的数据库，默认情况下是各自互相不干扰
        //创建一个数据库，并且打开
     /*   SQLiteDatabase db=openOrCreateDatabase("user.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists usertb(_id integer primary key autoincrement,phonumber integer not null,password text not null)");
        ContentValues values=new ContentValues();
        values.put("phonumber","12345678910");
        values.put("password","123456");
        db.insert("usertb",null,values);
        values.clear();
        db.close();*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=phoneEditText.getText().toString();
                password=passEditText.getText().toString();
                /*setEditable(phoneEditText,11,true);*/

                if(phoneNumber.length()<11 || phoneNumber.length()>11 ){
                    Toast toast=Toast.makeText(getApplicationContext(),"请输入11位数字的手机号",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(password.length()==0){
                    Toast toast=Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    //数据库验证
                    //创建或打开现有的数据库
                    dbHelper=new MyDatabaseHelper(LoginPhoneActivity.this,"userStore.db",null,1);
                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                   /* String tiaojian="phoNumber=? and password=?";*/
                    String [] selectionArgs={phoneNumber};
                    Cursor cursor=db.query("user",null,"phoNumber=?",selectionArgs,null,null,null);
                    if (cursor.moveToFirst()){
                        checkPassword=cursor.getString(cursor.getColumnIndex("password"));
                        if (password.equals(checkPassword)){
                            Intent intent=new Intent(LoginPhoneActivity.this,NavigationActivity.class);
                            startActivity(intent);
                        }else
                        {
                            Toast toast2=Toast.makeText(getApplicationContext(),"用户名或密码错误"+checkPassword+" "+password,Toast.LENGTH_SHORT);
                            toast2.show();
                            passEditText.setText("");
                        }
                    }else{
                        Toast toast=Toast.makeText(getApplicationContext(),"该手机号尚未注册，跳转至注册页面",Toast.LENGTH_SHORT);
                        toast.show();
                        delayTime();
                        Intent registerIntent=new Intent(LoginPhoneActivity.this,RegisterPhoneActivity.class);
                        registerIntent.putExtra("registerOrForget","手机号注册");
                        startActivity(registerIntent);
                    }

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
    private void delayTime(){
        //延时操作
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

            }
        };
        Timer timer=new Timer();
        timer.schedule(task,20000);
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
