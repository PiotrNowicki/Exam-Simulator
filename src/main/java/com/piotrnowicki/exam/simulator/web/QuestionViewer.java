package com.piotrnowicki.exam.simulator.web;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.github.rjeschke.txtmark.Processor;
import com.piotrnowicki.exam.simulator.boundary.QuestionsReader;
import com.piotrnowicki.exam.simulator.entity.Question;

@ManagedBean
@ViewScoped
public class QuestionViewer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	QuestionsReader questionsReader;

	private String questionNumber;

	private Question question;
	
	private String previousQuestionNumber;
	
	private String nextQuestionNumber;

	private boolean showExplanaition;


	public void loadQuestion() {
		if (questionNumber == null || questionNumber.isEmpty()) {
			questionNumber = questionsReader.getQuestions().firstKey();
		}
		
		this.question = questionsReader.getQuestionById(questionNumber);
		this.nextQuestionNumber = questionsReader.getQuestions().higherKey(questionNumber);
		this.previousQuestionNumber = questionsReader.getQuestions().lowerKey(questionNumber);
	}

	public Map<String, Question> allQuestions() {
		return questionsReader.getQuestions();
	}

	public String markdownify(String value) {
		return Processor.process(value);
	}

	public void toggleExplanaition() {
		showExplanaition = !showExplanaition;
	}
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

	public boolean isShowExplanaition() {
		return showExplanaition;
	}

	public void setShowExplanaition(boolean showExplanaition) {
		this.showExplanaition = showExplanaition;
	}

	public String getPreviousQuestionNumber() {
		return previousQuestionNumber;
	}

	public void setPreviousQuestionNumber(String previousQuestionNumber) {
		this.previousQuestionNumber = previousQuestionNumber;
	}

	public String getNextQuestionNumber() {
		return nextQuestionNumber;
	}

	public void setNextQuestionNumber(String nextQuestionNumber) {
		this.nextQuestionNumber = nextQuestionNumber;
	}

}
