package com.edu.ubosque.prg.beans;



import com.edu.ubosque.prg.dao.ScheduleDao;
import com.edu.ubosque.prg.dao.impl.ScheduleDaoImpl;
import com.edu.ubosque.prg.entity.Schedule;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@SessionScoped
public class ScheduleBean {

	
	private Schedule auditoria;
	private DataModel listaSchedule;

	public String adicionarResultado(Schedule schedule) {
		ScheduleDao dao = new ScheduleDaoImpl();
		dao.save(schedule);
		return "index";
	}

	public DataModel getListarSchedule() {
		List<Schedule> lista = new ScheduleDaoImpl().listado();
		listaSchedule = new ListDataModel(lista);
		return listaSchedule;
	}


}
