package com.piotrnowicki.exam.simulator.control;

import com.piotrnowicki.exam.simulator.entity.Question;

public class DataModifiedEvent {
	
	private Question question;
	
	public DataModifiedEvent(Question question) {
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}
}
