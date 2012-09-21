package com.piotrnowicki.exam.simulator.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private Boolean isCorrect;

	@NotNull
	@Size(min=1)
	private String content;

	private String explanaition;

	public Answer() {
	}

	public Answer(String content, Boolean isCorrect) {
		this.content = content;
		this.isCorrect = isCorrect;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getExplanaition() {
		return explanaition;
	}

	public void setExplanaition(String explanaition) {
		this.explanaition = explanaition;
	}

	public Long getId() {
		return id;
	}

}