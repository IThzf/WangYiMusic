package com.task.android.wangyiyun.fragement;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private String[] data1 = {"本地音乐", "最近播放", "下载管理", "我的电台", "我的收藏"};
    private String[] data2 = {"(0)", "(0)", "(0)", "(0)", "(0)"};
    private int[] tubiao = {R.drawable.yinyue3, R.drawable.bofang1, R.drawable.xiazai1, R.drawable.diantai1, R.drawable.shoucang1};
    List<Wangyiyun_yinyue_listview_information> boylist = new ArrayList<>();

    //Model：定义的数据
    private ExpandableListView expandableListView;

    private List<String> group_list;

    private List<String> item_lt;

    private List<List<String>> item_list;

    private List<List<Integer>> item_list2;

    private List<List<Integer>> gr_list2;

    private MyExpandableListViewAdapter adapter;
    private static final String TAG = "YinyueFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inititem();

        View view = inflater.inflate(R.layout.fragment_yinyue, container, false);

        final ListView listView = view.findViewById(R.id.yinyue_fragment_lv);
        final Listview_information_adapter adapter = new Listview_information_adapter(getContext(), R.layout.yinyue_listview_item, boylist);

        listView.setAdapter((ListAdapter) adapter);
        final Context mContext = getActivity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Log.i(TAG, "onItemClick: ");
                    Intent intent_musicList = new Intent(mContext, MusicListActivity.class);
                    Log.i(TAG, "onItemClick2: " + intent_musicList);
                    startActivity(intent_musicList);
//                    if (intent_musicList != null){
//                        startActivity(intent_musicList);
//                    }
//
//                    Log.i(TAG, "onItemClick3: ");
                }
            }
        });
        group_list = new ArrayList<String>();
        group_list.add("创建的歌单(1)");

        item_lt = new ArrayList<String>();
        item_lt.add("我喜欢的音乐");


        item_list = new ArrayList<List<String>>();
        item_list.add(item_lt);


        List<Integer> tmp_list = new ArrayList<Integer>();
        tmp_list.add(R.drawable.xihuan1);


        item_list2 = new ArrayList<List<Integer>>();
        item_list2.add(tmp_list);


        List<Integer> gr_list = new ArrayList<Integer>();
        gr_list.add(R.drawable.shezhi2);

        gr_list2 = new ArrayList<List<Integer>>();
        gr_list2.add(gr_list);


        expandableListView = (ExpandableListView)view.findViewById(R.id.yinyue_frament_expandableListView);
        expandableListView.setGroupIndicator(null);

        // 监听组点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (item_list.get(groupPosition).isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                return false;
            }
        });

        expandableListView.setAdapter(new MyExpandableListViewAdapter(getContext()));



        return view;
    }


    private void inititem() {

        for (int i = 0; i < 5; i++) {

            Wangyiyun_yinyue_listview_information information = new Wangyiyun_yinyue_listview_information(tubiao[i], data1[i], data2[i]);
            boylist.add(information);
        }

    }




    class MyExpandableListViewAdapter extends BaseExpandableListAdapter
    {

        private Context context;

        public MyExpandableListViewAdapter(Context context)
        {
            this.context = context;
        }

        /**
         *
         * 获取组的个数
         *
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupCount()
         */
        @Override
        public int getGroupCount()
        {
            return group_list.size();
        }

        /**
         *
         * 获取指定组中的子元素个数
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
         */
        @Override
        public int getChildrenCount(int groupPosition)
        {
            return item_list.get(groupPosition).size();
        }

        /**
         *
         * 获取指定组中的数据
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroup(int)
         */
        @Override
        public Object getGroup(int groupPosition)
        {
            return group_list.get(groupPosition);
        }

        /**
         *
         * 获取指定组中的指定子元素数据。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChild(int, int)
         */
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return item_list.get(groupPosition).get(childPosition);
        }

        /**
         *
         * 获取指定组的ID，这个组ID必须是唯一的
         *
         * @param groupPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupId(int)
         */
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        /**
         *
         * 获取指定组中的指定子元素ID
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#getChildId(int, int)
         */
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        /**
         *
         * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
         *
         * @return
         * @see android.widget.ExpandableListAdapter#hasStableIds()
         */
        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        /**
         *
         * 获取显示指定组的视图对象
         *
         * @param groupPosition 组位置
         * @param isExpanded 该组是展开状态还是伸缩状态
         * @param convertView 重用已有的视图对象
         * @param parent 返回的视图对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
         *      android.view.ViewGroup)
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            GroupHolder groupHolder = null;

            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_group, null);

                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView)convertView.findViewById(R.id.txt);
                groupHolder.img = (ImageView)convertView.findViewById(R.id.img);



                convertView.setTag(groupHolder);
            }
            else
            {
                groupHolder = (GroupHolder)convertView.getTag();
            }

            if (!isExpanded)
            {
                groupHolder.img.setBackgroundResource(R.drawable.jiantouyou);
            }
            else
            {
                groupHolder.img.setBackgroundResource(R.drawable.jiantouxia);
            }

            groupHolder.txt.setText(group_list.get(groupPosition));

            return convertView;
        }

        /**
         *
         * 获取一个视图对象，显示指定组中的指定子元素数据。
         *
         * @param groupPosition 组位置
         * @param childPosition 子元素位置
         * @param isLastChild 子元素是否处于组中的最后一个
         * @param convertView 重用已有的视图(View)对象
         * @param parent 返回的视图(View)对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
         *      android.view.ViewGroup)
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            ItemHolder itemHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);
                itemHolder.img = (ImageView)convertView.findViewById(R.id.img);

                convertView.setTag(itemHolder);
            }
            else
            {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));
            itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
            return convertView;
        }

        /**
         *
         * 是否选中指定位置上的子元素。
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

    }

    class GroupHolder
    {
        public TextView txt;

        public ImageView img;


    }

    class ItemHolder
    {
        public ImageView img;

        public TextView txt;

    }

}

