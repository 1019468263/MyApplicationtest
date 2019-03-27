package com.example.assen.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mButtonTrue,mButtonFalse;//正确按钮和错误按钮
    private Button mButtonNext,mButtonAnswer;//下一题和查看答案
    private TextView mQuestionTextView;//显示题目
    private int mQuestionIndex=0;//题目索引
    private static final int REQUEST_CODE_ANSWER=10;//请求代码
    private TranslateAnimation mTranslateAnimation;//初始化成员变量
    private Animation mAnimation;
    private Question[] mQuestions=new Question[]{
            new Question(R.string.Q1,false),
            new Question(R.string.Q2,true),
            new Question(R.string.Q3,true),
            new Question(R.string.Q4,true),
            new Question(R.string.Q5,true),
            new Question(R.string.Q6,true),
            new Question(R.string.Q7,true),
            new Question(R.string.Q8,true),
    };
    //1
    private static final String TAG="MainActivity";//日志来源
    private static final String KEY_INDEX="index";//索引键


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//调用父类方法
        if (savedInstanceState!=null){
            mQuestionIndex=savedInstanceState.getInt(KEY_INDEX,0);
            Log.d(TAG, "Bundle恢复状态");
        }
        //2
        Log.i(TAG,"onCreate()初始化界面");

        setContentView(R.layout.activity_main);//投影出去


        mQuestionTextView=findViewById(R.id.n_main_TextView);
        mButtonTrue=findViewById(R.id.n_main_correct);
        mButtonFalse=findViewById(R.id.n_main_error);
        mButtonNext=findViewById(R.id.n_main_button_next);
        mButtonAnswer=findViewById(R.id.n_main_button_tips);
        updateQuQueestion();//更新题目

        //检测用户答题是否正确
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(true);
            }
        });


        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(false);
            }
        });


        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mQuestionIndex=(mQuestionIndex+1)%mQuestions.length;//防溢出的递增循环算法
              /*  if(mQuestionIndex==mQuestions.length-1){
                    mQuestionIndex=0;
                }else{
                    mQuestionIndex++;
                }*/

            updateQuQueestion();//更新题目
             mButtonNext.setEnabled(false);//恢复下一题不可用按钮
                if(mQuestionIndex==mQuestions.length-1){
                    Toast.makeText(MainActivity.this,"最后一题了",Toast.LENGTH_SHORT).show();
                   mButtonNext.setText(R.string.TextView_rest);//更换按钮文字
                    updateButtonNext(R.drawable.ic_reset);//更换按钮图片
                }
                if(mQuestionIndex==0){
                    mButtonNext.setText("下一题");//更换按钮文字
                    updateButtonNext(R.drawable.ic_move_to_next);//更换按钮图片
                }
            }
        });
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;
                if(mQuestions[mQuestionIndex].isAnswer()){
                    temp="正确";
                }else{
                    temp="错误";
                }
               Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("msg",temp);//将数据附到Intent
                startActivityForResult(intent,REQUEST_CODE_ANSWER);//需要返回值的启动Activity方法


                startActivity(intent);
//              //检查是否已获取授权
//                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=0) {
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);//动态申请权限，请求代码1
//                }
//                    Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("smsto:18820821272"));//发送短信
//                    startActivity(intent);

            }
        });
    }

    private  void checkQuestion(boolean userAnswer)
    {
        boolean trueAnswer=mQuestions[mQuestionIndex].isAnswer();//取得正确答案
        int message;
        if(userAnswer==trueAnswer){
            message=R.string.yes;
            mButtonNext.setEnabled(true);
        }else{
            message=R.string.no;
            mButtonNext.setEnabled(false);
        }
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

    }


    //换图片
    private void updateButtonNext(int imageID){
        Drawable d = getDrawable(imageID);
        d.setBounds(0 ,0,d.getMinimumWidth(),d.getMinimumHeight());
        mButtonNext.setCompoundDrawables(null,null,d,null);
    }

    //获取题目方法
    private void updateQuQueestion(){
        int i=mQuestions[mQuestionIndex].getTextID();//获取题目资源ID
        mQuestionTextView.setText(i);//显示出来
       /* mTranslateAnimation =new TranslateAnimation(-5,5,0,0);
        mTranslateAnimation.setDuration(100);//动画持续时间
        mTranslateAnimation.setRepeatCount(5);//重复次数（不包括第一次）
        mTranslateAnimation.setRepeatMode(Animation.REVERSE);//动画执行模式
        mQuestionTextView.setAnimation(mTranslateAnimation);*/
        mAnimation=AnimationUtils.loadAnimation(this,R.anim.animation_list);
       mQuestionTextView.setAnimation(mAnimation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()使界面可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()界面前台显示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()界面离开前台");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()界面不可见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()销毁"+TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart()我又肥来了");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState()保存状态");
        outState.putInt(KEY_INDEX,mQuestionIndex);//形成键值对

    }
    //requestCode请求代码
    //resultCode 返回代码
    //data返回的Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ANSWER && requestCode==RESULT_OK){//请求代码一致
              String temp=data.getStringExtra("answer_show");//取出msg对应的数据
              Toast.makeText(MainActivity.this,temp,Toast.LENGTH_LONG).show();;
                assert  data!=null;
        }
    }
}
