package com.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.learn.model.Comment;
import com.learn.model.CommentNotify;
import com.learn.model.Game;
import com.learn.model.User;
import com.learn.repository.CommentNotifyRepository;
import com.learn.repository.CommentRepository;
import com.learn.repository.GameRepository;
import com.learn.repository.UserRepository;

@Service
public class CommentService {
	@Autowired
	UserRepository userrepo;
	@Autowired
	CommentRepository commentrepo;
	@Autowired
	CommentNotifyRepository commentnotrepo;
	@Autowired
	GameRepository gamerepo;
	
	
	public ModelAndView addComment(Comment comment, long userId, long gameId){
		User commentOwner = userrepo.FindById(userId);
		comment.setName(commentOwner.getName());
		comment.setGameId(gameId);
		commentrepo.save(comment);
		Game commentedGame= gamerepo.FindById(gameId);
		sendCommentNotify(commentedGame.getTeacherID(), commentOwner.getName(), commentedGame.getName());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/Course/Play/"+gameId);
		return mav;
	}
	public ModelAndView showCommentForm (){
		ModelAndView mav = new ModelAndView();
		Comment comment = new Comment();
		mav.setViewName("AddComment");
		mav.addObject("Comment",comment);
		return mav;
	}
	public void sendCommentNotify(Long creatorId,String commentorName, String gameName){
		CommentNotify dummy= new CommentNotify();
		dummy.setCreatorId(creatorId);
		dummy.setCommentorName(commentorName);
		dummy.setGameName(gameName);
		commentnotrepo.save(dummy);
	}
}
