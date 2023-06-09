package com.athletics.model;

import java.util.List;

public class Match {

  private String status;
  private List<NameSuper> nameSuper;
  private List<Schedule> schedules;
  private List<AthleteList> athleteList;
  List<String> filenames;
  
public String getStatus() {
	return status;
}

public List<String> getFilenames() {
	return filenames;
}

public void setFilenames(List<String> filenames) {
	this.filenames = filenames;
}

public void setStatus(String status) {
	this.status = status;
}

public List<Schedule> getSchedules() {
	return schedules;
}

public void setSchedules(List<Schedule> schedules) {
	this.schedules = schedules;
}

public List<AthleteList> getAthleteList() {
	return athleteList;
}

public void setAthleteList(List<AthleteList> athleteList) {
	this.athleteList = athleteList;
}

public List<NameSuper> getNameSuper() {
	return nameSuper;
}

public void setNameSuper(List<NameSuper> nameSuper) {
	this.nameSuper = nameSuper;
}
  
}