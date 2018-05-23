package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.ParameterDao;
import com.edu.ubosque.prg.entity.Parameter;
import com.edu.ubosque.prg.util.HibernateUtil;

public class ParameterDaoImpl implements ParameterDao{

	@Override
	public void save(Parameter parameter) {
		Session session= HibernateUtil.getSessionFactory().openSession();
		Transaction t= session.beginTransaction();
		session.save(parameter);
		t.commit();
		
		
	}

	@Override
	public Parameter getParameter(int id) {
		Session session= HibernateUtil.getSessionFactory().openSession();
		
		return (Parameter) session.load(Parameter.class, id);
	}

	@Override
	public List<Parameter> list() {
		Session session= HibernateUtil.getSessionFactory().openSession();
		Transaction t= session.beginTransaction();
		List lista= session.createQuery("from Parameter").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(Parameter parameter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(parameter);
		t.commit();
		
	}

	@Override
	public void update(Parameter parameter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(parameter);
		t.commit();
		
	}

}
