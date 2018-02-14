package org.dice.qa;

import java.io.IOException;
import java.util.Set;
import org.aksw.qa.commons.datastructure.Question;
import org.dice.qa.AnswerContainer.AnswerType;
import org.dice.util.GerbilFinalResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * An abstract layer for the qa system
 *
 */
public abstract class AbstractQASystem implements QASystem {

	@SuppressWarnings("unchecked")
	private JSONObject getAnswersAsQALD(Set<String> answers, AnswerType answerType) throws IOException, ParseException {
		
		String varName = answerType.toString().toLowerCase();
		JSONObject answerJson = new JSONObject();
		JSONObject head = new JSONObject();
		JSONArray varArr = new JSONArray();
		varArr.add(varName);
		head.put("vars", varArr);
		JSONObject results = new JSONObject();
		JSONArray bindings = new JSONArray();
		for (String answer : answers) {
			JSONObject binding = new JSONObject();
			JSONObject var = new JSONObject();
			String answerT = answerType.toString().toLowerCase();
			if("resource".equals(answerT)) {
				answerT = "uri";
			}
			var.put("type", answerT);
			var.put("value", answer);
			binding.put(varName, var);
			bindings.add(binding);
		}
		results.put("bindings", bindings);
		answerJson.put("head", head);
		answerJson.put("results", results);

		return answerJson;
	}

	@Override
	public JSONObject getAnswersToQuestion(final Question q, String lang) {
		// gets the prefered question
		String question = q.getLanguageToQuestion().get(lang);

		// retrieve the answers from the system
		AnswerContainer answers = retrieveAnswers(question, lang);
		// sets the answers
		q.setGoldenAnswers(lang, answers.getAnswers());
		// sets the answertype as lower case (e.g. resource)
		q.setAnswerType(answers.getType().toString().toLowerCase());
		//
		q.setSparqlQuery(lang, answers.getSparqlQuery());
		try {
			q.setAnswerAsQALDJSON(getAnswersAsQALD(answers.getAnswers(), answers.getType()));

			// return the answer as a valid qaldJSON string
			GerbilFinalResponse resp = new GerbilFinalResponse();

			resp.setQuestions(q, lang);
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json;

			json = ow.writeValueAsString(resp);
			return (JSONObject) new JSONParser().parse(json);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves the Answers from the System for a particular question and its
	 * language. <br/>
	 * It has to set the answers as a set, the answer types and the sparql query
	 * used
	 * 
	 * @param question
	 * @param lang
	 * @return
	 */
	public abstract AnswerContainer retrieveAnswers(String question, String lang);
}
