package com.piotrnowicki.exam.simulator.control;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.entity.Answer;
import com.piotrnowicki.exam.simulator.entity.Question;

@Singleton
@Startup
public class Initializer {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	@PostConstruct
	private void init() {
		Answer a1 = new Answer("Content of answer 1", true);
		Answer a2 = new Answer("Content of answer 2", false);
		Answer a3 = new Answer("Content of answer 3", true);
		
		Question q = new Question("Q01", "**Short** description of the *question*", "Content of the question");
		q.addAnswers(a1, a2, a3);
		
		q.setExplanaition("Explanaition of the question");
		
		em.persist(q);
		
		Answer aa1 = new Answer("Content of answer 111", true);
		Answer aa2 = new Answer("Content of answer 222", false);
		Answer aa3 = new Answer("Content of answer 333", true);
		
		Question qq = new Question("Q02", "Another part of the `question`", "Blah blah blah");
		qq.addAnswers(aa1, aa2, aa3);
		
		qq.setExplanaition("Explanaition 2");
		em.persist(qq);
		
		log.warning("Data populated");
	}
	
}
