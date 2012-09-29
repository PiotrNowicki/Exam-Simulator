package com.piotrnowicki.exam.simulator.boundary;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.piotrnowicki.exam.simulator.control.DataModifiedEvent;
import com.piotrnowicki.exam.simulator.entity.Question;

public class QuestionsManagerTest {

	private QuestionsManager cut;

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void beforeMethod() {
		cut = new QuestionsManager();
		cut.events = mock(Event.class);
		cut.em = mock(EntityManager.class);
	}

	@Test
	public void createQuestionFiresEvent() {
		// given
		Question createdQuestion = new Question();

		// when
		cut.createQuestion(createdQuestion);

		// then
		verify(cut.events).fire(any(DataModifiedEvent.class));
	}

	@Test
	public void deleteQuestionFiresEvent() {
		// given
		Question deletedQuestion = new Question();

		// when
		cut.deleteQuestion(deletedQuestion);

		// then
		verify(cut.events).fire(any(DataModifiedEvent.class));
	}
	
	@Test
	public void updateQuestionFiresEvent() {
		// given
		Question updatedQuestion = new Question();

		// when
		cut.updateQuestion(updatedQuestion);

		// then
		verify(cut.events).fire(any(DataModifiedEvent.class));
	}
}
