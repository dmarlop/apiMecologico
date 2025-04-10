package com.meco.persistance.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.meco.persistance.entities.Direccion;

public interface DireccionRepository extends ListCrudRepository<Direccion, Long> {
	 List<Direccion> findByEsActualTrue();
}
