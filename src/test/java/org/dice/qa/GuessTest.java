package org.dice.qa;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.dice.qa.AnswerContainer.AnswerType;
import org.dice.qa.impl.ExampleQASystem;
import org.junit.Test;

public class GuessTest {

	private AbstractQASystem system = new ExampleQASystem();
	
	@Test
	public void checkUris() {
		Set<String> answers = new HashSet<String>();
		answers.add("http://dbpedia.org/resource/Test1");
		answers.add("urn://dbpedia.org/resource/Test2");
		assertEquals(AnswerType.RESOURCE, system.guessAnswerType(answers));
	}
	
	@Test
	public void checkedMixed() {
		Set<String> answers = new HashSet<String>();
		answers.add("http://dbpedia.org/resource/Test1");
		answers.add("123.5");
		assertEquals(AnswerType.LITERAL, system.guessAnswerType(answers));
	}
	
	@Test
	public void checkBoolean() {
		Set<String> answers = new HashSet<String>();
		answers.add("true");
		assertEquals(AnswerType.BOOLEAN, system.guessAnswerType(answers));
		answers = new HashSet<String>();
		answers.add("FALSE");
		assertEquals(AnswerType.BOOLEAN, system.guessAnswerType(answers));
	}
	
	@Test
	public void checkString() {
		Set<String> answers = new HashSet<String>();
		answers.add("true");
		answers.add("hope");
		assertEquals(AnswerType.LITERAL, system.guessAnswerType(answers));
	}
	
	@Test
	public void checkNumber() {
		Set<String> answers = new HashSet<String>();
		answers.add("\"123\"^^xsd:test");
		assertEquals(AnswerType.LITERAL, system.guessAnswerType(answers));
	}
	
	@Test
	public void checkDate() {
		Set<String> answers = new HashSet<String>();
		answers.add("23.03.1999");
		assertEquals(AnswerType.LITERAL, system.guessAnswerType(answers));
	}
	
}
