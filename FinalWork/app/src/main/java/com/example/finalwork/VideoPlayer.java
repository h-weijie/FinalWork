package com.example.finalwork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class VideoPlayer extends AppCompatActivity {
    private Love heart;
    private VideoView videoView;
    private ImageView pause;
    private GestureDetector mGesture;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        videoView=findViewById(R.id.videoPlayer);
        heart=findViewById(R.id.heart);
        pause=findViewById(R.id.pause);
        Glide.with(this)
                .load(R.mipmap.pause)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(pause);

        //将触屏事件转发给mGesture
        heart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGesture.onTouchEvent(event);
                return true;
            }
        });
        //mGesture识别单双击事件
        mGesture =new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //单击控制视频的播放与暂停
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pause.setVisibility(View.VISIBLE);
                } else {
                    videoView.start();
                    pause.setVisibility(View.INVISIBLE);
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //双击出现点赞爱心特效
                heart.addheart(e);
                return true;
            }
        });

        //获取url
        Uri url;
        Intent intent=getIntent();
        if(intent.hasExtra("url")){
            url=Uri.parse(intent.getStringExtra("url"));
            videoView.setVideoURI(url);//设置视频的url
        }else{
            Toast.makeText(this,"视频播放失败",Toast.LENGTH_SHORT).show();
        }
        videoView.start();//自动播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pause.setVisibility(View.VISIBLE);
            }
        });
    }
}
