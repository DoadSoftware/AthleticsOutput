package com.athletics.doa.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.athletics.doa.AthleticsDao;
import com.athletics.model.NameSuper;
import com.athletics.model.Player;
import com.athletics.model.Schedule;
import com.athletics.model.Team;

@Transactional
@Repository("athleticsDao")
@SuppressWarnings("unchecked")
public class AthleticsDaoImpl implements AthleticsDao {

 @Autowired
 private SessionFactory sessionFactory;
 
@Override
public Player getPlayer(String valueToProcess) {
	return (Player) sessionFactory.getCurrentSession().createQuery("from Player WHERE PlayerId=" + valueToProcess).uniqueResult();  
}

@Override
public Team getTeam(String valueToProcess) {
	return (Team) sessionFactory.getCurrentSession().createQuery("from Team WHERE TeamId=" + valueToProcess).uniqueResult();  
}

@Override
public List<Team> getTeams() {
	return sessionFactory.getCurrentSession().createQuery("from Team").list();
}

@Override
public List<Player> getPlayers(String valueToProcess) {
	return sessionFactory.getCurrentSession().createQuery("from Player WHERE TeamId=" + valueToProcess).list();  
}

@Override
public List<NameSuper> getNameSupers() {
	return sessionFactory.getCurrentSession().createQuery("from NameSuper").list();
}

@Override
public List<Player> getAllPlayer() {
	return sessionFactory.getCurrentSession().createQuery("from Player").list();
}

@Override
public List<Schedule> getSchedules() {
	return sessionFactory.getCurrentSession().createQuery("from Schedule").list();
}
}