package com.athletics.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;

@Entity
@Table(name = "Players")
public class Player
{

  @Id
  @Column(name = "PLAYERID")
  private int playerId;
	
  @Column(name = "FULLNAME")
  private String full_name;

  @Column(name = "FIRSTNAME")
  private String firstname;

  @Column(name = "SURNAME")
  private String surname;
  
  @Column(name = "TICKERNAME")
  private String ticker_name;
  
  @Column(name = "Age")
  private int age;
  
  @Column(name = "TEAMID")
  private Integer teamId;
  
  public Player() {
		super();
  }

public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public String getFull_name() {
	return full_name;
}

public void setFull_name(String full_name) {
	this.full_name = full_name;
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

public String getTicker_name() {
	return ticker_name;
}

public void setTicker_name(String ticker_name) {
	this.ticker_name = ticker_name;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public Integer getTeamId() {
	return teamId;
}

public void setTeamId(Integer teamId) {
	this.teamId = teamId;
}

}