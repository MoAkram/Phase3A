package com.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Collaborator;
import com.learn.model.Comment;
import com.learn.model.Course;
import com.learn.model.Game;
import com.learn.model.QuestionListWrapper;
import com.learn.model.User;
import com.learn.repository.CommentRepository;
import com.learn.repository.QuestionRepository;
import com.learn.repository.UserRepository;
import com.learn.service.CollaboratorService;
import com.learn.service.CommentService;
import com.learn.service.CourseService;
import com.learn.service.GameService;
import com.learn.service.NotifyService;
import com.learn.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserRepository userrepo;
	@Autowired
	QuestionRepository questionrepo;
	@Autowired
	CourseService courseservice;
	@Autowired
	UserService userservice;
	@Autowired
	GameService gameservice;
	@Autowired
	CommentService commentservice;
	@Autowired
	CommentRepository commentrepo;
	@Autowired
	NotifyService notifyservice;
	@Autowired
	CollaboratorService collabservice;

	long loggedId;
	Boolean logged = false;
	Boolean type = false; // Type keeps track of user type true for teacher and
	Long CourseSession; // false for student
	Long currentGame;

	@RequestMapping("/")
	public ModelAndView home() {
		return userservice.getHome();
	}

	@GetMapping("/SignUp")
	public ModelAndView Signup() {
		return userservice.getSignup();
	}

	@GetMapping("/LogIn")
	public ModelAndView showLoginForm() {
		return userservice.getLogin();
	}

	@GetMapping("/CreateCourse")
	public ModelAndView createCourse() {
		if (logged && type) {
			return userservice.getCreateCourse();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/SignUp")
	public ModelAndView addUser(@ModelAttribute("User") User user) {

		return userservice.add(user);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/LogIn")
	public ModelAndView verifylogin(@ModelAttribute("User") User user, ModelAndView mav) {
		User x = userservice.logIn(user, mav);
		if (x != null) {
			logged = true;
			type = userservice.selectMode(x);
			loggedId = userservice.useID(x);
			return userservice.intializeHome(x, loggedId);
		} else {
			mav.setViewName("LogIn");
			mav.addObject("error", "bal7");
			return mav;
		}

	}

	@PostMapping("/CreateCourse")
	public ModelAndView addCourse(@ModelAttribute("Course") Course course, ModelAndView mav) {
		if (type && logged) {
			return courseservice.addCourse(course, loggedId, mav);
		}
		mav.setViewName("redirect:/LogIn");
		mav.addObject("error", "bal7");
		return mav;
	}

	@GetMapping("/MyCourses")
	public ModelAndView getTeacherCourses() {
		if (logged) {
			return courseservice.getUserCourses(loggedId);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Course/{num1}")
	public ModelAndView getCourseGames(@PathVariable("num1") int num1) {
		if (logged) {
			CourseSession = (long) num1;
			return gameservice.intializeCoursePage((long) num1);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Course/CreateGame")
	public ModelAndView createGame(ModelAndView mav) {
		if (logged && type) {
			return gameservice.createGameIntiate(mav, CourseSession);
		} else {
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/Course/CreateGame")
	public ModelAndView saveGametoDB(@ModelAttribute("Game") Game game,
			@ModelAttribute("wrapper") QuestionListWrapper questionWrapper) {
		return gameservice.addGame(loggedId, CourseSession, game, questionWrapper);
	}

	@GetMapping("/Course/Play/{num1}")
	public ModelAndView showGame(@PathVariable("num1") int num1, ModelAndView mav) {
		if (logged) {
			currentGame = (long) num1;
			return gameservice.showGame((long) num1, loggedId);
		} else {
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/Course/Play/{num1}")
	public ModelAndView evaluateGame(@PathVariable("num1") int num1,
			@ModelAttribute("wrapper") QuestionListWrapper gameWrapper) {
		return gameservice.evaluateGame((long) num1, gameWrapper, loggedId, CourseSession);
	}

	@GetMapping("/Games")
	public ModelAndView showAllGames() {
		if (logged) {
			return gameservice.showAllGames();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Courses")
	public ModelAndView showAllCourses() {
		if (logged) {
			return courseservice.showAllCourses();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Achievements")
	public ModelAndView showUserAcheiv() {
		if (logged) {
			return gameservice.showAchiev(loggedId);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/Copygame")
	public ModelAndView copygame(@ModelAttribute("Game") Game gameData) {
		if (logged & type) {
			return gameservice.copyGame(gameData);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/Course/Comment/")
	public ModelAndView addComment(@ModelAttribute("Comment") Comment comment) {
		return commentservice.addComment(comment, loggedId, currentGame);
	}

	@GetMapping("/Course/Comment/{num1}")
	public ModelAndView Game(@PathVariable("num1") int num1) {
		if (logged) {
			currentGame = (long) num1;
			return commentservice.showCommentForm();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Notify")
	public ModelAndView showNotification() {
		if (logged) {
			return notifyservice.loadGameNotify(loggedId);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}

	}

	@GetMapping("/EditGame")
	public ModelAndView editGame() {
		if (logged) {
			return gameservice.editGame(currentGame);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/AddCollab")
	public ModelAndView showUsers() {
		if (logged) {
			return collabservice.showAddCollab();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/AddCollab")
	public ModelAndView addCollab(@ModelAttribute("Collaborator") Collaborator collab) {
		return collabservice.addCollab(collab.getCollabratorId(), currentGame);
	}

	@GetMapping("/CancelGame")
	public ModelAndView cancelGame() {
		return gameservice.cancelGame(currentGame);
	}

	@GetMapping("/RetiredGames")
	public ModelAndView showRetired() {
		if (logged) {
			return gameservice.getRetiredGame(loggedId);
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@PostMapping("/RetiredGames")
	public ModelAndView restoreGame(@ModelAttribute("Game") Game game) {
		if (logged) {
			return gameservice.restoreGame(game.getId());
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/LogIn");
			return mav;
		}
	}

	@GetMapping("/Test")
	public void Test() {
		gameservice.sendGameNotifications(1, "BAL7", "3ENAB");
	}
}