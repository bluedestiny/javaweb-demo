package com.jinan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jinan.utils.RedisBaiseTakes;

@Controller
public class TestRedisController {
	 @Resource(name="seeUserRedisTakes")
	 private RedisBaiseTakes seeUserRedisTakes;
	 
	 @RequestMapping("hello")
	    public ModelAndView hello(){
	        ModelAndView mv = new ModelAndView();
	        System.out.println("hello see");
	        seeUserRedisTakes.add("hello1","zxm");
	        mv.setViewName("hello");
	        return mv;
	    }
	 @RequestMapping("getRedisContent")
	    public ModelAndView getRedisContent(){
	        ModelAndView mv = new ModelAndView();
	        System.out.println("hello see");
	        String msg=seeUserRedisTakes.get("hello1");
	        mv.addObject("msg", msg);
	        mv.setViewName("hello");
	        return mv;
	    }

	 
}
