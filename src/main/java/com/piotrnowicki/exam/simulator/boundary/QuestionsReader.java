package com.piotrnowicki.exam.simulator.boundary;

import java.util.NavigableMap;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.piotrnowicki.exam.simulator.control.Cache;
import com.piotrnowicki.exam.simulator.entity.Question;

/**
 * Read-only functionality of the {@link Question} entities.
 * 
 * <p>
 * All entities are accessible from the cache and <strong>not</strong> from the DB directly.
 * </p>
 * 
 * @author Piotr Nowicki
 * 
 */
@Stateless
public class QuestionsReader {

    @Inject
    Cache cache;

    /**
     * Get {@link Question} based on it's human-readable number: {@link Question#number}.
     * 
     * @param questionNumber that is to be fetched.
     * 
     * @return {@link Question} or null if none could be found.
     */
    public Question getQuestionByNumber(String questionNumber) {
        Question question = cache.getQuestions().get(questionNumber);

        return question;
    }

    /**
     * Returns the underlying cache.
     * 
     * @return 
     */
    public NavigableMap<String, Question> getQuestions() {
        return cache.getQuestions();
    }
}
