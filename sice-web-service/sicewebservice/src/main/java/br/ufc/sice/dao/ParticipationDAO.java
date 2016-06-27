package br.ufc.sice.dao;

import java.util.List;

import br.ufc.sice.model.Event;
import br.ufc.sice.model.Participation;
import br.ufc.sice.model.User;

public class ParticipationDAO extends BaseDAO<Participation>{

	@Override
	public List loadAll() {
		return super.loadAll(Participation.class);
	}
	
	@Override
	public Participation load(long id) {
		return super.load(id, Participation.class);
	}

}
