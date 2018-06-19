package com.task.android.wangyiyun.fragement;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.android.wangyiyun.R;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SingerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.singer,container,false);
    }
}
