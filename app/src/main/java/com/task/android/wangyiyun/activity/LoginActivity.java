package com.task.android.wangyiyun.activity;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.task.android.wangyiyun.R;

/**
 * Created by xue on 2018/5/14.
 */

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button regiserButton;
    private TextView textView01;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginButton=(Button)findViewById(R.id.login);
        regiserButton=(Button)findViewById(R.id.register);
        textView01=(TextView)findViewById(R.id.tryText01);
        //添加下划线
        textView01.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


         loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(LoginActivity.this,LoginPhoneActivity.class);
                startActivity(loginIntent);
            }
        });

          regiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterPhoneActivity.class);
                registerIntent.putExtra("registerOrForget","手机号注册");
                startActivity(registerIntent);
            }
        });
          textView01.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                  startActivity(intent);
              }
          });
    }

}
