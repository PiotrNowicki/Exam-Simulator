package com.piotrnowicki.exam.simulator.control;

import com.piotrnowicki.exam.simulator.entity.Question;

/**
 * Event fired whenever the underlying {@link Question} (e.g. in the database) has changed and some action should be taken (e.g.
 * the cache should be updated or recreated.)
 * 
 * @author Piotr Nowicki
 * 
 */
// TODO: Make this Event more generic - the payload doesn't have to be only the Question object.
// (https://github.com/PiotrNowicki/Exam-Simulator/issues/23)
public class DataModifiedEvent {

    /**
     * Question which was changed and triggered the event.
     */
    private Question question;

    /**
     * Create an event which was triggered by the <code>question</code> parameter.
     * 
     * @param question
     */
    public DataModifiedEvent(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
