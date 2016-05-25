package com.zhenbeiju.commanutil.common.utl;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class TimerTicker implements Serializable, ITimeUnit {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public boolean repeatFlag = false;
    public int repeatDistance = 1;
    public int repeatType = 0;
    public Long mBaseTime;

    public boolean avalideFlag = false;
    public Date time;
    public boolean[] weekAvalibeFlag = {true, true, true, true, true, true, true};
    public int endCount = 1000;
    public long endDate = Long.MAX_VALUE;


    public TimerTicker() {
        mBaseTime = System.currentTimeMillis();
        repeatType = Calendar.WEEK_OF_YEAR;


    }

    public TimerTicker(long time, boolean repeatFlag, int repeatDistance, int repeatType) {
        this.mBaseTime = time;
        this.repeatFlag = repeatFlag;
        this.repeatDistance = repeatDistance;
        this.repeatType = repeatType;
    }


    public void setAvalideFlag(boolean[] weekAvalibeFlag) {
        avalideFlag = true;
        System.arraycopy(weekAvalibeFlag, 0, this.weekAvalibeFlag, 0, weekAvalibeFlag.length);
    }


    @Override
    public long getRunTime() {
        Calendar changeCale = Calendar.getInstance();
        changeCale.setTimeInMillis(mBaseTime);
        time = changeCale.getTime();

        if (repeatFlag) {
            int tempRepeatCount = 0;
            while ((time.getTime() - System.currentTimeMillis()) <= 2 && tempRepeatCount < endCount) {

                //TODO change week caculater

                if (repeatType == Calendar.WEEK_OF_MONTH || repeatType == Calendar.WEEK_OF_YEAR) {

                    changeCale.add(repeatType, repeatDistance - 1);
                    //TODO test 在合适的一周内，选出合适的一天，
                    for (int i = 0; i < 7; i++) {
                        int weekday = changeCale.get(Calendar.DAY_OF_WEEK);
                        weekday--;
                        // TODO
                        if (weekday == 0) {
                            weekday = 6;
                        }
                        if (weekAvalibeFlag[weekday] && changeCale.getTime().getTime() > System.currentTimeMillis()) {
                            time = changeCale.getTime();
                            break;
                        }
                        changeCale.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    continue;
                } else {
                    changeCale.add(repeatType, repeatDistance);
                }
                time = changeCale.getTime();
                tempRepeatCount++;
            }
            if (changeCale.getTime().getTime() > endDate) {
                return endDate;
            }
            return changeCale.getTime().getTime();
        }

        return mBaseTime;
    }

    @Override
    public boolean getReapteFlag() {
        // TODO Auto-generated method stub
        return repeatFlag;
    }

    public Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mBaseTime);
        return calendar;
    }

    String[] timeleveltrans = {"", "年", "月", "周", "周", "天", "天", "天", "", "", "小时", "小时", "分钟", "秒", ""};
    String[] weektrans = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    int[] repeatTypeLevel = {Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,};

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mBaseTime);
        if (repeatFlag) {

            sb.append("每");
            if (repeatDistance != 1) {
                sb.append(repeatDistance);
            }
            sb.append(timeleveltrans[repeatType]);

            if (repeatType == Calendar.WEEK_OF_YEAR) {
                for (int i = 0; i < weekAvalibeFlag.length; i++) {
                    if (weekAvalibeFlag[i]) {
                        sb.append(weektrans[i]);
                        sb.append("、");
                    }
                }
            } else {
                for (int i : repeatTypeLevel) {
                    if (i > repeatType) {
                        sb.append(calendar.get(i));
                        sb.append(timeleveltrans[i]);
                    }
                }
            }


            if (endCount > 0) {
                sb.append("直到");
                sb.append(endCount);
                sb.append("次以后");
            } else if (endDate > 0) {
                sb.append("直到");
                calendar.setTimeInMillis(endDate);
                for (int i : repeatTypeLevel) {
                    sb.append(calendar.get(i));
                    sb.append(timeleveltrans[i]);
                }
            }


        } else {

            sb.append(calendar.get(Calendar.YEAR));
            sb.append("/");
            sb.append(calendar.get(Calendar.MONTH) + 1);
            sb.append("/");
            sb.append(calendar.get(Calendar.DAY_OF_MONTH));
        }
        return sb.toString();
    }


    public String toJson() {
//        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static TimerTicker getFromeJson(String json) {
        Gson gson = new Gson();
        TimerTicker ticker = gson.fromJson(json, TimerTicker.class);
        return ticker;
    }


}
