package com.task.android.wangyiyun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.bean.Wangyiyun_yinyue_listview_information;

import java.util.List;

/**
 * Created by 刘永飞 on 2018/5/15.
 */

public class Listview_information_adapter extends ArrayAdapter<Wangyiyun_yinyue_listview_information> {
    private int resourceId;

    public Listview_information_adapter(Context context, int textViewResourceId, List<Wangyiyun_yinyue_listview_information> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Wangyiyun_yinyue_listview_information information = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.wangyiyun_tubiao);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item_information1);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.item_information2);
            view.setTag(viewHolder);

        } else {
            view = convertView;

            viewHolder = (ViewHolder) view.getTag();
        }



        viewHolder.imageView.setImageResource(information.getImageId());
        viewHolder.textView1.setText(information.getStr1());
        viewHolder.textView2.setText(information.getStr2());

        return view;
    }
    public int getCount() {

        int size = 5;
        if(size > 0)

            return size >= 5 ? 5 : size;
        else

            return 0;
    }


    class ViewHolder {
        ImageView imageView;
        TextView textView1, textView2;
    }

}

