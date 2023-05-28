package com.athletics.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;

@Entity
@Table(name = "Schedule")
public class Schedule
{

  @Id
  @Column(name = "ScheduleId")
  private int scheduleId;
	
  @Column(name = "ScheduleDate")
  private String scheduleDate;

  @Column(name = "ScheduleTime")
  private String scheduleTime;

  @Column(name = "EventName")
  private String eventName;
  
  @Column(name = "Specification")
  private String specification;
  
  @Column(name = "Gender")
  private String gender;
  
  @Column(name = "Round")
  private String round;

public int getScheduleId() {
	return scheduleId;
}

public void setScheduleId(int scheduleId) {
	this.scheduleId = scheduleId;
}

public String getScheduleDate() {
	return scheduleDate;
}

public void setScheduleDate(String scheduleDate) {
	this.scheduleDate = scheduleDate;
}

public String getScheduleTime() {
	return scheduleTime;
}

public void setScheduleTime(String scheduleTime) {
	this.scheduleTime = scheduleTime;
}

public String getEventName() {
	return eventName;
}

public void setEventName(String eventName) {
	this.eventName = eventName;
}

public String getSpecification() {
	return specification;
}

public void setSpecification(String specification) {
	this.specification = specification;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getRound() {
	return round;
}

public void setRound(String round) {
	this.round = round;
}

}