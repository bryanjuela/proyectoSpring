package com.bryan.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bryan.springboot.app.models.entity.Cliente;

public interface DAO extends PagingAndSortingRepository<Cliente, Long>{

}
