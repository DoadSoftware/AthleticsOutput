package com.athletics.containers;

public class ScoreBug {
	
	private boolean scorebug_on_screen;
	private String team_score;
	private String scorebug_stat;
	private String last_scorebug_stat;
	
	public boolean isScorebug_on_screen() {
		return scorebug_on_screen;
	}
	public void setScorebug_on_screen(boolean scorebug_on_screen) {
		this.scorebug_on_screen = scorebug_on_screen;
	}
	public String getTeam_score() {
		return team_score;
	}
	public void setTeam_score(String team_score) {
		this.team_score = team_score;
	}
	public String getScorebug_stat() {
		return scorebug_stat;
	}
	public void setScorebug_stat(String scorebug_stat) {
		this.scorebug_stat = scorebug_stat;
	}
	public String getLast_scorebug_stat() {
		return last_scorebug_stat;
	}
	public void setLast_scorebug_stat(String last_scorebug_stat) {
		this.last_scorebug_stat = last_scorebug_stat;
	}
	
}