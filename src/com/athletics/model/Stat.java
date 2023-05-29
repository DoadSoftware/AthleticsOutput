package com.athletics.model;

public class Stat {

  private int statId;

  private String attempt;

  private String firstResult;

  private String secondResult;
  
  private String thirdResult;

public Stat() {
	super();
}

public Stat(int statId, String attempt) {
	super();
	this.statId = statId;
	this.attempt = attempt;
}

public Stat(int statId, String attempt, String firstResult, String secondResult, String thirdResult) {
	super();
	this.statId = statId;
	this.attempt = attempt;
	this.firstResult = firstResult;
	this.secondResult = secondResult;
	this.thirdResult = thirdResult;
}

public int getStatId() {
	return statId;
}

public void setStatId(int statId) {
	this.statId = statId;
}


public String getAttempt() {
	return attempt;
}

public void setAttempt(String attempt) {
	this.attempt = attempt;
}

public String getFirstResult() {
	return firstResult;
}

public void setFirstResult(String firstResult) {
	this.firstResult = firstResult;
}

public String getSecondResult() {
	return secondResult;
}

public void setSecondResult(String secondResult) {
	this.secondResult = secondResult;
}

public String getThirdResult() {
	return thirdResult;
}

public void setThirdResult(String thirdResult) {
	this.thirdResult = thirdResult;
}

@Override
public String toString() {
	return "Stat [statId=" + statId + ", Attempt=" + attempt + ", firstResult=" + firstResult + ", secondResult="
			+ secondResult + ", thirdResult=" + thirdResult + "]";
}

}