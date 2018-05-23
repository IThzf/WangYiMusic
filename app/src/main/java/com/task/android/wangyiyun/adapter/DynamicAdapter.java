package com.task.android.wangyiyun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.bean.Dynamic;

import java.util.List;

/**
 * Created by 韩振峰 on 2017/8/2.
 */


// DynamicRecyclerView 的适配器
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {
    private List<Dynamic> mlist;  // Dynamic列表
    private Context mcontext;  // 获取DynamicActivity对应的Context

    public DynamicAdapter(Context context, List<Dynamic> list){  // 有参构造器，初始化数据
        mcontext =context;
        mlist = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_userAvatar;  // 头像
        TextView tv_useerName;  // 昵称
        ImageView iv_dynamic_comment;  // 动态的内容  目前仅支持图片
        RelativeLayout rl_item_dynamic;  //  动态的 R.id
        LinearLayout ll_item_dynamic_praise;  // 赞区域
        LinearLayout ll_item_dynamic_share;  // 分享区域
        ImageView iv_item_dynamic_praise;  // 赞的图标
        TextView tv_item_dynamic_praise;  // 赞的数量
        TextView tv_item_dynamic_commentNumber;  // 评论的数量
        TextView tv_item_dynamic_shareNumber;  // 分享的数量


        public ViewHolder(View itemView) {
            super(itemView);
            // 对控件进行初始化
            iv_userAvatar = (ImageView) itemView.findViewById(R.id.item_dynamic_iv_userAvatar);
            tv_useerName = (TextView) itemView.findViewById(R.id.item_dynamic_tv_userName);
            iv_dynamic_comment = (ImageView) itemView.findViewById(R.id.dynamic_comment);
            ll_item_dynamic_praise = (LinearLayout) itemView.findViewById(R.id.item_dynamic_ll_praise);
            ll_item_dynamic_share = (LinearLayout) itemView.findViewById(R.id.item_dynamic_ll_share);
            iv_item_dynamic_praise = (ImageView) itemView.findViewById(R.id.item_dynamic_iv_praise);
            tv_item_dynamic_praise = (TextView) itemView.findViewById(R.id.item_dynamic_tv_praiseNumber);
            rl_item_dynamic = (RelativeLayout) itemView.findViewById(R.id.rl_item_dynamic);
            tv_item_dynamic_commentNumber = (TextView) itemView.findViewById(R.id.item_dynamic_tv_commentNumber);
            tv_item_dynamic_shareNumber = (TextView) itemView.findViewById(R.id.item_dynamic_tv_shareNumber);

            rl_item_dynamic.setOnClickListener(this);
            ll_item_dynamic_praise.setOnClickListener(this);
            ll_item_dynamic_share.setOnClickListener(this);
            iv_item_dynamic_praise.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != monItemClickListener){
                monItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendcircle_dynamic_rv,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dynamic dynamic = mlist.get(position);
        if (dynamic.getHasPraise()){
            Glide.with(mcontext).load(R.drawable.zan_red_selected).into(holder.iv_item_dynamic_praise);
//            holder.iv_item_dynamic_praise.setImageResource(R.drawable.zan_red_selected);
        } else {
            holder.iv_item_dynamic_praise.setImageResource(R.drawable.zan_gray_unselected);
        }
        Glide.with(mcontext).load(R.drawable.scene).into(holder.iv_dynamic_comment);

//        int userAvatarId = DynamicDetailActivity.getResources(dynamic.getUserAvatar());
        Glide.with(mcontext).load(R.drawable.wangyiiocn).into(holder.iv_userAvatar);
//        holder.iv_userAvatar.setImageResource(R.drawable.wangyiiocn);
        holder.tv_useerName.setText(dynamic.getUserID());
        holder.tv_item_dynamic_praise.setText(String.valueOf(dynamic.getPraiseNumber()));
        holder.tv_item_dynamic_commentNumber.setText(String.valueOf(dynamic.getCommentNumber()));
        holder.tv_item_dynamic_shareNumber.setText(String.valueOf(dynamic.getShareNumber()));

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    // recyclerView的点击接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private OnItemClickListener monItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }


}
