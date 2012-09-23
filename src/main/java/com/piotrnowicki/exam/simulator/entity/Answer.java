package com.piotrnowicki.exam.simulator.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(length=16000)
	private String content;

	@Column(length=16000)
	private String explanaition;

	public Answer() {
	}

	public Answer(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		
		builder.append("id", id);
		builder.append("content", content);
		
		return builder.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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