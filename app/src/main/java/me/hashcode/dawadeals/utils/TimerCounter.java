package me.hashcode.dawadeals.utils;

import android.text.TextUtils;

import java.util.Timer;
import java.util.TimerTask;

import me.hashcode.dawadeals.R;


@SuppressWarnings("unused")
public class TimerCounter {
    private Timer timer;
    private OnTimerTick OnTimerTick;
    private OnTimerFinished OnTimerFinished;
    private long time;
    private final long timeConstant;
    private int passedSeconds;
    private TimerStatus timerStatus;
    private TimeType timeType;
    private int executionInterval;

    boolean numberFormat;
    public TimerCounter(OnTimerTick onTimerTick, TimeType timeType) {
        timerStatus=TimerStatus.notWorking;
        timeConstant = 0 ;
        this.timeType = timeType ;
        OnTimerTick = onTimerTick;
        switch (timeType){
            case Second:executionInterval=1;break;
            case Minute:executionInterval=30;break;
            case Hour:executionInterval=150;break;
            case Day:executionInterval=3600;break;
        }
    }

    public TimerCounter(OnTimerTick onTimerTick,int executionInterval) {
        timerStatus=TimerStatus.notWorking;
        timeConstant = 0 ;
        this.executionInterval = executionInterval ;
        OnTimerTick = onTimerTick;
    }

    public TimerCounter(long time, TimeType timeType, OnTimerTick onTimerTick, TimerCounter.OnTimerFinished onTimerFinished) {
        timerStatus=TimerStatus.notWorking;
        OnTimerTick = onTimerTick;
        OnTimerFinished = onTimerFinished;
        this.timeConstant=time;
        this.time= toSeconds(timeConstant,timeType);
        this.timeType=timeType;
        switch (timeType){
            case Second:executionInterval=1;break;
            case Minute:executionInterval=30;break;
            case Hour:executionInterval=150;break;
            case Day:executionInterval=3600;break;
        }
    }
    public TimerCounter(long time, TimeType timeType,
                        OnTimerTick onTimerTick,
                        TimerCounter.OnTimerFinished onTimerFinished,
                        int executionInterval) {
        timerStatus=TimerStatus.notWorking;
        OnTimerTick = onTimerTick;
        OnTimerFinished = onTimerFinished;
        this.timeConstant=time;
        this.time= toSeconds(timeConstant,timeType);
        this.timeType=timeType;
        this.executionInterval = executionInterval ;
    }

    public static long fromSeconds(long seconds, TimeType timeType) {
        switch (timeType) {
            case Day: {
                return seconds / ( 24 * 3600);
            }
            case Hour:{
                return seconds / 3600;
            }
            case Minute:{
                return seconds / 60;
            }
            default: {
                return seconds;
            }
        }
    }


    public static long toSeconds(long date, TimeType timeType) {
        switch (timeType) {
            case Day: {
                return date * 24 * 3600;
            }
            case Hour:{
                return date*3600;
            }
            case Minute:{
                return date*60;
            }
            default: {
                return date;
            }
        }
    }


    public TimerStatus getTimerStatus() {
        if (timer == null)
            return TimerStatus.notWorking;
        return timerStatus;
    }

    public void startTimer(long startTime) {
        cancel();
        if(timeConstant!= 0L)
            startRegularTimer(startTime);
        else startInfinteTimer();
    }

    public interface OnTimerTick {
        void onTimerTick(String time);
    }
    public interface OnTimerFinished{
        void onfinished(String time);
    }

    public void startTimer()
    {
        startTimer(0);
    }

    public boolean isNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(boolean numberFormat) {
        this.numberFormat = numberFormat;
    }
    private void startRegularTimer(){
        startRegularTimer(0);
    }
    private void startRegularTimer(long startTime) {
        if (OnTimerTick == null && OnTimerFinished == null)
            return;
        cancel();
        passedSeconds = 0;
        timer=new Timer();
        this.time=toSeconds(timeConstant,timeType) ;
        if (startTime>0)
            this.time-= (System.currentTimeMillis() -startTime)/1000;
        timerStatus=TimerStatus.started;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (time>1) {
                    OnTimerTick.onTimerTick(decreaseOneUnit());
                }
                else
                {
                    OnTimerFinished.onfinished("");
                    TimerCounter.this.cancel();
                    timerStatus=TimerStatus.finished;
                }
                passedSeconds += executionInterval;
            }
        }, 0, executionInterval*1000);
    }

    private void startInfinteTimer() {

        if (OnTimerTick == null)
            return;
        passedSeconds = 0;
        timer=new Timer();
        timerStatus=TimerStatus.started;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                OnTimerTick.onTimerTick(getTimeString(passedSeconds,TimeType.Second,true));
                passedSeconds += executionInterval;
            }
        }, 0, executionInterval*1000);
    }

    public void cancel() {
        timerStatus=TimerStatus.notWorking;
        try {
            if(timer!=null)
                timer.cancel();
            timer=null;
        }catch (Exception ignored){
            timer=null;
        }
    }

    public static String getTimeString(long time,TimeType type,boolean numberFormat) {
        time= toSeconds(time,type);
        int seconds = (int) (time % 60);
        int minutes = (int) ((time / 60) % 60);
        int hours = (int) ((time / 3600));
        //int days = (int) ((timeInSeconds / (86400)));
        return getTimeString(0, hours, minutes, seconds,numberFormat);
    }

    public long getTime() {
        return time;
    }

    private String decreaseOneUnit() {
        long time = this.time;//toSeconds(this.time,timeType);
        time -= executionInterval;
        this.time = time;//fromSeconds(time,timeType);
        int seconds = (int) (time % 60);
        time -= seconds;
        time = time / 60;
        int minutes = (int) (time % 60);
        time -= minutes;
        time = time / 60;
        int hours =(int)(time % 24);
        time -= hours;
        time = time / 24;
        int days = (int) (time / 24);
        if(this.time<=0)
            return getTimeString(0, 0, 0, 0,numberFormat);
        return getTimeString(0, hours, minutes, seconds,numberFormat);

    }
    private static String getTimeString(@SuppressWarnings("SameParameterValue") int day, int hour, int minute, int seconds,boolean numberFormat) {
        if(!numberFormat)
        {
            String days = day > 0 ? Utils.getFixedLengthString(day,3) + " "+Utils.getStringRes(R.string.day)+ " " : "";
            String hours = Utils.getFixedLengthString(hour,3)+ " " +Utils.getStringRes(R.string.hour)+ " " ;
            String minutes = Utils.getFixedLengthString(minute,3) + " "+Utils.getStringRes(R.string.minute)+ " " ;
            String secondss = Utils.getFixedLengthString(seconds,3) + " "+ Utils.getStringRes(R.string.seconds)+" ";
            return days +hours +minutes +secondss;
        }else {
            String days = day >9 ? ""+day:(day>0?"0"+day:"");
            String hours = hour > 9 ?""+hour:("0"+hour);
            String minutes = minute > 9 ?""+minute:"0"+minute;
            String secondss = seconds > 9? ""+seconds:"0"+seconds;
            return (!TextUtils.isEmpty(days)?days+" : "+hours:/*(TextUtils.isEmpty(hours)?hours:*/hours+" : ") /*)*/+minutes/**/ +" : "+secondss;
        }
    }

}
