package it.polito.tdp.spellchecker.model;

public class RichWord {

	private String input;
	private boolean isCorrect;
	
	public RichWord(String input) {
		super();
		this.input = input;
		this.isCorrect = false;
	}

	public void setCorrect() {
		this.isCorrect = true;
	}

	public String getInput() {
		return input;
	}

	public boolean isCorrect() {
		return isCorrect;
	}
	
	
}
