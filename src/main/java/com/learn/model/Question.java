package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String question;
	private Boolean answer;
	private Long gameId;

	public Boolean getAnswer() {
		return answer;
	}

	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}

	public Question(Long id, String question, Long gamedId) {
		super();
		this.id = id;
		this.question = question;
		this.gameId = gamedId;
	}

	public Question() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gamedId) {
		this.gameId = gamedId;
	}

}
