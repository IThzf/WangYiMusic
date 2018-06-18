package com.task.android.wangyiyun.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.task.android.wangyiyun.R;

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
                   Toast toast=Toast.makeText(getApplicationContext(),"请输入11位数字的手机号",Toast.LENGTH_SHORT);
                   toast.show();
               }else if(setpassword.length()==0){
                   Toast toast=Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT);
                   toast.show();
               }else if(setpassword.length()<6){
                   Toast toast=Toast.makeText(getApplicationContext(),"请输入6位或以上的密码",Toast.LENGTH_SHORT);
                   toast.show();
               }else{
                   //验证码验证 需要手机号啧啧啧
                   //创建或打开现有的数据库
                   dbHelper=new MyDatabaseHelper(RegisterPhoneActivity.this,"userStore.db",null,1);
                   SQLiteDatabase db=dbHelper.getWritableDatabase();//返回一个SQLiteDatabase对象
                   ContentValues values=new ContentValues();
                   String [] selectionArgs={phoneNumber};
                   Cursor cursor=db.query("user",null,"phoNumber=?",selectionArgs,null,null,null);

                   if (registerOrForget.equals("手机号注册")){
                       if (cursor.moveToFirst()){
                           Toast toast=Toast.makeText(getApplicationContext(),"该手机号已注册,跳转至登录界面",Toast.LENGTH_SHORT);
                           toast.show();
                       }else{
                           values.put("phoNumber",phoneNumber);
                           values.put("password",setpassword);
                           db.insert("user",null,values);
                           Toast toast=Toast.makeText(getApplicationContext(),"手机号已成功注册,跳转至登录界面",Toast.LENGTH_SHORT);
                           toast.show();
                           delayTime();
                       }

                   }else if(registerOrForget.equals("忘记密码")){
                       if (!cursor.moveToFirst()){
                           Toast toast=Toast.makeText(getApplicationContext(),"该手机号尚未注册，跳转至注册页面",Toast.LENGTH_SHORT);
                           toast.show();
                           delayTime();
                           Intent registerIntent=new Intent(RegisterPhoneActivity.this,RegisterPhoneActivity.class);
                           registerIntent.putExtra("registerOrForget","手机号注册");
                           startActivity(registerIntent);
                       }else{
                           values.put("password",setpassword);
                           db.update("user",values,"phoNumber=?",new String[]{phoneNumber});
                           Toast toast=Toast.makeText(getApplicationContext(),"密码修改成功，跳转至登录界面",Toast.LENGTH_SHORT);
                           toast.show();
                       }
                   }
                   delayTime();
                   values.clear();
                   db.close();
                   Intent intent=new Intent(RegisterPhoneActivity.this,LoginPhoneActivity.class);
                   startActivity(intent);
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
