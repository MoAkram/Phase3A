package com.learn.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Course;
import com.learn.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository courserepo;

	public ModelAndView addCourse(Course c, long id, ModelAndView mav) {
		c.setCreatorID(id);
		courserepo.save(c);
		mav.setViewName("Home");
		return mav;
	}

	public ModelAndView getUserCourses(long id) {
		ArrayList<Course> Courses = courserepo.FindBycreatorID(id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("TeacherCourses");
		mav.addObject("Courses", Courses);
		return mav;
	}

	public ModelAndView showAllCourses() {
		ArrayList<Course> Courses = courserepo.FindAllCourses();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("AllGamesAndCourses");
		mav.addObject("Courses", Courses);
		return mav;
	}
}
