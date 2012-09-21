package com.piotrnowicki.exam.simulator.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.rjeschke.txtmark.Processor;
import com.piotrnowicki.exam.simulator.boundary.ExamManager;
import com.piotrnowicki.exam.simulator.entity.Question;

@Named
@RequestScoped
public class QuestionsViewer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ExamManager examManager;
	
	@PostConstruct
	private void init() {
	}

	public Question getQuestionById(String questionNum) {
		Question question = examManager.getQuestionById(questionNum);
		
		question = markdownifyQuestion(question);
		
		return question;
	}
	
	public List<Question> allQuestions() {
		return new ArrayList<>(examManager.getQuestions().values());
	}
	
	
	Question markdownifyQuestion(Question question) {
		String summary = Processor.process(question.getSummary());
		String content = Processor.process(question.getContent());
		String explanaition = Processor.process(question.getExplanaition());

		question.setSummary(summary);
		question.setContent(content);
		question.setExplanaition(explanaition);

		return question;
	}
}
