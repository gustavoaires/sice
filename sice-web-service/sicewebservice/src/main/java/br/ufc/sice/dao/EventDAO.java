package br.ufc.sice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.sice.model.Event;
import br.ufc.sice.model.SubEvent;
import br.ufc.sice.model.User;
import br.ufc.sice.model.UserSummary;

public class EventDAO extends BaseDAO<Event>{
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	SubEventDAO subEventDAO;

	@Autowired
	ParticipationDAO participationDAO;

	@Override
	public List loadAll() {
		return super.loadAll(Event.class);
	}
	
	public List loadAllFilter(String filter){
		List<Event> result = loadAll();
		
		for(int i = 0; i<result.size(); i++){
			Event event = result.get(i);
			if(!event.getTitle().contains(filter) && !event.getSubtitle().contains(filter) && !event.getDescription().contains(filter)){
				result.remove(i);
				i--;
			}
		}
		
		return result;
	}
	
	@Override
	public Event load(long id) {
		return super.load(id, Event.class);
	}
	
	public List summary(long id) {
		List<SubEvent> subEvents = subEventDAO.loadAllFromEvent(id);
		System.out.println(subEvents);
		List<UserSummary> users = new ArrayList<>();
		
		for(SubEvent subEvent : subEvents){
			List<User> seUsers = subEventDAO.loadUsersFromSubEvents(subEvent.getId());
			for(User user : seUsers){
				if(users.contains(user)){
					users.get(users.indexOf(user)).addHours(subEvent.getDuration());
				}else{
					UserSummary us = new UserSummary(user.getId(), user.getName(), user.getEmail(), subEvent.getDuration());
					users.add(us);
				}
			}
		}
		
		return users;
	}

}
