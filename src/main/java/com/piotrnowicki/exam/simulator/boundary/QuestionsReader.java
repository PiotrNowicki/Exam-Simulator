package com.piotrnowicki.exam.simulator.boundary;

import java.util.NavigableMap;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.piotrnowicki.exam.simulator.control.Cache;
import com.piotrnowicki.exam.simulator.entity.Question;

@Stateless
public class QuestionsReader {

	@Inject
	Cache cache;
	
	public Question getQuestionById(String questionNum) {
		Question question = cache.getQuestions().get(questionNum);
		
		return question;
	}
	
	public NavigableMap<String, Question> getQuestions() {
		return cache.getQuestions();
	}
}
