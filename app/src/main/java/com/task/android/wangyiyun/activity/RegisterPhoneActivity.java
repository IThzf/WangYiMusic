package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.fragement.FriendCircleFragement;
import com.task.android.wangyiyun.util.DBManager;
import com.task.android.wangyiyun.util.MyDatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by xue on 2018/5/16.
 */

public class RegisterPhoneActivity extends AppCompatActivity {
    private Button backButtonRegister;
    private Button nextStepRegister;
    private EditText editPhoRegister;
    private EditText editSetpassRegister;
    private String phoneNumber;
    private String setpassword;
    private TextView registerOrChange;
    private String registerOrForget;
    //
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phonumber_layout);
        registerOrForget=this.getIntent().getStringExtra("registerOrForget");


        backButtonRegister=(Button)findViewById(R.id.backButtonRegister);
        nextStepRegister=(Button)findViewById(R.id.nextStepRegister);
        editPhoRegister=(EditText) findViewById(R.id.editPhoRegister);
        editSetpassRegister=(EditText) findViewById(R.id.editSetpassRegister);
        registerOrChange=(TextView)findViewById(R.id.registerOrChange);
        registerOrChange.setText(registerOrForget);

        nextStepRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               phoneNumber=editPhoRegister.getText().toString();
               setpassword=editSetpassRegister.getText().toString();
                /*setEditable(phoneEditText,11,true);*/

               if(phoneNumber.length()<11 || phoneNumber.length()>11 ){
                   Toast toast= Toast.makeText(getApplicationContext(),"请输入11位数字的手机号", Toast.LENGTH_SHORT);
                   toast.show();
               }else if(setpassword.length()==0){
                   Toast toast= Toast.makeText(getApplicationContext(),"请输入密码", Toast.LENGTH_SHORT);
                   toast.show();
               }else if(setpassword.length()<6){
                   Toast toast= Toast.makeText(getApplicationContext(),"请输入6位或以上的密码", Toast.LENGTH_SHORT);
                   toast.show();
               }else{
                   //验证码验证 需要手机号
                   //创建或打开现有的数据库
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           try {

                               DBManager manager = DBManager.createInstance();
                               Connection con = manager.getConnection();
                               Statement sql = con.createStatement();
                               ResultSet res = sql.executeQuery("select * from User where ID = " + phoneNumber);
                               String id = "";
                               String password = "";
                               if (res.next()){
                                   Log.i("TAG", "该账号已存在");
                                   toast(0);
                                   return;
                               }

                               PreparedStatement ps = con.prepareStatement("insert into User(ID,password,name) values (?,?,?)");
                               ps.setString(1, phoneNumber);
                               ps.setString(2, setpassword);
                               ps.setString(3,"用户" + phoneNumber);
                               ps.executeUpdate();
                               toast(1);
                               FriendCircleFragement.userID = phoneNumber;

                               con.close();
                               sql.close();
                               res.close();

                           } catch (SQLException e) {
                               e.printStackTrace();
                           }
                       }
                   }).start();
               }
           }
        });

        //监听回退事件
        backButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toast(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == 0){
                    Toast.makeText(RegisterPhoneActivity.this,"该账号已注册", Toast.LENGTH_SHORT).show();
                } else if (type == 1){
                    Toast.makeText(RegisterPhoneActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(RegisterPhoneActivity.this,MainActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        });
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
    private void delayTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
            }
        }, 3000);//3秒后执行Runnable中的run方法
    }

}
