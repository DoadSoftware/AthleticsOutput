package com.athletics.service;

import java.util.List;

import com.athletics.model.NameSuper;
import com.athletics.model.Player;
import com.athletics.model.Schedule;
import com.athletics.model.Team;

public interface AthleticsService {
  Player getPlayer(String valueToProcess);
  Team getTeam(String valueToProcess);
  List<Player> getPlayers(String valueToProcess);
  List<Player> getAllPlayer();
  List<Team> getTeams();
  List<NameSuper> getNameSupers();
  List<Schedule> getSchedules();
}