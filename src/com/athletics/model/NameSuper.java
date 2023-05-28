package com.athletics.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;

@SuppressWarnings("unused")
@Entity
@Table(name = "NameSupers")
public class NameSuper
{
  @Id
  @Column(name = "NAMESUPERID")
  private int namesuperId;

  @Column(name = "FIRSTNAME")
  private String firstname;

  @Column(name = "SURNAME")
  private String surname;
  
  @Column(name = "Prompt")
  private String prompt;

  @Column(name = "SUBLINE")
  private String subLine;
  
  @Column(name = "SubHeader")
  private String subHeader;

public int getNamesuperId() {
	return namesuperId;
}

public void setNamesuperId(int namesuperId) {
	this.namesuperId = namesuperId;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public String getPrompt() {
	return prompt;
}

public void setPrompt(String prompt) {
	this.prompt = prompt;
}

public String getSubLine() {
	return subLine;
}

public void setSubLine(String subLine) {
	this.subLine = subLine;
}

public String getSubHeader() {
	return subHeader;
}

public void setSubHeader(String subHeader) {
	this.subHeader = subHeader;
}

}