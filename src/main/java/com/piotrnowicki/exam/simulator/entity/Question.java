package com.piotrnowicki.exam.simulator.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Single question of the exam.
 * 
 * @author Piotr Nowicki
 * 
 */
@Entity
@NamedQuery(name = Question.READ_ALL, query = "SELECT q FROM Question q ORDER BY q.number")
public class Question implements Serializable {

    /**
     * Named query for returning all {@link Question}s
     */
    public final static String READ_ALL = "Question.ReadAll";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * <p>
     * Human readable question's number.
     * </p>
     * 
     * <p>
     * It is shown to the user and it's also used in the sorting.
     * </p>
     * 
     */
    @NotNull
    @Size(min = 1)
    private String number;

    /**
     * Short summary of the question. It might be treated as a question's title (not its content!)
     */
    @NotNull
    @Size(min = 5)
    private String summary;

    /**
     * <p>
     * Content of the question.
     * </p>
     * <p>
     * You can use the Markdown syntax here.
     * </p>
     */
    @NotNull
    @Size(min = 10)
    @Column(length = 16000)
    private String content;

    /**
     * <p>
     * Explanation for the question's answer.
     * </p>
     * <p>
     * Mind that each {@link Answer} can have its own explanation independent on the Question's one.
     * </p>
     */
    @Column(length = 16000)
    private String explanaition;

    /**
     * <p>
     * Possible answers to the question.
     * </p>
     * 
     * <p>
     * Loaded eagerly as there is no much point in getting a {@link Question} without the {@link Answer}s already populated. <br />
     * The order of the answers must be preserved as we might depend on it in the answer.<br />
     * Finally, the {@link Answer} cannot exist if there is no {@link Question} related with it.
     * </p>
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn(updatable = false)
    private List<Answer> answers = new LinkedList<>();

    /**
     * Constructor for the JPA purposes.
     */
    public Question() {
    }

    /**
     * Convenient constructor.
     * 
     * @param number
     * @param summary
     * @param content
     */
    public Question(String number, String summary, String content) {
        this.number = number;
        this.summary = summary;
        this.content = content;
    }

    /**
     * Adds given answers to the question. Collection of {@link Answer}s is always initialized, so user doesn't have to bother
     * about it. <br />
     * This method doesn't pay attention to the duplication of {@link Answer}s - it just adds everything you pass it and it
     * won't complain.
     * 
     * @param answers to be added
     */
    public void addAnswers(Answer... answers) {
        for (Answer answer : answers) {
            this.answers.add(answer);
        }
    }

    // -------------------------------------------------------------------------------||
    // Getters and setters -----------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getExplanaition() {
        return explanaition;
    }

    public void setExplanaition(String explanaition) {
        this.explanaition = explanaition;
    }

    // -------------------------------------------------------------------------------||
    // Other contracts ---------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

        hashCodeBuilder.append(id);
        hashCodeBuilder.append(number);
        hashCodeBuilder.append(summary);

        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Question)) {
            return false;
        }

        Question other = (Question) obj;
        EqualsBuilder equalsBuilder = new EqualsBuilder();

        equalsBuilder.append(number, other.number);
        equalsBuilder.append(summary, other.summary);

        return equalsBuilder.isEquals();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);

        builder.append("id", id);
        builder.append("number", number);
        builder.append("summary", summary);

        return builder.toString();
    }
}