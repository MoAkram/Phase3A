package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentNotify {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String commentorName;
	private String gameName;
	private long creatorId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCommentorName() {
		return commentorName;
	}
	public void setCommentorName(String commentorName) {
		this.commentorName = commentorName;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public CommentNotify(long id, String commentorName, String gameName, long creatorId) {
		super();
		this.id = id;
		this.commentorName = commentorName;
		this.gameName = gameName;
		this.creatorId = creatorId;
	}
	public CommentNotify() {
		
	}
	
}
