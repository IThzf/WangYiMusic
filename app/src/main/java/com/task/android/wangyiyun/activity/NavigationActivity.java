package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.task.android.wangyiyun.R;

//https://blog.csdn.net/qq_28468727/article/details/52958735
public class NavigationActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private View headerView;
    private ImageView imageView;
    private Button button01;
    private MyDatabaseHelper dbHelper;
    private LinearLayout settingLinear;
    private LinearLayout loginOutLinear;
    private LinearLayout quitLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        disableNavigationViewScrollbars(navigationView);
        headerView=navigationView.getHeaderView(0);//获得头部控件
        button01=(Button)findViewById(R.id.tranTologin);
        loginOutLinear=(LinearLayout)findViewById(R.id.loginOutLinear);
        quitLinear=(LinearLayout) findViewById(R.id.quitLinear);


        //创建或打开现有的数据库
        dbHelper=new MyDatabaseHelper(this,"userStore.db",null,1);
        Button createDatabase=(Button)findViewById(R.id.cdb);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        //退出账户
        loginOutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavigationActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        //返回home界面
        quitLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                *//**//*mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);*//**//*
                mHomeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mHomeIntent);*/
                finish();
            }
        });



        //item点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Toast toast=Toast.makeText(getApplicationContext(),"你好啊",Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavigationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



    }

    //解决侧滑菜单的滚动条问题，调用disableNavigationViewScrollbars()函数
    private void disableNavigationViewScrollbars(NavigationView navigationView)
    {
        if (navigationView!=null){
            NavigationMenuView navigationMenuView=(NavigationMenuView)navigationView.getChildAt(0);
            if (navigationMenuView!=null){
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
    //类似home键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
