package com.zhenbeiju.commanutil.common.utl;

import com.zhenbeiju.commanutil.common.utl.RunAbleStack.RunExcute;
import com.zhenbeiju.commanutil.common.utl.file.ObjUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class SuperTaskSchedu implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected RunAbleStack mAbleStack;
    protected Thread mThread;
    private HashMap<Integer, Runnable> mTaskRunableHash = new HashMap<Integer, Runnable>();
    protected ArrayList<TaskExcuter> excuterArray = new ArrayList<TaskExcuter>();
    protected final String excuteArray_savePath = "/data/data/com.voice.assistant.main/models/task";
    private final int MAX_VALUE = Integer.MAX_VALUE - 100;

    public SuperTaskSchedu() {
        mAbleStack = new RunAbleStack();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    if (!mAbleStack.isEmpty()) {
                        RunExcute r = mAbleStack.peek();
                        // LogManager.e(r.getExcuteTime() + "   " +
                        // (r.getExcuteTime() - System.currentTimeMillis()));
                        long distance = System.currentTimeMillis() - r.getExcuteTime();
                        if (distance > 0) {
                            LogManager.i("real pop one " + mAbleStack.size());
                            r = mAbleStack.pop();
                            excuterArray.remove(r.getmRunnable());
                            r.getmRunnable().run();
                        }
//						else if (distance > 0) {
//
//						}
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }

    public int pushStack(Runnable r) {

        RunExcute runExcute = mAbleStack.new RunExcute();
        runExcute.setExcuteTime(System.currentTimeMillis());
        runExcute.setmRunnable(r);
        mAbleStack.push(runExcute);
        int id = new Random().nextInt(MAX_VALUE - 50);
        mTaskRunableHash.put(id, r);
        return id;
    }

    public int pushStackDelay(Runnable r, long DelayTime) {
        // LogManager.e("push new task");
        // RunExcute runExcute = mAbleStack.new RunExcute();
        // runExcute.setExcuteTime(System.currentTimeMillis() + DelayTime);
        // runExcute.setmRunnable(r);
        // mAbleStack.push(runExcute);
        // int id = new Random().nextInt(MAX_VALUE);
        // mTaskRunableHash.put(id, r);
        return pushStackatTime(r, System.currentTimeMillis() + DelayTime);
    }

    public int pushStackatTime(Runnable r, long Time) {

        LogManager.printStackTrace();
        LogManager.e("push new task  " + (Time - System.currentTimeMillis()));
        RunExcute runExcute = mAbleStack.new RunExcute();
        runExcute.setExcuteTime(Time);
        runExcute.setmRunnable(r);
        mAbleStack.push(runExcute);
        int id = new Random().nextInt(MAX_VALUE - 50);
        mTaskRunableHash.put(id, r);
        return id;
    }

    public void pushStackatTime(Runnable r, long Time, int id) {
        LogManager.e("push new task");
        RunExcute runExcute = mAbleStack.new RunExcute();
        runExcute.setExcuteTime(Time);
        runExcute.setmRunnable(r);

        if (mTaskRunableHash.containsKey(id)) {
            mAbleStack.removeExcute(mTaskRunableHash.get(id));
            mTaskRunableHash.remove(id);
        }

        mAbleStack.push(runExcute);
        mTaskRunableHash.put(id, r);
    }

    public void removeStack(Runnable r) {
        mAbleStack.removeExcute(r);
    }

    /**
     * 用来根据ID移除任务的
     */
    public void removeTaskById(int id) {
        LogManager.e("remove task " + id);
        if (mTaskRunableHash.containsKey(id)) {
            removeStack(mTaskRunableHash.get(id));
        }
        for (TaskExcuter t : excuterArray) {
            if (t.id == id) {
                excuterArray.remove(t);
                break;
            }
        }
        try {
            ObjUtil.saveTo(excuteArray_savePath, excuterArray);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<TaskExcuter> getRunTask() {
        return excuterArray;
    }

}
