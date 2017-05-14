package com.learn.service;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Achievement;
import com.learn.model.Comment;
import com.learn.model.Course;
import com.learn.model.Game;
import com.learn.model.NewGameNotification;
import com.learn.model.Question;
import com.learn.model.QuestionListWrapper;
import com.learn.repository.AchievementRepository;
import com.learn.repository.CollaboratorRepository;
import com.learn.repository.CommentRepository;
import com.learn.repository.CourseRepository;
import com.learn.repository.GNotiRepository;
import com.learn.repository.GameRepository;
import com.learn.repository.QuestionRepository;

@Service
public class GameService {
	@Autowired
	GameRepository gamerepo;
	@Autowired
	CourseRepository courserepo;
	@Autowired
	QuestionRepository questionrepo;
	@Autowired
	AchievementRepository achievrepo;
	@Autowired
	CommentRepository commentrepo;
	@Autowired
	GNotiRepository notifrepo;
	@Autowired
	CollaboratorRepository collabrepo;

	public ModelAndView intializeCoursePage(Long courseId) {
		Course currentCourse = courserepo.FindById(courseId);
		ArrayList<Game> courseGames = gamerepo.FindByCourseId(courseId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Games");
		mav.addObject("Course", currentCourse);
		mav.addObject("GamesInCourse", courseGames);
		return mav;
	}

	public ModelAndView createGameIntiate(ModelAndView mav, Long CourseId) {
		ArrayList<Question> questions = new ArrayList<Question>(5);
		Question dummyQuestion = new Question();
		for (int i = 1; i < 6; i++) {
			questions.add(dummyQuestion);
		}
		mav.setViewName("Games");
		mav.addObject("Game", new Game());
		QuestionListWrapper wrapper = new QuestionListWrapper();
		wrapper.setQuestions(questions);
		mav.addObject("wrapper", wrapper);
		Course x = courserepo.FindById(CourseId);
		mav.addObject("Course", x);
		return mav;
	}

	public ModelAndView addGame(Long creatorId, Long courseId, Game game, QuestionListWrapper questionWrapper) {
		game.setCourseID(courseId);
		game.setTeacherID(creatorId);
		gamerepo.save(game);
		Long savedGameId = gamerepo.FindByName(game.getName()).getId();
		questionWrapper.saveGameId(savedGameId);
		sendGameNotifications(courseId, courserepo.FindById(game.getCourseID()).getName(), game.getName());
		questionrepo.save(questionWrapper.getQuestions());
		String coursePg = "redirect:/Course/" + courseId;
		ModelAndView mav = new ModelAndView();
		mav.setViewName(coursePg);
		return mav;
	}

	public ModelAndView showGame(Long gameId, Long currentUser) {
		Game currentGame = gamerepo.FindById(gameId);
		QuestionListWrapper wrapper = new QuestionListWrapper();
		ArrayList<Question> gameQuestions = questionrepo.FindByGameId(gameId);
		for (int i = 0; i < gameQuestions.size(); i++) {
			gameQuestions.get(i).setAnswer(null);
		}
		wrapper.setQuestions(gameQuestions);
		ArrayList<Comment> Comments = commentrepo.FindComments(gameId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("PlayGame");
		mav.addObject("Game", currentGame);
		mav.addObject("wrapper", wrapper);
		mav.addObject("Comments", Comments);
		if (currentUser == currentGame.getTeacherID()
				|| currentUser == collabrepo.FindCollab(currentUser).getCollabratorId()) {
			mav.addObject("creator", "Dummy");
		}
		return mav;
	}

	public ModelAndView evaluateGame(Long gameId, QuestionListWrapper userGame, Long userId, Long courseId) {
		ArrayList<Question> gameQuestions = questionrepo.FindByGameId(gameId);
		ArrayList<Question> userAnswers = userGame.getQuestions();// Equivalent
																	// to Get
																	// Answers
		int score = 0;
		for (int i = 0; i < gameQuestions.size(); i++) {
			if (gameQuestions.get(i).getAnswer() == userAnswers.get(i).getAnswer())
				score++;
		}
		Achievement newAchiev = new Achievement();
		newAchiev.setGameId(gameId);
		newAchiev.setUserId(userId);
		newAchiev.setScore(score);
		newAchiev.setCourseId(courseId);
		achievrepo.save(newAchiev);
		Game currentGame = gamerepo.FindById(gameId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("PlayGame");
		mav.addObject("result", score);
		mav.addObject("Game", currentGame);
		return mav;
	}

	public ModelAndView showAllGames() {
		ArrayList<Game> Games = gamerepo.FindAllGames();
		ArrayList<Course> Courses = courserepo.FindAllCourses();
		Game CopyGame = new Game();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("AllGamesAndCourses");
		mav.addObject("Courses", Courses);
		mav.addObject("Games", Games);
		mav.addObject("Game", CopyGame);
		return mav;
	}

	public ModelAndView showAchiev(long id) {
		ArrayList<Achievement> achievs = achievrepo.FindAchiev(id);
		ArrayList<Game> games = new ArrayList<Game>();
		for (int i = 0; i < achievs.size(); i++) {
			long gamedId = achievs.get(i).getGameId();
			Game game = gamerepo.FindById(gamedId);
			games.add(game);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Achievs");
		mav.addObject("Achievs", achievs);
		mav.addObject("Games", games);
		return mav;
	}

	public ModelAndView copyGame(Game userInput) {
		Long gameId = userInput.getId();
		Long courseId = userInput.getCourseID();
		Game gameToCopy = gamerepo.FindById(gameId);
		if (gameToCopy.getId() != 0) {
			Game newGame = new Game();
			newGame.setName(gameToCopy.getName());
			newGame.setDescription(gameToCopy.getDescription());
			newGame.setCourseID(courseId);
			newGame.setTeacherID(gameToCopy.getTeacherID());
			gamerepo.save(newGame);
			copyQuestions(gameId, gamerepo.FindByName(newGame.getName()).getId());
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/Games");
			return mav;

		}

		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/Games");
			mav.addObject("error", "dummy");
			return mav;
		}

	}

	public void copyQuestions(long originalId, long newGameId) {
		ArrayList<Question> orgQuestions = questionrepo.FindByGameId(originalId);
		ArrayList<Question> questions = new ArrayList<Question>();
		Question dummyQuestion = new Question();
		dummyQuestion.setId((long) 0);
		for (int i = 0; i < orgQuestions.size(); i++) {
			questions.add(dummyQuestion);
		}
		for (int i = 0; i < orgQuestions.size(); i++) {
			questions.get(i).setQuestion(orgQuestions.get(i).getQuestion());
			questions.get(i).setAnswer(orgQuestions.get(i).getAnswer());
			questions.get(i).setGameId(newGameId);
			questionrepo.save(questions.get(i));
		}
	}

	public void sendGameNotifications(long courseId, String courseName, String gameName) {
		ArrayList<BigInteger> usersBig = achievrepo.FindUsersByCourseId(courseId);
		NewGameNotification dummy = new NewGameNotification();
		for (int i = 0; i < usersBig.size(); i++) {
			dummy.setUserId(usersBig.get(i).longValue());
			dummy.setCourseName(courseName);
			dummy.setGameName(gameName);
			notifrepo.save(dummy);
		}
	}

	public ModelAndView editGame(Long gameId) {
		ModelAndView mav = new ModelAndView();
		Game gameToEdit = gamerepo.FindById(gameId);
		mav.setViewName("Games");
		mav.addObject("Game", gameToEdit);
		mav.addObject("Course", courserepo.FindById(gamerepo.FindById(gameId).getCourseID()));
		QuestionListWrapper wrapper = new QuestionListWrapper();
		wrapper.setQuestions(questionrepo.FindByGameId(gameId));
		mav.addObject("wrapper", wrapper);
		gameToEdit.setCancelled(1);
		gamerepo.save(gameToEdit);
		return mav;
	}
	public ModelAndView cancelGame(Long gameId){
		Game gameToCancel = gamerepo.FindById(gameId);
		gameToCancel.setCancelled(1);
		gamerepo.save(gameToCancel);
		ModelAndView mav= new ModelAndView();
		mav.setViewName("redirect:/MyCourses");
		return mav;
	}
	public ModelAndView getRetiredGame(Long userId){
		ArrayList<Game> retiredGames=gamerepo.FindRetiredByTeacherId(userId);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("RetiredGames");
		mav.addObject("Games", retiredGames);
		mav.addObject("Game", new Game());
		return mav;
	}
	public ModelAndView restoreGame(Long gameId){
		Game gameToRestore =gamerepo.FindById(gameId);
		Game gameToReplace= gamerepo.FindByName(gameToRestore.getName());
		gameToRestore.setCancelled(0);
		gameToReplace.setCancelled(1);
		gamerepo.save(gameToRestore);
		gamerepo.save(gameToReplace);
		ModelAndView mav= new ModelAndView();
		mav.setViewName("redirect:/MyCourses");
		return mav;
	}
}
