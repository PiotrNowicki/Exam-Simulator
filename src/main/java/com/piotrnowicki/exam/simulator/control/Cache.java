package com.piotrnowicki.exam.simulator.control;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.piotrnowicki.exam.simulator.entity.Question;

/**
 * Cache for the {@link Question}s. During the startup it's populated with data fetched from the DB.
 * <p>
 * It is aware of any data modifications made by the application and refreshes its cache appropriately.
 * </p>
 * 
 * @author Piotr Nowicki
 * 
 */
@Singleton(name = "Cache")
@Startup
public class Cache {

    @Inject
    EntityManager em;

    @Inject
    Logger log;
    
    /**
     * The cache structure itself. It maps human readable question's number {@link Question#number} to the {@link Question}
     * instance. It is a sorted structure and sorting is based on the {@link String#compareTo(String)}.
     */
    private NavigableMap<String, Question> questions = new TreeMap<>();

    @PostConstruct
    private void init() {
        populate();
    }

    /**
     * Populates cache with data fetched from the DB.
     * <p>
     * <strong>Node:</strong> This method fills the cache <strong>without clearing it before</strong>. If the client of this
     * method requires the cache to be cleared before it's filled - he must do it by himself.
     * </p>
     */
    public void populate() {
        List<Question> questionsFromDB = em.createNamedQuery(Question.READ_ALL, Question.class).getResultList();

        for (Question question : questionsFromDB) {
            questions.put(question.getNumber(), question);
        }

        log.info("Cache populated successfully.");
    }

    /**
     * Returns the underlying cache structure. Can be used from e.g. the JSF layer.
     * 
     * @return cache
     */
    // TODO: READ Lock?
    // TODO: ImmutableMap?
    public NavigableMap<String, Question> getQuestions() {
        return questions;
    }

    /**
     * Reacts upon every DB data modification and refreshes the cache to reflect these changes to the user.
     * 
     * @param event CDI fired event
     */
    public void dataModified(@Observes DataModifiedEvent event) {
        log.log(Level.INFO, "Data modified. Event related with question: {0}.", event.getQuestion());

        // TODO: populate only part of the cache instead of recreating it from the scratch.
        // (https://github.com/PiotrNowicki/Exam-Simulator/issues/8)
        questions.clear();
        populate();
    }
}
