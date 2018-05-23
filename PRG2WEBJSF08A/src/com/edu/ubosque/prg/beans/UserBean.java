package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@SessionScoped
public class UserBean {

	private User usuario;
	private DataModel listaUsuarios;

	public String prepararAdicionarUsuario() {
		usuario = new User();
		usuario.setActive("A");
		return "userRegister";
	}

	public String prepararModificarUsuario() {
		usuario = (User) (listaUsuarios.getRowData());
		return "userRegister";
	}

	public String eliminarUsuario() {
		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		usuarioTemp.setActive("I");
		dao.update(usuarioTemp);
		return "listarUsuarios";
	}

	public String adicionarUsuario() {
		UserDAO dao = new UserDAOImpl();
		dao.save(usuario);
		return "listarUsuarios";
	}

	public String modificarUsuario() {
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		return "listarUsuarios";
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public DataModel getListarUsuarios() {
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel(lista);
		return listaUsuarios;
	}

}
