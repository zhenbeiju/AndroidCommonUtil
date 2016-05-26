package commanutil.utl;

import com.google.gson.Gson;

import java.io.Serializable;

public class TaskExcuter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TimerTicker timeUnit;
	public int id;
	public long creatTime;
	public boolean isExcuted = false;


    public  TaskExcuter(){}

    public static TaskExcuter getInstance(String json){
        TaskExcuter excuter = new Gson().fromJson(json,TaskExcuter.class);
        return  excuter;
    }



    @Override
    public String toString() {
        String s = new Gson().toJson(this);
        return s;
    }
}