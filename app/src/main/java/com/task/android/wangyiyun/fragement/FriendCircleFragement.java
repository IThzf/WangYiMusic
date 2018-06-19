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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.activity.DynamicDetailActivity;
import com.task.android.wangyiyun.adapter.DynamicAdapter;
import com.task.android.wangyiyun.bean.Dynamic;
import com.task.android.wangyiyun.util.DBManager;
import com.task.android.wangyiyun.util.DensityUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ArrayList<Dynamic> dynamicList;
    private View friend_view;
    private RecyclerView.LayoutManager layoutManager_rv;
    private Context context;
    private Boolean hasPraise = false; // 一条动态用户是否已赞


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

        fresh_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPage();
            }
        });  // 设置刷新按钮的刷新事件

    }


    private void initData() {
        initDynamicInfo();
        initdynamicList();  // 初始化动态的列表


    }

    private void initDynamicInfo() {

        dynamicList = new ArrayList<>();
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
                    dynamicList.add(dynamic);
                }



                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dynamicAdapter.notifyDataSetChanged();
                    }
                });


                // 从服务端加载测试数据
                DBManager manager = DBManager.createInstance();
                manager.connectDB();

                if (manager == null){
                    Log.i("FriendCircleFragement", "run: 数据库连接失败");
                    return;
                }

                String sql = "select * from dynamic";  // 查询Dynamic表
                ResultSet res = manager.executeQuery(sql);

                if (res == null){
                    Log.i("FriendCircleFragement", "run: 数据库连接失败2");
                    return;
                }
                try {
                    int index = 0;
                    while(res.next()){  // 判断结果集中是否还有数据，并将指针下移
                        Log.i("FriendCircleFragement","  Dynamic开始添加");
                        // 从Res结果集中读取Dynamic具体信息
                        dynamic = new Dynamic();
                        dynamic.setDID(res.getInt("DID"));
                        dynamic.setUserID(res.getString("userID"));
                        dynamic.setPraiseNumber(res.getInt("praiseNumber"));
                        dynamic.setCommentNumber(res.getInt("commentNumber"));
                        dynamic.setShareNumber(res.getInt("shareNumber"));

                        // 从数据库中读取此Dynamic作者的头像
                        String userAvatarSql = "select userAvatar from user where ID in " +
                                "(select userID from dynamic where DID = "+ res.getInt("DID") + " )";

                        ResultSet userAvatarRes = manager.executeQuery(userAvatarSql);
                        if (userAvatarRes.next()){
                            dynamic.setUserAvatar(userAvatarRes.getString("userAvatar"));
                        }else{
                            dynamic.setUserAvatar("drawable/wangyiiocn");
                        }

                        Log.i("FriendCircleFragement"," " + res);
//                        int dynamic_ID = res.getInt("DID");
                        // 从数据库中判断此Dynamic是否已经被登陆者赞了
                        if (userID != null){  // 用户已登录
                            String hasPraiseSql = "select count(*) from praise where DID = " + dynamic.getDID() + " and praiseID = " + userID;

                            ResultSet hasPraiseRes = manager.executeQuery(hasPraiseSql);
                            if (hasPraiseRes.next()){
                                int num = hasPraiseRes.getInt(1);
                                if (num == 1){
                                    hasPraise = true;
                                }else{
                                    hasPraise = false;
                                }
                                dynamic.setHasPraise(hasPraise);
                            }
                        }else{ // 游客状态
                            dynamic.setHasPraise(false);
                        }

                        dynamicList.set(index,dynamic);   // 把得到的Dyanmic对象加入到Dynamic列表中
                        index ++;
                        Log.i("FriendCircleFragement","  Dynamic添加成功");

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    res.close();
                    manager.closeDB();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        dynamicAdapter = new DynamicAdapter(getActivity(), dynamicList);
//        message = singAdapter.handler.obtainMessage();
        dynamic_rv.setAdapter(dynamicAdapter);
        // 给每个item添加分割线
        dynamic_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // 设置item增加和移除的动画
        dynamic_rv.setItemAnimator(new DefaultItemAnimator());
        ((SimpleItemAnimator)dynamic_rv.getItemAnimator()).setSupportsChangeAnimations(false);

        // 设置recylcerView的点击事件
        dynamicAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //创建一个Intent储存此Dynamic的信息，以方便跳转后的页面进行初始化
                Intent intent = new Intent(getActivity(),DynamicDetailActivity.class);
                intent.putExtra("dynamicInfo",dynamicList.get(position).getDID());
                intent.putExtra("dynamicPraiseNumber",dynamicList.get(position).getPraiseNumber());
                intent.putExtra("dynamicCommentNumber",dynamicList.get(position).getCommentNumber());
                startActivity(intent);  // 点击Dynamic是跳转到Dynamic的详细页面，包含Dynamic内容，点赞列表，评论列表
            }
        });
    }
    // 刷新数据
    private void refreshPage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);  // 刷新时睡眠1000ms，用来模拟从服务器加载数据
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dynamicAdapter.notifyDataSetChanged(); // 更新成功后刷新页面
                        fresh_srl.setRefreshing(false);  // 刷新中止
                        Toast toast = Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, DensityUtil.px2dip(getActivity(), 120));
//                        Log.i("Toast", + "");
                        Log.i("SCENE", R.drawable.scene + "");
                        toast.show(); // 提示用户刷新成功
                    }
                });
            }
        }).start();
    }
}
