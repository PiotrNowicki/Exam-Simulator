package com.piotrnowicki.exam.simulator.control;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.entity.Answer;
import com.piotrnowicki.exam.simulator.entity.Question;

@Singleton(name="initializer")
@Startup
public class Initializer {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;
	
	@PostConstruct
	private void init() {
		Answer a1 = new Answer("the order is always unspecified");
		Answer a2 = new Answer("the order is specified by alphabetically sorted listener implementation class names");
		Answer a3 = new Answer("the order is unspecified when using annotations to define listeners");
		Answer a4 = new Answer("the order can be defined only when using Deployment Descriptor to define listeners");

		Question q = new Question("Q01", "Listeners invocation", "Considering Servlets 3.0 and listeners invocation order, choose statements that are true:");
		q.addAnswers(a1, a2, a3, a4);
		
		q.setExplanaition("c, d<br /><br />**Reference:** page 72, section 8.2.3 “Assembling the descriptor from web.xml, web-fragment.xml and annotations”.<br /><br />**Explanation:** The order of listeners when using `@WebListener` annotation is **unspecified**. If you need to define the ordering, you should use the Deployment Descriptor which follows a set of rules (defined in section 8.2.3, 2. c.) and works basically on **the order of listeners definition in the DD**.");
		
		em.persist(q);
		
		Answer aa1 = new Answer("Content of answer 111");
		Answer aa2 = new Answer("Content of answer 222");
		Answer aa3 = new Answer("Content of answer 333");
		
		Question qq = new Question("Q02", "Another part of the question", "Blah blah blah");
		qq.addAnswers(aa1, aa2, aa3);
		
		qq.setExplanaition("Explanaition 2");
		em.persist(qq);
		
		Answer aaa1 = new Answer("Content of answer xxxx1");
		Answer aaa2 = new Answer("Content of answer xxxx2");
		Answer aaa3 = new Answer("Content of answer xxxx3");
		
		Question qq1 = new Question("Q03", "xxxxx", "zzzzz");
		qq1.addAnswers(aaa1, aaa2, aaa3);
		
		qq1.setExplanaition("Explanaition 3");
		em.persist(qq1);
		
		log.warning("Data populated");
	}
	
}
