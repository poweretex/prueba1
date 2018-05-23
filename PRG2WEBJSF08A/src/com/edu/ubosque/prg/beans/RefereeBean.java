package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.edu.ubosque.prg.dao.RefereeDAO;
import com.edu.ubosque.prg.dao.impl.RefereeDAOImpl;
import com.edu.ubosque.prg.entity.Referee;

@ManagedBean
@SessionScoped
public class RefereeBean {
	private Referee referee;
	private DataModel listaReferes;

	public String prepararAdicionarReferee() {
		referee = new Referee();
		referee.setState("A");
		return "userRegister";
	}

	public String prepararModificarReferee() {
		referee = (Referee) (listaReferes.getRowData());
		return "userRegister";
	}

	public String eliminarReferee() {
		Referee refereeTemp = (Referee) (listaReferes.getRowData());
		RefereeDAO dao = new RefereeDAOImpl();
		refereeTemp.setState("I");
		dao.update(referee);
		return "listarUsuarios";
	}

	public String adicionarReferee() {
		RefereeDAO dao = new RefereeDAOImpl();
		dao.save(referee);
		return "listarUsuarios";
	}

	public String modificarReferee() {
		RefereeDAO dao = new RefereeDAOImpl();
		dao.update(referee);
		return "listarUsuarios";
	}

	public Referee getReferee() {
		return referee;
	}

	public void setReferee(Referee referee) {
		this.referee = referee;
	}

	public DataModel getListarReferees() {
		List<Referee> lista = new RefereeDAOImpl().list();
		listaReferes = new ListDataModel(lista);
		return listaReferes;
	}
}
