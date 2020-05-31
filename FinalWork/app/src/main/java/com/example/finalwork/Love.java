package com.example.finalwork;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class Love extends RelativeLayout {
    private Context mContext;
    float[] angles={-30,-20,0,20,30};//随机爱心图片角度

    public Love(Context context) {
        super(context);
        initView(context);
    }

    public Love(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Love(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mContext=context;
    }

    public void addheart(MotionEvent event) {
        final ImageView heart=new ImageView(mContext);
        //获取点击事件的屏幕坐标，用来设置爱心图片的出现位置
        LayoutParams params=new LayoutParams(300,300);
        params.leftMargin=(int)event.getX()-150;
        params.topMargin=(int)event.getY()-150;
        //获取爱心的图片资源，并设置其坐标
        heart.setImageDrawable(getResources().getDrawable(R.mipmap.heart));
        heart.setLayoutParams(params);
        //添加爱心的ImageView
        addView(heart);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animator(heart,"scaleX",2f,0.9f,100,0),//0ms时将爱心的X轴从2缩小到0.9，动画时长100ms
                animator(heart,"scaleY",2f,0.9f,100,0),//0ms时将爱心的y轴从2缩小到0.9，动画时长100ms
                rotation(heart,0,0,angles[new Random().nextInt(4)]),//0ms时将爱心旋转随机角度，动画时长0ms
                animator(heart,"alpha",0,1,100,0),//0ms时将爱心的透明度从0渐变为1，动画时长100ms
                animator(heart,"scaleX",0.9f,1,50,150),//150ms时将爱心的X轴从0.9放大到1，动画时长50ms
                animator(heart,"scaleY",0.9f,1,50,150),//150ms时将爱心的y轴从0.9放大到1，动画时长50ms
                animator(heart,"translationY",0,-600,800,400),//400ms时将爱心的向上平移600，动画时长800ms
                animator(heart,"alpha",1,0,300,400),//400ms时将爱心的透明度从1渐变为0，动画时长300ms
                animator(heart,"scaleX",1,3f,700,400),//400ms时将爱心的X轴从1放大到3，动画时长700ms
                animator(heart,"scaleY",1,3f,700,400));//400ms时将爱心的y轴从1放大到3，动画时长700ms
        animatorSet.start();//播放动画
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时移除爱心的ImageView
                removeViewInLayout(heart);
            }
        });
    }

    //放大缩小、平移、透明动画
    public static ObjectAnimator animator(View view,String propertyName,float from,float to,long time,long delayTime){
        ObjectAnimator myAnimator=ObjectAnimator.ofFloat(view,propertyName,from,to);
        myAnimator.setInterpolator(new LinearInterpolator());
        myAnimator.setStartDelay(delayTime);
        myAnimator.setDuration(time);
        return myAnimator;
    }

    //旋转动画
    public static ObjectAnimator rotation(View view,long time,long delayTime,float angle){
        ObjectAnimator rotationAni=ObjectAnimator.ofFloat(view,"rotation",angle);
        rotationAni.setDuration(time);
        rotationAni.setStartDelay(delayTime);
        rotationAni.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        return rotationAni;
    }
}
