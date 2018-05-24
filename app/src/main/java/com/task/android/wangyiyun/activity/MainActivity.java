package com.task.android.wangyiyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.adapter.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab yinyue;
    private TabLayout.Tab wangyiyun;
    private TabLayout.Tab friend;
    private Button genduo_bt;

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

        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_main);

        //初始化视图
        initViews();
    }

    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        yinyue = mTabLayout.getTabAt(0);
        wangyiyun = mTabLayout.getTabAt(1);
        friend = mTabLayout.getTabAt(2);


        //设置Tab的图标
        yinyue.setIcon(R.drawable.yinyue_fragment_selector);
        wangyiyun.setIcon(R.drawable.wangyiyun_fragment_selector);
        friend.setIcon(R.drawable.pengyou_fragment_selector);
        genduo_bt  = findViewById(R.id.genduo_main_bt);

        genduo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

            }
        });

        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        disableNavigationViewScrollbars(navigationView);
        headerView=navigationView.getHeaderView(0);//获得头部控件
//        button01=(Button)findViewById(R.id.tranTologin);
//        loginOutLinear=(LinearLayout)findViewById(R.id.loginOutLinear);
//        quitLinear=(LinearLayout) findViewById(R.id.quitLinear);

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

