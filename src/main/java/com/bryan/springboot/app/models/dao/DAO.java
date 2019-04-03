package com.bryan.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.bryan.springboot.app.models.entity.Cliente;

public interface DAO extends CrudRepository<Cliente, Long>{

}
