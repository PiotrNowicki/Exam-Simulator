package com.piotrnowicki.exam.simulator.web;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.piotrnowicki.exam.simulator.boundary.QuestionsManager;
import com.piotrnowicki.exam.simulator.entity.Answer;
import com.piotrnowicki.exam.simulator.entity.Question;

@ManagedBean
@ViewScoped
public class QuestionModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	QuestionsManager qManager;

	@Inject
	FacesContext ctx;

	private Question question;

	private String questionId;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionNumber) {
		this.questionId = questionNumber;
	}

	void loadQuestion(Long id) {
		this.question = qManager.getQuestionById(id);
	}

	public void loadAnswers() {
		while (question.getAnswers().size() < 6) {
			question.addAnswers(new Answer());
		}
	}

	public void initCreation(ComponentSystemEvent event) {

		if (!ctx.isPostback()) {
			question = new Question();

			for (int i = 0; i < 6; i++) {
				question.addAnswers(new Answer());
			}
		}
	}

	public void loadQuestion(String id) {
		if (id == null) {
			Long firstId = qManager.getQuestions().get(0).getId();
			loadQuestion(firstId);
		} else {
			loadQuestion(Long.valueOf(id));
		}

		loadAnswers();
	}

	public List<Question> allQuestions() {
		return qManager.getQuestions();
	}

	public String update() {
		boolean isUpdate = (question.getId() == null) ? false : true;

		removeEmptyAnswers();

		if (isUpdate) {
			qManager.updateQuestion(question);
			return null;
		} else {
			qManager.createQuestion(question);
			
			String url = "modifyQuestion.xhtml?faces-redirect=true&q=" + question.getId();
			return url;
		}

	}

	public String delete() {
		qManager.deleteQuestion(question);

		return "modifyQuestion";
	}

	void removeEmptyAnswers() {
		Iterator<Answer> it = question.getAnswers().iterator();

		while (it.hasNext()) {
			Answer answer = it.next();

			if (answer.getContent().isEmpty()) {
				it.remove();
			}
		}
	}
}
