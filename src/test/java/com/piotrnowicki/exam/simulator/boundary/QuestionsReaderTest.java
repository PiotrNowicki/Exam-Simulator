package com.piotrnowicki.exam.simulator.boundary;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.NavigableMap;
import java.util.TreeMap;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.piotrnowicki.exam.simulator.control.Cache;
import com.piotrnowicki.exam.simulator.entity.Question;

public class QuestionsReaderTest {
	
	private QuestionsReader cut;
	
	@BeforeMethod
	public void beforeMethod() {
		cut = new QuestionsReader();
		cut.cache = Mockito.mock(Cache.class);
	}

	@Test
	public void getQuestionById() {
		// given
		String questionNum = "Q_NUM";
		Question expected = new Question();
		
		NavigableMap<String, Question> questions = new TreeMap<>();
		questions.put(questionNum, expected);
		
		// when
		when(cut.cache.getQuestions()).thenReturn(questions);
		Question actual = cut.getQuestionByNumber(questionNum);
		
		// then
		assertThat(actual).isEqualTo(expected);
	}

}
