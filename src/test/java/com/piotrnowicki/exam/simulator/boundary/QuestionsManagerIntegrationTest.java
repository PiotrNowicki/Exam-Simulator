package com.piotrnowicki.exam.simulator.boundary;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.testng.annotations.Test;

import com.piotrnowicki.exam.simulator.control.Cache;
import com.piotrnowicki.exam.simulator.entity.Question;

// TODO: Use DBUnit and Persistence in Arquillian (https://github.com/PiotrNowicki/Exam-Simulator/issues/26)
@Cleanup(phase = TestExecutionPhase.NONE)
public class QuestionsManagerIntegrationTest extends Arquillian {

    @Resource
    private UserTransaction utx;

    @Inject
    private QuestionsManager cut;

    @Inject
    private Cache cache;

    @Deployment
    public static WebArchive deploy() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class);

        // Add entities
        archive.addPackage(Question.class.getPackage());

        // ... control logic
        archive.addPackage(Cache.class.getPackage());

        // ... and the boundary we're testing.
        archive.addClass(QuestionsManager.class);

        // Remember about JPA, EJB and CDI config
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        archive.addAsWebInfResource("beans.xml");
        archive.addAsWebInfResource("ejb-jar.xml");

        // Also add all runtime dependencies from Maven we require
        File[] dependencies = DependencyResolvers.use(MavenDependencyResolver.class).includeDependenciesFromPom("pom.xml")
                .resolveAsFiles();
        archive.addAsLibraries(dependencies);

        return archive;
    }

    // -------------------------------------------------------------------------------||
    // Test methods ------------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @Test
    public void dataIsPopulated() {
        // given populated cache - see Initializer Singleton EJB.

        // when
        List<Question> questions = cut.getQuestions();

        // then
        assertThat(questions.size()).isEqualTo(3);
        assertThat(questions.get(0).getNumber()).isEqualTo("Q01");
        assertThat(questions.get(1).getNumber()).isEqualTo("Q02");
        assertThat(questions.get(2).getNumber()).isEqualTo("Q03");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void updateIsSavedAndCached() throws Exception {

        // given
        Question question = cut.getQuestions().get(0);

        // when
        question.setSummary("My test summary");
        cut.updateQuestion(question);

        // then
        Question actual = cut.getQuestionById(question.getId());
        assertThat(actual.getSummary()).isEqualTo("My test summary");

        // check the cache for update
        Question questionFromCache = cache.getQuestions().get(question.getNumber());
        assertThat(questionFromCache.getSummary()).isEqualTo("My test summary");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void createIsSavedAndCached() throws Exception {

        // given
        Question question = new Question("Qxx", "aaaaaaaaaa", "bbbbbbbbbb");

        // when
        cut.createQuestion(question);

        // then
        Question actual = cut.getQuestionById(question.getId());
        assertThat(actual).isEqualTo(question);

        // check if the cache was updated
        Question questionFromCache = cache.getQuestions().get(question.getNumber());
        assertThat(questionFromCache).isEqualTo(question);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteIsSavedAndCached() throws Exception {
        // given
        Question question = cut.getQuestions().get(0);

        // when
        cut.deleteQuestion(question);

        // then
        Question actual = cut.getQuestionById(question.getId());
        assertThat(actual).isNull();

        // check if the cache was updated
        Question questionFromCache = cache.getQuestions().get(question.getNumber());
        assertThat(questionFromCache).isNull();
    }
}
