package com.task.android.wangyiyun.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.adapter.MyFragmentPageAdapter;

public class MusicListActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPageAdapter myFragmentPagerAdapter;



    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    private Button back_MusicList_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);
        initViews();

    }
    private void initViews(){



        mViewPager=(ViewPager)findViewById(R.id.viewPager_MusicList);
        back_MusicList_btn = findViewById(R.id.back_MusicList_btn);
        myFragmentPagerAdapter=new MyFragmentPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        mTabLayout=(TabLayout)findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);

        back_MusicList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

}
