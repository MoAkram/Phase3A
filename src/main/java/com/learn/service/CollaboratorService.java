package com.learn.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Collaborator;
import com.learn.repository.CollaboratorRepository;
import com.learn.repository.UserRepository;

@Service
public class CollaboratorService {
	@Autowired
	CollaboratorRepository collabrepo;
	@Autowired
	UserRepository userrepo;

	public ModelAndView showAddCollab() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("AddCollab");
		mav.addObject("Users",userrepo.FindTeachers());
		mav.addObject("Collaborator", new Collaborator());
		return mav;
	}

	public ModelAndView addCollab(Long collabId, Long gameAssoc) {
		Collaborator dummy= new Collaborator();
		dummy.setCollabratorId(collabId);
		dummy.setGameAssociated(gameAssoc);
		collabrepo.save(dummy);
		ModelAndView mav =new ModelAndView();
		mav.setViewName("redirect:/MyCourses");
		return mav;
	}
}
