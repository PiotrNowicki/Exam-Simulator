package com.piotrnowicki.exam.simulator.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min=1)
	private String number;

	@NotNull
	@Size(min=1)
	private String summary;

	@NotNull
	@Size(min=1)
	private String content;

	private String explanaition;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@OrderColumn
	private List<Answer> answers = new LinkedList<>();

	public Question() {
	}

	public Question(String number, String summary, String content) {
		this.number = number;
		this.summary = summary;
		this.content = content;
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

	public Long getId() {
		return id;
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

	public void addAnswers(Answer... answers) {
		for (Answer answer : answers) {
			this.answers.add(answer);
		}
	}
}