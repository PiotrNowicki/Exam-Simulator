package com.piotrnowicki.exam.simulator.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Single Answer to the given {@link Question}.
 * 
 * @author Piotr Nowicki
 * 
 */
@Entity
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * <p>
     * Content of the answer.
     * </p>
     * <p>
     * You can use the Markdown syntax here.
     * </p>
     */
    @NotNull
    @Column(length = 16000)
    private String content;

    /**
     * Answer-local explanation, e.g. "the foo occurs, so this answer is not correct". It's independent on the {@link Question}
     * 's global expalation.
     */
    @Column(length = 16000)
    private String explanation;

    @Column(name = "isCorrect")
    private boolean isCorrect;

    /**
     * Constructor for the JPA purposes.
     */
    public Answer() {
    }

    /**
     * Convenient constructor.
     * 
     * @param content of the question
     */
    public Answer(String content) {
        this.content = content;
    }

    // -------------------------------------------------------------------------------||
    // Getters and setters -----------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    // -------------------------------------------------------------------------------||
    // Other contracts ---------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);

        builder.append("id", id);
        builder.append("content", content);
        builder.append("isCorrect", isCorrect);

        return builder.toString();
    }

}