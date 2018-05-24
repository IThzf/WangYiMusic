package com.task.android.wangyiyun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.android.wangyiyun.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {
   private Context context;
   private List<String> data;
   public MusicListAdapter(Context context, List<String> data)
   {
       this.context=context;
       this.data=data;
   }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        holder = new MyViewHolder(LayoutInflater.from(
               context).inflate(R.layout.music_items, parent,
                false));


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
       TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);

            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != monItemClickListener){
                monItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    // recyclerView的点击接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private MusicListAdapter.OnItemClickListener monItemClickListener;

    public void setOnItemClickListener(MusicListAdapter.OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }


}
