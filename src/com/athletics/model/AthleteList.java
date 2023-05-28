package com.athletics.model;

import java.util.List;

public class AthleteList {

  private Integer athleteListId;
  private String header;
  private List<Athlete> athletes;

public AthleteList() {
	super();
}


public AthleteList(Integer athleteListId, String header) {
	super();
	this.athleteListId = athleteListId;
	this.header = header;
}


public Integer getAthleteListId() {
	return athleteListId;
}

public void setAthleteListId(Integer _athleteListId) {
	athleteListId = _athleteListId;
}

public String getHeader() {
	return header;
}

public void setHeader(String header) {
	this.header = header;
}

public List<Athlete> getAthletes() {
	return athletes;
}

public void setAthletes(List<Athlete> athletes) {
	this.athletes = athletes;
}

@Override
public String toString() {
	return "AthleteList [athleteListId=" + athleteListId + ", header=" + header + ", athletes=" + athletes + "]";
}
  
}