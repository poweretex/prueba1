package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Parameter;

public interface ParameterDao {
	public void save(Parameter parameter);

	public Parameter getParameter(int id);

	public List<Parameter> list();

	public void remove(Parameter parameter);

	public void update(Parameter parameter);
}
