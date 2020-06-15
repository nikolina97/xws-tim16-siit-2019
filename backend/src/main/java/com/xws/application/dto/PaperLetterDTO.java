package com.xws.application.dto;

public class PaperLetterDTO {

	private String paper;
	private String letter;

	public PaperLetterDTO() {}

	public PaperLetterDTO(String paper, String letter) {
		this.paper = paper;
		this.letter = letter;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

}
