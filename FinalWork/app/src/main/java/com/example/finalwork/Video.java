package com.example.finalwork;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Video {
    //视频ID，例如：5e9830b0ce330a0248e89d86
    @SerializedName("_id")
    public String _id;
    //视频的url，例如：http://jzvd.nathen.cn/video/1137e480-170bac9c523-0007-1823-c86-de200.mp4
    @SerializedName("feedurl")
    public String feedurl;
    //作者昵称，例如：王火火
    @SerializedName("nickname")
    public String nickname;
    //视频描述，例如：这是第一条Feed数据
    @SerializedName("description")
    public String description;
    //喜欢这个视频的人数，例如：1000
    @SerializedName("likecount")
    public int likecount;
    //作者头像的url，例如：http://jzvd.nathen.cn/snapshot/f402a0e012b14d41ad07939746844c5e00005.jpg
    @SerializedName("avatar")
    public String avatar;
}
