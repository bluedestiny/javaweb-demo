package com.jinan.task;

import java.util.Date;

public class MyJob {
	public void execute(){
        System.out.println("Quartz的任务调度！！！"+(new Date()).toString());
    }

}
