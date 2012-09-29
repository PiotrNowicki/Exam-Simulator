package com.piotrnowicki.exam.simulator.control;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.piotrnowicki.exam.simulator.entity.Question;


public class CacheTest {
	
	private Cache cut;
	
	@BeforeMethod
	public void beforeMethod() {
		cut = new Cache();
		cut.em = mock(EntityManager.class);
		cut.log = mock(Logger.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void populate() {
		// given
		List<Question> questions = new ArrayList<>();
		questions.add(new Question("Q01", "", ""));
		questions.add(new Question("Q02", "", ""));
		
		TypedQuery<Question> mockQuery = mock(TypedQuery.class);
		when(mockQuery.getResultList()).thenReturn(questions);
		when(cut.em.createNamedQuery(anyString(), any(Class.class))).thenReturn(mockQuery);
		
		// when
		cut.populate();

		// then
		assertThat(questions.size()).isEqualTo(2);
	}
}
