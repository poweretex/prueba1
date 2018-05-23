package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.util.Date;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.entity.New;

@ManagedBean
@SessionScoped
public class NewBean {
private New noticia;
private DataModel listaNoticias;
public String prepararAdicionarNoticia(){
	noticia = new New();
	noticia.setState("P");
	Date fecha= new Date();
	fecha.getTime();
	noticia.setDateNews(fecha);
	return "userRegister";
}
public String prepararModificarNoticia(){
	noticia= (New) (listaNoticias.getRowData());
	return "userRegister";
}
public String eliminarNoticia(){
	New noticiaTemp = (New) (listaNoticias.getRowData());
	NewDAO dao= new NewDAOImpl();
	noticiaTemp.setState("O");
	dao.update(noticiaTemp);
	return "listaUsuarios";
}
public String adicionarNoticia(){
	NewDAO dao= new NewDAOImpl();
	dao.save(noticia);
	return "listarUsuarios";
}
public String modificarNoticia(){
	NewDAO dao= new NewDAOImpl();
	dao.update(noticia);
	return "listarUsuarios";
	
}
public New getNoticia(){
	return noticia;
}
public void setNoticia(New noticia){
	this.noticia= noticia;
}
public DataModel getListarNoticias(){
	List<New> lista= new NewDAOImpl().list();
	listaNoticias= new ListDataModel(lista);
	return listaNoticias;
	
}
}
