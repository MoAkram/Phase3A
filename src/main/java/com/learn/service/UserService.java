package com.learn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Course;
import com.learn.model.Game;
import com.learn.model.User;
import com.learn.repository.GameRepository;
import com.learn.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userrepo;
	@Autowired
	GameRepository gamerepo;
	
	public ModelAndView getHome(){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("Home");
		return mav;
	}
	public ModelAndView getSignup(){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("SignUp");
		mav.addObject("User", new User());
		return mav;
	}
	public ModelAndView getLogin(){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("LogIn");
		mav.addObject("User", new User());
		return mav;
	}
	public ModelAndView getCreateCourse(){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("CreateCourse");
		mav.addObject("Course", new Course());
		return mav;
	}
	
	
	public long useID(User user) {
		String name = user.getName();
		String password = user.getPassword();
		User x = userrepo.FindBynameAndpassword(name, password);
		return x.getId();
	}

	public User logIn(User user, ModelAndView mav) {
		String name = user.getName();
		String password = user.getPassword();
		User x = userrepo.FindBynameAndpassword(name, password);

		if (x != null) {
			return x;
		} else {
			return null;
		}
	}

	public Boolean selectMode(User user) {
		String name = user.getName();
		String password = user.getPassword();
		User x = userrepo.FindBynameAndpassword(name, password);
		if (x.getType().contains("Teacher")) {
			return true;
		} else
			return false;
	}

	public UserService(UserRepository userrepository) {
		this.userrepo = userrepository;
	}

	public List<User> getall() {
		return (List<User>) userrepo.findAll();
	}

	public ModelAndView add(User user) {
		boolean valid = false;
		if ((((user.getMail()).contains("@fci")) && user.getType().contains("Teacher"))
				|| user.getType().contains("Student")) {
			valid = true;
		}
		User x = userrepo.FindBymail(user.getMail());
		User y = userrepo.FindByname(user.getName());
		if (y == null && x == null && valid) {
			userrepo.save(user);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("SignUp");
			mav.addObject("error", "bal7");
			return mav;
		}
	}
	public ModelAndView intializeHome(User x, long id){
		ModelAndView mav =new ModelAndView();
		mav.setViewName("Home");
		mav.addObject("User", x);
		ArrayList<Game> teacherGames = gamerepo.FindByTeacherId(id);
		mav.addObject("TeacherGames", teacherGames);
		return mav;
	}
}