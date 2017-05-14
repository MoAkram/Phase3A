package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Achievement {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Long id;
	Long GameId;
	Long userId;
	Long courseId;
	int score;
	

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Achievement(Long id, Long gameId, Long userId) {
		super();
		this.id = id;
		GameId = gameId;
		this.userId = userId;
	}

	public Achievement() {
	}
}
