package com.jnu.itime2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jnu.itime2.R;

import java.text.ParseException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CountActivity extends AppCompatActivity {

    private RelativeLayout test;
    // 倒计时
    private TextView mDays_Tv, mHours_Tv, mMinutes_Tv, mSeconds_Tv,t4,t5;

    private long mDay=1 ;// 天
    private long mHour=2 ;//小时,
    private long mMin =2;//分钟,
    private long mSecond =3;//秒
    long diff;


    private  TextView juli;
    private Timer mTimer;
    private Time t=new Time("GMT+8");

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if(mDay==-1&&mHour==23&&mSecond==29&&mMin==59)
                {
                    mDay=0;
                    mHour=0;
                    mSecond=0;
                    mMin=0;
                    juli.setText("距离预定日期已过去");
                    computeTimePast();
                }
                else if(diff<0)
                {
                    juli.setText("距离预定日期已过去");
                    computeTimePast();
                }
                else
                    computeTime();

                mDays_Tv.setText(mDay+"");//天数不用补位
                mHours_Tv.setText(getTv(mHour));
                mMinutes_Tv.setText(getTv(mMin));
                mSeconds_Tv.setText(getTv(mSecond));
                if (mSecond == 0 &&  mDay == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        juli=findViewById(R.id.describe_tv);
        mTimer = new Timer();
        test = (RelativeLayout) findViewById(R.id.countdown_layout);
        mDays_Tv = (TextView) findViewById(R.id.days_tv);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);

        t5 = (TextView) findViewById(R.id.textView5);

        startRun();

        final String goodPrice = getIntent().getStringExtra("good_price");
        final int goodName = getIntent().getIntExtra("good_name",0);
        final int goodday = getIntent().getIntExtra("good_day",0);
        final int goodyear = getIntent().getIntExtra("good_year",0);
        final int goodmonth = getIntent().getIntExtra("good_month",0);
        final String goodtime = getIntent().getStringExtra("good_time");
        int color=getIntent().getIntExtra("color",0);
        t5.setText(goodtime);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable drawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(drawable);

        t.setToNow(); // 取得系统时间。
        int myear = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(goodyear + "-" + goodmonth + "-" + goodday + " 00:00:00");//传入时间
            Date d2 = df.parse(myear + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);//当前事件
            diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            if(diff<0){
                long days = (-diff) / (1000 * 60 * 60 * 24);
                long hours = (-diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (-diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = (-diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                System.out.println("" + days + "天" + hours + "小时" + minutes + "分" + seconds + "秒");
                mDay =days;// 天
                mHour =(hours+8)%24;//小时,7
                mMin =minutes;//分钟,
                mSecond=seconds;
            }
            else {
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                System.out.println("" + days + "天" + hours + "小时" + minutes + "分" + seconds + "秒");
                mDay =days;// 天
                mHour =(hours-8)%24;//小时,7
                mMin =minutes;//分钟,
                mSecond=seconds;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
        /**
         * 开启倒计时
         *  //time为Date类型：在指定时间执行一次。
         *        timer.schedule(task, time);
         *  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
         *       timer.schedule(task, firstTime,period);
         *  //delay 为long类型：从现在起过delay毫秒执行一次。
         *       timer.schedule(task, delay);
         *  //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
         *       timer.schedule(task, delay,period);
         */

        private void startRun() {
            TimerTask mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Message message = Message.obtain();
                    message.what = 1;
                    timeHandler.sendMessage(message);
                }
            };
            mTimer.schedule(mTimerTask,0,1000);
        }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                    }
                }
            }
        }
    }

    private void computeTimePast() {
        mSecond++;
        if (mSecond  >59) {
            mMin++;
            mSecond = 0;
            if (mMin > 59) {
                mMin = 0;
                mHour++;
                if (mHour > 23) {
                    // 倒计时结束
                    mHour =0;
                    mDay++;

                }
            }
        }
    }

}
