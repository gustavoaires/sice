package br.ufc.sice.dao;

import java.util.List;

import br.ufc.sice.model.User;

public class UserDAO extends BaseDAO<User>{

	@Override
	public List loadAll() {
		return super.loadAll(User.class);
	}

	@Override
	public User load(long id) {
		return super.load(id, User.class);
	}

}
