package com.piotrnowicki.exam.simulator.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.control.DataModifiedEvent;
import com.piotrnowicki.exam.simulator.entity.Question;

@Stateless
public class QuestionsManager {

	@Inject
	EntityManager em;
	
	@Inject
	Event<DataModifiedEvent> events;

	public Question getQuestionById(Long id) {
		return em.find(Question.class, id);
	}

	public List<Question> getQuestions() {
		return em.createNamedQuery(Question.READ_ALL, Question.class)
				.getResultList();
	}

	public Question updateQuestion(Question question) {
		events.fire(new DataModifiedEvent(question));
		
		return em.merge(question);
	}

	// TODO: move it to the interceptor like @GenerateEvent
	public Question createQuestion(Question question) {
		em.persist(question);

		events.fire(new DataModifiedEvent(question));
		
		return question;
	}

	public void deleteQuestion(Question question) {
		events.fire(new DataModifiedEvent(question));
		
		if (!em.contains(question)) {
			Question managedQuestion = getQuestionById(question.getId());
			em.remove(managedQuestion);
		} else {
			em.remove(question);
		}
	}
}
