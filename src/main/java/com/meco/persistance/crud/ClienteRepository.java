package com.meco.persistance.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.meco.persistance.entities.Cliente;

public interface ClienteRepository extends ListCrudRepository<Cliente, Long>{

}
