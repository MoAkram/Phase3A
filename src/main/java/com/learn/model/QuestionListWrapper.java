package com.learn.model;

import java.util.ArrayList;

public class QuestionListWrapper {
	private ArrayList<Question> Questions;

	public QuestionListWrapper() {
		
	}
	public void saveGameId(long id){
		for(int i=0;i<Questions.size();i++){
			Questions.get(i).setGameId(id);
		}
	}
	public QuestionListWrapper(ArrayList<Question> questions) {
		super();
		Questions = questions;
	}

	public ArrayList<Question> getQuestions() {
		return Questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		Questions = questions;
	}
	

}
