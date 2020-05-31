package com.example.finalwork;

import retrofit2.Call;
import retrofit2.http.GET;

//retrofit接口
public interface ApiService {
    //发送GET请求，将返回的数据转换成Video的数组
    @GET("api/invoke/video/invoke/video")
    Call<Video[]>getVideos();
}
