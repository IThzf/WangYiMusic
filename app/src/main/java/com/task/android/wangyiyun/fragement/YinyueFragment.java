package com.task.android.wangyiyun.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.activity.MusicListActivity;
import com.task.android.wangyiyun.adapter.Listview_information_adapter;
import com.task.android.wangyiyun.bean.Wangyiyun_yinyue_listview_information;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YinyueFragment extends Fragment {
    private String[] data1={"本地音乐","最近播放","下载管理","我的电台","我的收藏"};
    private String[] data2={"(0)","(0)","(0)","(0)","(0)"};
    private int []tubiao={R.drawable.yinyue3,R.drawable.bofang1,R.drawable.xiazai1,R.drawable.diantai1,R.drawable.shoucang1};
    List<Wangyiyun_yinyue_listview_information> boylist=new ArrayList<>();
    private ExpandableListView expandableListView;

    //Model：定义的数据
    private String[] groups = {">创建的歌单"};


    private String[][] childs = {{"我最喜欢的音乐"}};
    private String[][] childs2 = {{"0首"}};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inititem();

        View view = inflater.inflate(R.layout.fragment_yinyue, container,false);
        final ListView listView=view.findViewById(R.id.yinyue_fragment_lv);
        final Listview_information_adapter adapter=new Listview_information_adapter(getContext(),R.layout.yinyue_listview_item,boylist);

        listView.setAdapter((ListAdapter) adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent_musicList = new Intent(getActivity(), MusicListActivity.class);
                    startActivity(intent_musicList);
                }
            }
        });
        expandableListView = (ExpandableListView)view.findViewById(R.id.yinyue_frament_expandableListView);

        expandableListView.setAdapter(new MyExpandableListView_adapter());



        return view;
    }



    private void inititem() {

        for (int i = 0; i < 5; i++)
        {

            Wangyiyun_yinyue_listview_information information=new Wangyiyun_yinyue_listview_information(tubiao[i],data1[i],data2[i]);
            boylist.add(information);
        }

    }
    class MyExpandableListView_adapter extends BaseExpandableListAdapter {
        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            return childs[groupPosition].length;
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
            } else {

            }
            TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            tv_group.setText(groups[groupPosition]);
            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
            }

            ImageView iv_child = (ImageView) convertView.findViewById(R.id.iv_child);
            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            TextView tv_child2 = (TextView) convertView.findViewById(R.id.tv_child2);

            tv_child.setText(childs[groupPosition][childPosition]);
            tv_child2.setText(childs2[groupPosition][childPosition]);
            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


}
