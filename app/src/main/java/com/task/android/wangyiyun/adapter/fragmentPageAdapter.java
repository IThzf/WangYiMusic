package com.task.android.wangyiyun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.task.android.wangyiyun.fragement.Album_Fragment;
import com.task.android.wangyiyun.fragement.Folder_Fragment;
import com.task.android.wangyiyun.fragement.Singer_Fragment;
import com.task.android.wangyiyun.fragement.SingleMusicFragment;

/**
 * Created by Administrator on 2018/5/23.
 */

public class fragmentPageAdapter extends FragmentPagerAdapter{
    private String[] mTitles = new String[]{"单曲", "歌手", "专辑","文件夹"};

    public fragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {

            return new Singer_Fragment();
        } else if (position == 2) {
            return new Album_Fragment();
        }else if (position==3){
            return new Folder_Fragment();
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
