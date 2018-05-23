package com.edu.ubosque.prg.dao.impl;

import com.edu.ubosque.prg.dao.ScheduleDao;
import com.edu.ubosque.prg.entity.Schedule;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;

public class ScheduleDaoImpl implements ScheduleDao {
	
	
@Override	
	public void save(Schedule schedule) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(schedule);
		t.commit();
	}
@Override
	public List<Schedule> listado() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from schedule").list();
		t.commit();
		return lista;
	}
@Override
	public void modificar(int id,Schedule schedule) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Transaction t=session.beginTransaction();
		
		t.commit();
	}

}
