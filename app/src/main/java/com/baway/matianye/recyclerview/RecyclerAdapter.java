package com.baway.matianye.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by matianye .
 * on 2017/8/10:20.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private Context context;
    private List<Data.DataBean.ReturnDataBean.ComicsBean> list;
    private OnItemClickListener itemClickListener;
    public RecyclerAdapter(Context context, List<Data.DataBean.ReturnDataBean.ComicsBean> list) {
        this.context = context;
        this.list = list;
    }
    public void SetonitemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder hodler = new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_recycler,parent,false));
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getCover()).into(holder.imageView);
        holder.title.setText(list.get(position).getName());
        holder.modle.setText(list.get(position).getTags().get(0)+"  "+list.get(position).getTags().get(1));
        holder.content.setText(list.get(position).getDescription());
        Log.e("holder", "onBindViewHolder: "+list.get(position).getDescription());
        String i = list.get(position).getConTag();
        int a = Integer.parseInt(i);
        if(a>10000){
            char c =i.charAt(0);
            char c1 = i.charAt(1);
            holder.poll.setText(c+"."+c1+"万");
        }else{
            holder.poll.setText(list.get(position).getConTag());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,modle,content,poll;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_PhotosView_img);
            title = itemView.findViewById(R.id.item_introduce_title);
            modle = itemView.findViewById(R.id.item_introduce_modle);
            content = itemView.findViewById(R.id.item_introduce_content);
            poll = itemView.findViewById(R.id.item_introduce_poll);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View var2,int var3);
    }
}