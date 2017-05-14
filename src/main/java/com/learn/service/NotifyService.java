package com.learn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.CommentNotify;
import com.learn.model.NewGameNotification;
import com.learn.repository.CommentNotifyRepository;
import com.learn.repository.GNotiRepository;

@Service
public class NotifyService {
	@Autowired
	GNotiRepository GmNotifRepo;
	@Autowired
	CommentNotifyRepository commentnotRepo;
	
	public ModelAndView loadGameNotify(Long id){
		ArrayList<NewGameNotification> notifies=GmNotifRepo.FindNotify(id);
		ArrayList<CommentNotify> comNotifies= commentnotRepo.FindNotify(id);
		ModelAndView mav= new ModelAndView();
		mav.setViewName("Notify");
		mav.addObject("GamesNotify",notifies);
		mav.addObject("ComNotifies",comNotifies);
		return mav;
	}
}
