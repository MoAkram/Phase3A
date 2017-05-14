package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long GameId;
	String comment;
	String name;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGameId() {
		return GameId;
	}

	public void setGameId(Long gameId) {
		GameId = gameId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Comment(Long id, Long gameId, String comment, String name) {
		super();
		this.id = id;
		GameId = gameId;
		this.comment = comment;
		this.name = name;
	}

	public Comment() {

	}

}
