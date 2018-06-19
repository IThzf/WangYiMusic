package com.task.android.wangyiyun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.bean.MusicMedia;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/21.
 */

public class MusicItemsAdapter extends RecyclerView.Adapter<MusicItemsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MusicMedia> musicList;
    public MusicItemsAdapter(Context context, ArrayList<MusicMedia> musicList)
    {
        this.context=context;
        this.musicList= musicList;
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
        holder.music_title.setText(musicList.get(position).getTitle());
        holder.music_artist.setText(musicList.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView music_title;
        TextView music_artist;
        LinearLayout music_item_ll;

        public MyViewHolder(View view)
        {
            super(view);
            music_title=(TextView) view.findViewById(R.id.music_title);
            music_artist=(TextView) view.findViewById(R.id.music_artist);
            music_item_ll = view.findViewById(R.id.music_item_ll);

            music_item_ll.setOnClickListener(this);
        }
        public  void onClick(View view){
            if(null!=monItemClickListener){
                monItemClickListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface  OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private  MusicItemsAdapter.OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(MusicItemsAdapter.OnItemClickListener onItemClickListener){
        this.monItemClickListener=onItemClickListener;
    }

}
