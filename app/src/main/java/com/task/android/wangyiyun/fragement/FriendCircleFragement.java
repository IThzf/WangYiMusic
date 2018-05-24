package com.task.android.wangyiyun.fragement;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.activity.DynamicDetailActivity;
import com.task.android.wangyiyun.adapter.DynamicAdapter;
import com.task.android.wangyiyun.bean.Dynamic;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendCircleFragement extends Fragment {

    public static String userID = null;
    private RecyclerView dynamic_rv;
    private SwipeRefreshLayout fresh_srl;
    private FloatingActionButton addDynamic_fab;
    private DynamicAdapter dynamicAdapter;
    private ArrayList<Dynamic> dynamicsList;
    private View friend_view;
    private RecyclerView.LayoutManager layoutManager_rv;
    private Context context;


    public FriendCircleFragement() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        friend_view  = inflater.inflate(R.layout.fragment_friend_circle, container, false);

        initView();
        initData();

        return friend_view;
    }



    private void initView() {
        fresh_srl = friend_view.findViewById(R.id.fresh_firendcircle_srl);
        dynamic_rv = friend_view.findViewById(R.id.dynamic_friendcircle_rv);
        addDynamic_fab = friend_view.findViewById(R.id.addDynamic_friendcircle_fab);

    }


    private void initData() {
        initDynamicInfo();
        initdynamicList();  // 初始化动态的列表


    }

    private void initDynamicInfo() {

        dynamicsList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Dynamic dynamic;  // 模拟从服务端加载数据
                for (int i=0; i<10;i++){
                    dynamic = new Dynamic();
                    dynamic.setDID(i);
                    dynamic.setUserID("15836889617");
                    if (i == 2){
                        dynamic.setPraiseNumber(0);
                        dynamic.setCommentNumber(0);
                    }else {
                        dynamic.setPraiseNumber(666);
                        dynamic.setCommentNumber(666);
                    }

                    dynamic.setShareNumber(666);
                    dynamicsList.add(dynamic);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dynamicAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void initdynamicList() {
        layoutManager_rv = new LinearLayoutManager(getActivity());  // RecyclerView的布局样式为LinearLayout
        dynamic_rv.setLayoutManager(layoutManager_rv);
        dynamicAdapter = new DynamicAdapter(getActivity(),dynamicsList);
//        message = singAdapter.handler.obtainMessage();
        dynamic_rv.setAdapter(dynamicAdapter);
        // 给每个item添加分割线
        dynamic_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // 设置item增加和移除的动画
        dynamic_rv.setItemAnimator(new DefaultItemAnimator());
        ((SimpleItemAnimator)dynamic_rv.getItemAnimator()).setSupportsChangeAnimations(false);

        dynamicAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent_DynamicDetail = new Intent(getActivity(), DynamicDetailActivity.class);
                startActivity(intent_DynamicDetail);
            }
        });
    }
}
