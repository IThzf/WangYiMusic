package com.task.android.wangyiyun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.task.android.wangyiyun.fragement.AlbumFragment;
import com.task.android.wangyiyun.fragement.FolderFragment;
import com.task.android.wangyiyun.fragement.SingerFragment;
import com.task.android.wangyiyun.fragement.SingleMusicFragment;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"单曲", "歌手", "专辑","文件夹"};

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new SingerFragment();
        } else if (position == 2) {
            return new AlbumFragment();
        }else if (position==3){
            return new FolderFragment();
        }
        return new SingleMusicFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}
