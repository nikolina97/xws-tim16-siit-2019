package com.xws.application.dto;

public class ScientificPaperMetadataSearchDTO {
	
	private String category;
	
	private String title;
	
	private String dateRecieved;
	
	private String dateRevised;
	
	private String dateAccepted;
	
	private String authorFirstName;
	 
	private String authorLastName;
	
	private Integer version;
	
	public ScientificPaperMetadataSearchDTO() {
		super();
	}
	
	public ScientificPaperMetadataSearchDTO(String category, String title, String dateRecieved, String dateRevised,
			String dateAccepted, String authorFirstName, String authorLastName, Integer version) {
		super();
		this.category = category;
		this.title = title;
		this.dateRecieved = dateRecieved;
		this.dateRevised = dateRevised;
		this.dateAccepted = dateAccepted;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.version = version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDateRecieved() {
		return dateRecieved;
	}

	public void setDateRecieved(String dateRecieved) {
		this.dateRecieved = dateRecieved;
	}

	public String getDateRevised() {
		return dateRevised;
	}

	public void setDateRevised(String dateRevised) {
		this.dateRevised = dateRevised;
	}

	public String getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(String dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
