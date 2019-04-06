package com.bryan.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bryan.springboot.app.models.dao.DAO;
import com.bryan.springboot.app.models.entity.Cliente;

@org.springframework.stereotype.Service
public class ClienteService implements Service {

	@Autowired
	private DAO dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) dao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		dao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);;
	}

	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

}//class
