package com.piotrnowicki.exam.simulator.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.control.DataModifiedEvent;
import com.piotrnowicki.exam.simulator.entity.Question;

/**
 * Manages the {@link Question}s (has the CRUD functionality).
 * 
 * <p>
 * This EJB should be used as an entry-point to the {@link Question}s managing functionality; its methods are transactional.
 * </p>
 * 
 * @author Piotr Nowicki
 * 
 */
@Stateless
// TODO: Move event firing methods into single, reused interceptor (https://github.com/PiotrNowicki/Exam-Simulator/issues/24)
// TODO: Add the security context to the EJBs as well - right now only the web app is secured, not this EJB directly
// (https://github.com/PiotrNowicki/Exam-Simulator/issues/25).
public class QuestionsManager {

    @Inject
    EntityManager em;

    @Inject
    Event<DataModifiedEvent> events;

    /**
     * Returns the question with the automatically generated PK.
     * 
     * @param id primary key generated by the JPA provider.
     * 
     * @return {@link Question} with given <code>id</code> or null if no such <code>id</code> could be found.
     */
    public Question getQuestionById(Long id) {
        return em.find(Question.class, id);
    }

    /**
     * Returns all the questions.
     * 
     * @return
     */
    public List<Question> getQuestions() {
        return em.createNamedQuery(Question.READ_ALL, Question.class).getResultList();
    }

    /**
     * Updates the given question in the DB.
     * 
     * <p>
     * This method fires the {@link DataModifiedEvent}.
     * </p>
     * 
     * @param question to be merged in the DB.
     * 
     * @return JPA managed updated entity
     */
    public Question updateQuestion(Question question) {
        events.fire(new DataModifiedEvent(question));

        return em.merge(question);
    }

    /**
     * Creates new question in the DB.
     * 
     * <p>
     * This method fires the {@link DataModifiedEvent}.
     * </p>
     * 
     * @param question to be persisted in the DB.
     * 
     * @return JPA managed and freshly persisted entity
     */
    public Question createQuestion(Question question) {
        em.persist(question);

        events.fire(new DataModifiedEvent(question));

        return question;
    }

    /**
     * Deletes the given question from the DB.
     * 
     * <p>
     * If the <code>question</code> is <strong>not managed</strong>, it will firstly be merged and then removed.
     * </p>
     * 
     * <p>
     * This method fires the {@link DataModifiedEvent}.
     * </p>
     * 
     * @param question to be deleted from the DB.
     */
    public void deleteQuestion(Question question) {
        if (!em.contains(question)) {
            Question managedQuestion = getQuestionById(question.getId());
            em.remove(managedQuestion);
        } else {
            em.remove(question);
        }

        events.fire(new DataModifiedEvent(question));
    }
}
