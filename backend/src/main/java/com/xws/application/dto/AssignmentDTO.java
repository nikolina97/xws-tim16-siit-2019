package com.xws.application.dto;

import com.xws.application.model.ScientificPaper;

public class AssignmentDTO {

	private ScientificPaper paper;
	private String status;

	public AssignmentDTO() {
	}

	public AssignmentDTO(ScientificPaper paper, String status) {
		this.paper = paper;
		this.status = status;
	}

	public ScientificPaper getPaper() {
		return paper;
	}

	public void setPaper(ScientificPaper paper) {
		this.paper = paper;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
