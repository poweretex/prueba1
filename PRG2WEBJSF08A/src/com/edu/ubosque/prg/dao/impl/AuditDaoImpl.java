package com.edu.ubosque.prg.dao.impl;


import com.edu.ubosque.prg.dao.AuditDao;
import com.edu.ubosque.prg.entity.Audit;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;

public class AuditDaoImpl implements AuditDao {

@Override
	public void save(Audit auditoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
	}
@Override
	public List<Audit> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from audit").list();
		t.commit();
		return lista;
	}

}
