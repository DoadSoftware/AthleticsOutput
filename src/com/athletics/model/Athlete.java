package com.athletics.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Athlete")
@XmlAccessorType(XmlAccessType.FIELD)
public class Athlete {

  @XmlElement(name = "athleteId")
  private int athleteId;

  @XmlElement(name = "surname")
  private String surname;

  @XmlElement(name = "fullName")
  private String fullName;

  @XmlElement(name = "teamname")
  private String teamname;
  
  @XmlElement(name = "stats")
  private String stats;

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
			+ teamname + ", stats=" + stats + "]";
}

}