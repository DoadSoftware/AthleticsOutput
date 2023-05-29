package com.athletics.model;

import java.util.List;

public class Athlete {

  private int athleteId;

  private String surname;

  private String fullName;

  private String teamname;
  
  private String stats;
  
  private List<Stat> attempts_results;
  
public Athlete() {
	super();
}

public Athlete(int athleteId, String surname, String fullName, String teamname) {
	super();
	this.athleteId = athleteId;
	this.surname = surname;
	this.fullName = fullName;
	this.teamname = teamname;
}

public List<Stat> getAttempts_results() {
	return attempts_results;
}

public void setAttempts_results(List<Stat> attempts_results) {
	this.attempts_results = attempts_results;
}

public Athlete(int athleteId, String fullName, String teamname) {
	super();
	this.athleteId = athleteId;
	this.fullName = fullName;
	this.teamname = teamname;
}

public Athlete(String fullName, String teamname, String stats, int athleteId) {
	super();
	this.fullName = fullName;
	this.teamname = teamname;
	this.stats = stats;
	this.athleteId = athleteId;
}

public Athlete(int athleteId, String surname, String fullName, String teamname, String stats) {
	super();
	this.athleteId = athleteId;
	this.surname = surname;
	this.fullName = fullName;
	this.teamname = teamname;
	this.stats = stats;
}

public int getAthleteId() {
	return athleteId;
}

public void setAthleteId(int athleteId) {
	this.athleteId = athleteId;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public String getTeamname() {
	return teamname;
}

public void setTeamname(String teamname) {
	this.teamname = teamname;
}

public String getStats() {
	return stats;
}

public void setStats(String stats) {
	this.stats = stats;
}

@Override
public String toString() {
	return "Athlete [athleteId=" + athleteId + ", surname=" + surname + ", fullName=" + fullName + ", teamname="
			+ teamname + ", stats=" + stats + ", attempts_results=" + attempts_results + "]";
}

}