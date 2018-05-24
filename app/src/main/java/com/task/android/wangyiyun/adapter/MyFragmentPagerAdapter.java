package com.task.android.wangyiyun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.task.android.wangyiyun.fragement.FriendCircleFragement;
import com.task.android.wangyiyun.fragement.PengyouFragment;
import com.task.android.wangyiyun.fragement.WangyiyunFragment;
import com.task.android.wangyiyun.fragement.YinyueFragment;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"", "", "",};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new WangyiyunFragment();
        } else if (position == 2) {
            return new FriendCircleFragement();
        }
        return new YinyueFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
