package br.ufc.sice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.sice.model.Event;
import br.ufc.sice.model.Participation;
import br.ufc.sice.model.SubEvent;
import br.ufc.sice.model.User;

public class SubEventDAO extends BaseDAO<SubEvent>{
	
	@Autowired
	UserDAO userDAO;

	@Autowired
	ParticipationDAO participationDAO;

	@Override
	public List loadAll() {
		return super.loadAll(SubEvent.class);
	}
	
	@Override
	public SubEvent load(long id) {
		return super.load(id, SubEvent.class);
	}
	
	public List loadAllFromEvent(long idEvent){
		List<SubEvent> list = super.loadAll(SubEvent.class);
		List<SubEvent> retList = new ArrayList<>();
		for(SubEvent event : list){
			if(event.getId_event() == idEvent){
				retList.add(event);
			}
		}
		
		return retList;
	}
	
	public List loadUsersFromSubEvents(long idEvent){
		List<User> users = new ArrayList<>();
		
		List<Participation> parts = participationDAO.loadAll();

		for(Participation p : parts){
			if(p.getIdSubEvent() == idEvent){
				users.add(userDAO.load(p.getIdUser()));
			}
		}
		
		return users;
	}

}
