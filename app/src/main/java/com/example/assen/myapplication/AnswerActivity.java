package com.example.assen.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {
    private TextView mAnswer_text_View;
    private static  final  String EXTRA_ANSWER_SHOWN="answer_show";//返回的键值
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//去掉标题栏
        setContentView(R.layout.activity_answer);

        mAnswer_text_View=findViewById(R.id.Answer_text_View);
        Intent data=getIntent();//获取传过来的Intent对象
        String answerr=data.getStringExtra("msg");//取出msg对应的数据
        mAnswer_text_View.setText(answerr);//显示到文本组件中

        data.putExtra(EXTRA_ANSWER_SHOWN,"您已查看了答案");
        setResult(Activity.RESULT_OK,data);//返回代码和intent对象（包含要返回的数据）

        mImageView=findViewById(R.id.imageView);

        Animator set=AnimatorInflater.loadAnimator(this,R.animator.animator_alpha);//加载本地属性动画
        set.setTarget(mImageView);//将动画设置到图片组件
        set.start();//启动动画
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //TODO On Animator start
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TODO 动画结束时
                ValueAnimator moneyAnimator=ValueAnimator.ofFloat(0f,126512.03f);//offFloat浮点数估值器（初始值）
                moneyAnimator.setInterpolator(new DecelerateInterpolator());//减速插值器
                moneyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//当前
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float money=(float) animation.getAnimatedValue();//获取金额
                        mAnswer_text_View.setText(String.format("%.2f$",money));//显示金额
                    }
                });
                int starColor= Color.parseColor("#FFDEAD");//起始颜色
                int endColor=Color.parseColor("#FF4500");
                ValueAnimator colorAnimator=ValueAnimator.ofArgb(starColor,endColor);
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int color=(int)animation.getAnimatedValue();
                        mAnswer_text_View.setTextColor(color);
                    }
                });
                AnimatorSet Set=new AnimatorSet();//创建组合动画对象
                Set.playTogether(moneyAnimator,colorAnimator);//将两个动画组合
                Set.setDuration(10000);//动画持续时间
                Set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //TODO 动画取消时
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //TODO 动画重复时
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mImageView.setImageResource(R.drawable.animation_frame);//将 帧动画文件 设置到图片组件的图片资源中
        AnimationDrawable animationDrawable= (AnimationDrawable) mImageView.getDrawable();//将组件图片资源设置到逐帧动画对象中
        animationDrawable.start();
    }
}
