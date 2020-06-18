package com.xws.application.dto;

public class ReviewDTO {

	private String review;
	private String paperId;

	public ReviewDTO() {
	}

	public ReviewDTO(String review, String paperId) {
		this.review = review;
		this.paperId = paperId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

}
