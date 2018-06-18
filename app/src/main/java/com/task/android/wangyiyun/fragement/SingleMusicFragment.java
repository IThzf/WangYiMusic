package com.task.android.wangyiyun.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.activity.PlayMusicActivity;
import com.task.android.wangyiyun.adapter.MusicListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SingleMusicFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private MusicListAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.single_music,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        Log.i("TAG", "initViews: " + mRecyclerView);
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MusicListAdapter(getActivity(),mDatas );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent_PlayMusic = new Intent(getActivity(), PlayMusicActivity.class);
                startActivity(intent_PlayMusic);
            }
        });
        return view ;
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
}
