package com.meco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.meco.persistance.entities.Cliente;
import com.meco.persistance.entities.Direccion;
import com.meco.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> getAll(){
		return ResponseEntity.ok(this.clienteService.getAll());
	}
	
	@GetMapping("/{idDesayuno}")
	public ResponseEntity<Cliente> getById(@PathVariable Long idCliente){
		if(this.clienteService.exists(idCliente)) {
			return ResponseEntity.ok(this.clienteService.getById(idCliente));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> createDireccion(@RequestBody Cliente cliente) {
		return new ResponseEntity<Cliente>(this.clienteService.save(cliente), HttpStatus.CREATED);
	}

	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> updateDireccion(@PathVariable Long idCliente,
			@RequestBody Cliente direccionDetails) {
		if (idCliente != direccionDetails.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.clienteService.exists(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.clienteService.save(direccionDetails));
	}

	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Cliente> deleteDireccion(@PathVariable Long idCliente) {
		if (clienteService.exists(idCliente)) {
			clienteService.delete(idCliente);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
