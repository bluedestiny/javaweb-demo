package com.jinan.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	Logger log=Logger.getLogger(MainController.class);
	@RequestMapping("test")
	public String show(){
		int a=0;
		if(a<1){
			log.error("这个数小于1！！！");
		}
		System.out.println("test");
		return "hello";
	}
}
