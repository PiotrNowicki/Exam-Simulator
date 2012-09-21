package com.piotrnowicki.exam.simulator.control;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.entity.Question;

@ApplicationScoped
public class Cache {
	
	@Inject
	EntityManager em;
	
	Map<String, Question> questions;

	@PostConstruct
	private void populate() {
		List<Question> questionsFromDB = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
		
		Map<String, Question> processed = new HashMap<>();
				
		for (Question question : questionsFromDB) {
			processed.put(question.getNumber(), question);
		}
		
		questions = Collections.unmodifiableMap(processed);
	}
	
	public Map<String, Question> getQuestions() {
		return questions;
	}
}
