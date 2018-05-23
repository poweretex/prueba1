package com.edu.ubosque.prg.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the schedule database table.
 * 
 */
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date gameDate;

	private int idReferee;

	private int idStadium;

	private int localGoals;

	private int localTeam;

	private String state;

	private int visitGoals;

	private int visitTeam;

	public Schedule() {
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGameDate() {
		return this.gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public int getIdReferee() {
		return this.idReferee;
	}

	public void setIdReferee(int idReferee) {
		this.idReferee = idReferee;
	}

	public int getIdStadium() {
		return this.idStadium;
	}

	public void setIdStadium(int idStadium) {
		this.idStadium = idStadium;
	}

	public int getLocalGoals() {
		return this.localGoals;
	}

	public void setLocalGoals(int localGoals) {
		this.localGoals = localGoals;
	}

	public int getLocalTeam() {
		return this.localTeam;
	}

	public void setLocalTeam(int localTeam) {
		this.localTeam = localTeam;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getVisitGoals() {
		return this.visitGoals;
	}

	public void setVisitGoals(int visitGoals) {
		this.visitGoals = visitGoals;
	}

	public int getVisitTeam() {
		return this.visitTeam;
	}

	public void setVisitTeam(int visitTeam) {
		this.visitTeam = visitTeam;
	}

}