package com.edu.ubosque.prg.beans;
import com.edu.ubosque.prg.dao.AuditDao;
import com.edu.ubosque.prg.dao.impl.*;
import com.edu.ubosque.prg.entity.Audit;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@SessionScoped
public class AuditBean {
	
	private Audit auditoria;
	private DataModel listaAuditoria;

	public String adicionarAuditoria(Audit auditoria) {
		AuditDao dao = new AuditDaoImpl();
		dao.save(auditoria);
		return "index";
	}

	public DataModel getListarAuditoria() {
		List<Audit> lista = new AuditDaoImpl().list();
		listaAuditoria = new ListDataModel(lista);
		return listaAuditoria;
	}

}
