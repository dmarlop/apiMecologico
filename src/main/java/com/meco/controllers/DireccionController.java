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
import com.meco.persistance.entities.Direccion;
import com.meco.services.DireccionService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/direccion")
public class DireccionController {

	@Autowired
	private DireccionService direccionService;

	@GetMapping
	public ResponseEntity<List<Direccion>> getAll() {
		return ResponseEntity.ok(this.direccionService.getAll());
	}

	@GetMapping("/{idDireccion}")
	public ResponseEntity<Direccion> getById(@PathVariable Long idDireccion) {
		if (this.direccionService.exists(idDireccion)) {
			return ResponseEntity.ok(this.direccionService.getById(idDireccion));
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping
	public ResponseEntity<Direccion> createDireccion(@RequestBody Direccion direccion) {
		return new ResponseEntity<Direccion>(this.direccionService.save(direccion), HttpStatus.CREATED);
	}

	@PutMapping("/{idDireccion}")
	public ResponseEntity<Direccion> updateDireccion(@PathVariable Long idDireccion,
			@RequestBody Direccion direccionDetails) {
		if (idDireccion != direccionDetails.getId()) {
			return ResponseEntity.badRequest().build();
		}
		if (!this.direccionService.exists(idDireccion)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.direccionService.save(direccionDetails));
	}

	@DeleteMapping("/{idDireccion}")
	public ResponseEntity<Direccion> deleteDireccion(@PathVariable Long idDireccion) {
		if (direccionService.exists(idDireccion)) {
			direccionService.delete(idDireccion);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
