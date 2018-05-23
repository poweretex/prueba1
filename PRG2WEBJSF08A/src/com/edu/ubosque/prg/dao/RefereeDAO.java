package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Referee;

public interface RefereeDAO {
	public void save(Referee referee);

	public Referee getReferee(int id);

	public List<Referee> list();

	public void remove(Referee referee);

	public void update(Referee referee);
}
