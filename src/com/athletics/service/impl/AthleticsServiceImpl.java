package com.athletics.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.athletics.doa.AthleticsDao;
import com.athletics.model.NameSuper;
import com.athletics.model.Player;
import com.athletics.model.Schedule;
import com.athletics.model.Team;
import com.athletics.service.AthleticsService;

@Service("athleticsService")
@Transactional
public class AthleticsServiceImpl implements AthleticsService {

 @Autowired
 private AthleticsDao athleticsDao;
 
@Override
public Player getPlayer(String valueToProcess) {
	return athleticsDao.getPlayer(valueToProcess);
}

@Override
public Team getTeam(String valueToProcess) {
	return athleticsDao.getTeam(valueToProcess);
}

@Override
public List<Team> getTeams() {
	return athleticsDao.getTeams();
}

@Override
public List<Player> getPlayers(String valueToProcess) {
	return athleticsDao.getPlayers(valueToProcess);
}

@Override
public List<NameSuper> getNameSupers() {
	return athleticsDao.getNameSupers();
}

@Override
public List<Player> getAllPlayer() {
	return athleticsDao.getAllPlayer();
}

@Override
public List<Schedule> getSchedules() {
	return athleticsDao.getSchedules();
}

}