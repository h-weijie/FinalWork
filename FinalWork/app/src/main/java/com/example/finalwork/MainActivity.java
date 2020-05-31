package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyItemClickListener {
    private List<Video> videos;
    private ViewPager2 viewPager2;
    private MyAdapter.MyItemClickListener listener;
    private Context context;
    private int position;
    private TextView capture;
    private static final int REQUEST_CODE = 233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2=findViewById(R.id.videoList);
        capture=findViewById(R.id.nav3);
        listener=this;
        context=this;
        if(savedInstanceState!=null){
            position=savedInstanceState.getInt("position");
        }else{
            position=0;
        }
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);//调起系统相机
                startActivityForResult(intent,REQUEST_CODE);//等待返回结果
            }
        });

        Retrofit retrofit=new Retrofit.Builder()//实例化Retrofit
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())//设置Gson的数据解析器
                .build();
        ApiService apiService=retrofit.create(ApiService.class);//通过Retrofit实例创建接口服务对象
        //接口服务对象调用接口中的方法，获取Call对象，接着Call对象执行请求
        apiService.getVideos().enqueue(new Callback<Video[]>() {
            @Override
            public void onResponse(Call<Video[]> call, Response<Video[]> response) {
                if(response.body()!=null){
                    //获取数据成功时将Video数组转成Video列表，方便处理
                    videos = new ArrayList<Video>(Arrays.asList(response.body()));
                    //将Video列表传给viewpage2
                    viewPager2.setAdapter(new MyAdapter(videos,listener,context));
                    //设置viewpage2的item为之前的item
                    viewPager2.setCurrentItem(position,false);
                }else{
                    Toast.makeText(MainActivity.this,"数据为空",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            //视频录制成功后跳转到播放界面进行播放
            Uri uri=data.getData();
            Intent intent=new Intent(this,VideoPlayer.class);
            intent.putExtra("url",uri.toString());
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,"视频录制失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(String url,int pos) {
        position=pos;
        Intent intent=new Intent(this,VideoPlayer.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position);
    }
}
