package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.Schedule;
import java.util.List;

public interface ScheduleDao {

	public void save(Schedule schedule);
	public List<Schedule> listado();
	public void modificar(int id, Schedule schedule);
	
}
