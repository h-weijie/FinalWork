package com.example.finalwork;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

//自定义的适配器
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Video> items;
    private MyItemClickListener myItemClickListener;
    private Context context;
    public MyAdapter(List<Video> videos,MyItemClickListener listener,Context mContext){
        items= videos;
        myItemClickListener=listener;
        context=mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染相应item
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //为item绑定数据
        holder.bind(items.get(position),position,context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface MyItemClickListener{
        public void onItemClick(String url,int pos);
    }

    //自定义item
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private  final View view;
        private final ImageView titlePage;
        private final ImageView avatar;
        private final TextView video_id;
        private final TextView name_des;
        private String url;
        private int pos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            titlePage=itemView.findViewById(R.id.titlePage);
            avatar=itemView.findViewById(R.id.avatar);
            video_id=itemView.findViewById(R.id.video_id);
            name_des=itemView.findViewById(R.id.name_des);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener!=null){
                myItemClickListener.onItemClick(url,pos);
            }
        }


        public void bind(Video video, int position, Context context) {
            url=video.feedurl;
            pos=position;
            //绑定封面
            Glide.with(context)
                    .load(video.feedurl)
                    .into(titlePage);
            //绑定头像
            Glide.with(context)
                    .load(video.avatar)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(avatar);
            //绑定id
            video_id.setText("ID:"+video._id);
            //绑定昵称与视频描述
            String id="@"+ video.nickname+"\n";
            SpannableString name_des_str=new SpannableString(id+ video.description);
            name_des_str.setSpan(new RelativeSizeSpan(1.1f),0,id.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_des_str.setSpan(new StyleSpan(Typeface.BOLD),0,id.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            name_des.setText(name_des_str);
        }
    }
}
