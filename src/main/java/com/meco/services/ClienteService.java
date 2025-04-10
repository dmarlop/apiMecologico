package com.meco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meco.persistance.crud.ClienteRepository;
import com.meco.persistance.entities.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	
	public List<Cliente> getAll() {
		return this.clienteRepository.findAll();
	}

	
	public Cliente getById(Long id) {
		return this.clienteRepository.findById(id).get();
	}

	
	public boolean exists(Long id) {
		return this.clienteRepository.existsById(id);
	}

	
	public boolean delete(Long id) {
		if (this.clienteRepository.existsById(id)) {
			this.clienteRepository.deleteById(id);
			return true;
		}
		return false;
	}

	
	public Cliente save(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}

	
	public Cliente update(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
}