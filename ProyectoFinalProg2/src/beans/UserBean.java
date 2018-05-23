package beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import dao.ParameterDao;
import dao.UserDao;
import entity.Parameter;
import entity.User;
import impl.ParameterDaoImpl;
import impl.UserDaoImpl;
import util.Email;
import util.InfoUser;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserBean {

	private User user;
	private DataModel<User> listaUsuarios;
	private String usuario;
	private String password;
	private List<User> listaValidar;
	public static String MD5 = "MD5";
	private static Email email;
	private DataModel<User> filtrados;
	private String buscar;
	private List<User> listaFiltrados;
	private Logger log = Logger.getLogger(UserBean.class);
	private String contrasenia;
	private String lastPassword;
	private String confLastPassword;
	private InfoUser usuarioLogin;

	private boolean on;
	private boolean off;
public String cambiarPassword(){
	UserDao dao= new UserDaoImpl();
	if (lastPassword.equals(confLastPassword)){
		User userTemp= listaValidar.get(0);
		userTemp.setPassword(getStringMessageDigest(lastPassword, MD5));
		userTemp.setUserType("usuario");
		dao.update(userTemp);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se cambio correctamente la Password"));
		
		return "Login.xhtml";
	}else{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Las passwords no coinciden ", "Las passwords no coinciden"));
		return "cambiarContrase�a.xhtml";
	}
	
	
}
	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public String getConfLastPassword() {
		return confLastPassword;
	}

	public void setConfLastPassword(String confLastPassword) {
		this.confLastPassword = confLastPassword;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public boolean isOff() {
		return off;
	}

	public void setOff(boolean off) {
		this.off = off;
	}

	public void addMessage() {
		String summary = on? "Registro Activo" : "Registro Inactivo";
		System.out.println(summary);
		if (summary.equals("Registro Activo")) {
			this.activarRegistro();
			System.out.println("Se activ�");
		} else {
			this.desactivarRegistro();
			System.out.println("Se desactiv�");
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public DataModel<User> getFiltrados() {

		return filtrados;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String prepararAdicionarUsuario() {
		List<Parameter> lista = new ParameterDaoImpl().list();
		if (lista.get(0).getTextValue().equals("A")) {
			user = new User();
			user.setActive("A");
			Date date = new Date();
			date.getTime();
			user.setDateLastPassword(date);
			user.setUserType("nuevo");
			generarContrase�aAleatoria();
			user.setPassword(getStringMessageDigest(contrasenia, MD5));
			user.setIntentos(0);
			return "registroUsuario.xhtml";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"El registro se encuentra inactivo ", "El registro se encuentra inactivo"));
			return "PaginaPrincipal.xhtml";
		}

	}

	public String prepararModificarUsuario() {
		user = (User) (listaUsuarios.getRowData());
		return "EditarUsuario.xhtml";
	}

	public void buscarUsuario() {
		AuditBean auditoria = new AuditBean();

		UserDao dao = new UserDaoImpl();
		listaFiltrados = dao.getUserUsuario(buscar);
		if (!listaFiltrados.isEmpty()) {
			filtrados = new ListDataModel<User>(listaFiltrados);
		} else {
			filtrados = new ListDataModel<User>(new UserDaoImpl().list());
		}
	}

	public String eliminarUsuario() {

		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDao dao = new UserDaoImpl();
		if(!usuarioTemp.getUserType().equals("ADMIN")) {
		if(usuarioTemp.getActive().equals("I")) {
			usuarioTemp.setActive("A");
		}else {
			usuarioTemp.setActive("I");
		}}
		else {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome " + usuario));
		}
		
		dao.update(usuarioTemp);

		return "verTabla.xhtml";
	}

	public String adicionarUsuario() {

		UserDao dao = new UserDaoImpl();

		dao.save(user);
		AuditBean auditoria = new AuditBean();
		auditoria.adicionarAuditoria("Registrar", 0, "user", (int) (long) user.getId());
		log.info("Se cre� el usuario: " + user.getUserName());

		email = new Email();
		try {

			email.SendEmail("rusia.calendar2018", "wilsonrojas", user.getEmailAddress(),
					"Bienvenido a Calendario Mundial Rusia",
					"Hola, " + user.getFullName()
							+ ": Te damos la bienvenida a Calendario Mundial Rusia, una p�gina web en"
							+ " donde podr�s encontrar el cronograma, informaci�n y noticias actualizadas de este gran evento deportivo."
							+ "\n" + "Usuario: " + user.getUserName() + "\n" + "Contrase�a: " + contrasenia + "\n"
							+ "\n"
							+ "La primera vez que ingrese como usuario deber� cambiar la contrase�a, tiene un plazo de 5 d�as.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "Login.xhtml";
	}

	public String modificarUsuario() {

		UserDaoImpl dao = new UserDaoImpl();
		dao.update(user);
		AuditBean auditoria = new AuditBean();
		auditoria.adicionarAuditoria("UpdateUser", (int)(long)user.getId(), "user", (int) (long) user.getId());
		log.info("Se modific� el usuario: " + user.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se ha modificado su informacion exitosamente "));
		return "PanelUsuarios.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DataModel<User> getListaUsuarios() {
		List<User> lista = new UserDaoImpl().list();
		listaUsuarios = new ListDataModel<User>(lista);
		return listaUsuarios;
	}

	public void setListaUsuarios(DataModel<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String ingresarUsuario() {
		String rta = "";
		UserDao dao = new UserDaoImpl();
		listaValidar = dao.getUserUsuario(usuario);
		// Validacion de que el usuario existe
		if (!listaValidar.isEmpty()) {
			if (listaValidar.get(0).getPassword().equals(getStringMessageDigest(password, MD5))) {
				if (listaValidar.get(0).getUserType().equals("ADMIN")) {
					List<User> lista = new UserDaoImpl().list();
					filtrados = new ListDataModel<User>(lista);

					usuarioLogin=new InfoUser(listaValidar.get(0));
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome " + usuario));

					AuditBean auditoria = new AuditBean();
					auditoria.adicionarAuditoria("LoginUser", 0, "user", (int) (long) listaValidar.get(0).getId());
//					log.info("Ingreso del ADMIN ");
					rta = "panelAdmin.xhtml";

				} else if (listaValidar.get(0).getUserType().equals("usuario")) {
					if (listaValidar.get(0).getActive().equals("A"))
						if (listaValidar.get(0).getIntentos() < 3) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome " + usuario));
							AuditBean auditoria = new AuditBean();
							auditoria.adicionarAuditoria("LoginUser", 0, "User",
									(int) (long) listaValidar.get(0).getId());
//							log.info("Ingres� el usuario: " + user.getUserName());
							User userTemp = listaValidar.get(0);
							usuarioLogin=new InfoUser(userTemp);
							userTemp.setIntentos(0);
							dao.update(userTemp);
							rta = "PanelUsuario.xhtml";
						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_WARN, "SUPERO LOS INTENTOS ", "SUPERO LOS INTENTOS"));
							User usertmp = listaValidar.get(0);
							usertmp.setActive("I");
							dao.update(usertmp);
//							log.info("Se inactiv� el usuario: " + user.getUserName());
							rta = "Login.xhtml";
						}
					else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"SU USUARIO ESTA INACTIVO ", "SU USUARIO ESTA INACTIVO"));
//						log.info("Se inactiv� el usuario: " + user.getUserName());
						rta = "Login.xhtml";
					}
					}
				else if(listaValidar.get(0).getUserType().equals("nuevo")){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Se necesita cambiar la contrase�a por primera vez ", "Se necesita cambiar la contrase�a por primera vez"));
						rta= "cambiarContrase�a.xhtml";
					}

				 else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome " + "MOD"));
					log.info("Ingres� el moderador");

					rta = "EditarEquipo.xhtml";
				}

			} else {
				if (listaValidar.get(0).getUserType().equals("usuario") && listaValidar.get(0).getIntentos() < 3) {
					User UsuarioTemp = listaValidar.get(0);
					UsuarioTemp.setIntentos(UsuarioTemp.getIntentos() + 1);
					dao.update(UsuarioTemp);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "CONTRASE�A INCORRECTA se agrego un intento",
									"CONTRASE�A INCORRECTA se agrego un intento"));
//					log.info("Contrase�a incorrecta del usuario: " + user.getUserName());
					rta = "Login.xhtml";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"SUPERO LOS INTENTOS ", "SUPERO LOS INTENTOS"));
					User usertmp = listaValidar.get(0);
					usertmp.setActive("I");
					dao.update(usertmp);
//					log.info("Se inactiv� el usuario: " + user.getUserName());
					rta = "Login.xhtml";
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"CONTRASE�A INCORRECTA ", "CONTRASE�A INCORRECTA"));
				rta = "Login.xhtml";

			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "USUARIO INCORRECTO ", "USUARIO INCORRECTO"));
			rta = "Login.xhtml";
		}
		return rta;

	}

	public static String getStringMessageDigest(String message, String algorithm) {
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Error creando Digest");
		}
		return toHexadecimal(digest);
	}

	private static String toHexadecimal(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	public char[] generarNumsAleatorios() {

		String num = "0123456789";
		int longitud = (int) ((Math.random() * 3) + 1);

		int randomNums = (int) ((Math.random() * 10));

		char[] rta = new char[longitud];

		for (int i = 0; i < longitud; i++) {
			rta[i] = num.charAt(randomNums);

			randomNums = (int) ((Math.random() * 10));
		}

		return rta;
	}

	public char[] generarMinus() {

		String minus = "abcdefghijklmnopqrstuvwxyz";
		int rMinus = (int) ((Math.random() * 26));
		int longitud = (int) ((Math.random() * 3) + 1);

		char[] rta = new char[longitud];

		for (int i = 0; i < longitud; i++) {
			rta[i] = minus.charAt(rMinus);

			rMinus = (int) ((Math.random() * 26));
		}

		return rta;
	}

	public char[] generarMayus() {

		String mayus = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int rMayus = (int) ((Math.random() * 26));
		int longitud = (int) ((Math.random() * 3) + 1);

		char[] rta = new char[longitud];

		for (int i = 0; i < longitud; i++) {
			rta[i] = mayus.charAt(rMayus);

			rMayus = (int) ((Math.random() * 26));
		}

		return rta;
	}

	public String generarContrase�aAleatoria() {

		char[] nums = generarNumsAleatorios();
		char[] mayus = generarMayus();
		char[] minus = generarMinus();

		String num = String.valueOf(nums);
		String mayu = String.valueOf(mayus);
		String minu = String.valueOf(minus);

		contrasenia = num + "" + mayu + "" + minu;

		int random1 = (int) (Math.random() * 3) + 1;

		if (random1 == 2) {
			contrasenia = mayu + "" + num + "" + minu;
		}
		if (random1 == 3) {
			contrasenia = minu + "" + mayu + "" + num;
		}

		if (contrasenia.length() < 6 || contrasenia.length() > 8) {
			contrasenia = generarContrase�aAleatoria();
		}

		return contrasenia;

	}

	public String activarRegistro() {
		List<Parameter> lista = new ParameterDaoImpl().list();
		ParameterDao dao = new ParameterDaoImpl();
		Parameter parameter = lista.get(0);
		parameter.setTextValue("A");
		dao.update(parameter);
		return "panelAdmin.xhtml";

	}

	public String desactivarRegistro() {
		List<Parameter> lista = new ParameterDaoImpl().list();
		ParameterDao dao = new ParameterDaoImpl();
		Parameter parameter = lista.get(0);
		parameter.setTextValue("I");
		dao.update(parameter);

		return "panelAdmin.xhtml";

	}
	public InfoUser getUsuarioLogin() {
		return usuarioLogin;
	}
}
