package com.meco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.meco.services.DireccionService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private DireccionService direccionService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> getAll(){
		return ResponseEntity.ok(this.clienteService.getAll());
	}
	
	@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> getById(@PathVariable Long idCliente){
		if(this.clienteService.exists(idCliente)) {
			return ResponseEntity.ok(this.clienteService.getById(idCliente));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<Cliente>(this.clienteService.save(cliente), HttpStatus.CREATED);
	}

	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long idCliente,
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
	
	
	//CRUD de direcciones
	@GetMapping("/{idCliente}/direccion")
	public ResponseEntity<List<Direccion>> getDireccionesCliente(@PathVariable Long idCliente){
		if(this.clienteService.exists(idCliente)) {
			return ResponseEntity.ok(this.clienteService.getById(idCliente).getDirecciones());
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{idCliente}/direccionActual")
	public ResponseEntity<List<Direccion>> getDireccionesClienteActual(@PathVariable Long idCliente){
		if(this.clienteService.exists(idCliente)) {
			return ResponseEntity.ok(this.clienteService.getById(idCliente).getDirecciones());
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/{idCliente}/direccion")
	public ResponseEntity<Direccion> createDireccion(@PathVariable Long idCliente, @RequestBody Direccion direccion) {
		if(this.clienteService.exists(idCliente)) {
			Cliente c = this.clienteService.getById(idCliente);
			c.addDireccion(direccion);
		}
		return new ResponseEntity<Direccion>(this.direccionService.save(direccion), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idCliente}/direccion/{idDireccion}")
	public ResponseEntity<Direccion> updateDireccion(@PathVariable Long idCliente, @PathVariable Long idDireccion, @RequestBody Direccion direccion) {
		 if (!clienteService.exists(idCliente) || !direccionService.exists(idDireccion)) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		
		
			Cliente c = this.clienteService.getById(idCliente);
			Direccion d = this.direccionService.getById(idDireccion);
			d.setCliente(c);
			d.setCp(direccion.getCp());
			d.setDireccion(direccion.getDireccion());
			d.setMunicipio(direccion.getMunicipio());
			d.setProvincia(direccion.getProvincia());
			d.setEsActual(direccion.getEsActual());
			d.setEsComprador(direccion.getEsComprador());
			
			this.direccionService.save(d);
		
		return ResponseEntity.ok(d);
	}
	
	@DeleteMapping
	public ResponseEntity<Direccion> deleteDireccion(@PathVariable Long idCliente, @PathVariable Long idDireccion){
		if (!clienteService.exists(idCliente) || !direccionService.exists(idDireccion)) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		
	    Cliente c = clienteService.getById(idCliente);
	    Direccion d = direccionService.getById(idDireccion);
	    
	    if (d.getCliente() == null || !d.getCliente().getId().equals(idCliente)) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	    
	    c.getDirecciones().remove(d);
	    clienteService.save(c); 
	    
	    direccionService.delete(idDireccion);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
}
