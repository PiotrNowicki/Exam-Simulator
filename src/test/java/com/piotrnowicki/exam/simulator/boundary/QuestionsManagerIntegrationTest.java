package com.piotrnowicki.exam.simulator.boundary;

import java.util.NavigableMap;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

import com.piotrnowicki.exam.simulator.control.Cache;
import com.piotrnowicki.exam.simulator.entity.Question;

public class QuestionsManagerIntegrationTest extends Arquillian {

	@Deployment
	public static WebArchive deploy() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class);
		
		// Add entities
		archive.addPackage(Question.class.getPackage());

		// ... control logic
		archive.addPackage(Cache.class.getPackage());

		// ... and the boundary we're testing
		archive.addClass(QuestionsManager.class);

		// Remember about JPA and CDI config
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		archive.addAsWebInfResource("beans.xml");
		
		// Also add all runtime dependencies from Maven we require
//		MavenRepositorySystem
		return archive;
	}
	
	@Inject
	private Cache cache;

	@Test
	public void f() {
		NavigableMap<String,Question> questions = cache.getQuestions();
	}
}
