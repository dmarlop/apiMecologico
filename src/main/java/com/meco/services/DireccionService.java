package com.meco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meco.persistance.crud.DireccionRepository;
import com.meco.persistance.entities.Direccion;

@Service
public class DireccionService {
	
	@Autowired
	private DireccionRepository direccionRepository;
	
	
    public List<Direccion> getAll() {
        return this.direccionRepository.findAll();
    }

    
    public Direccion getById(Long id) {
        return this.direccionRepository.findById(id).get();
    }

    
    public boolean exists(Long id) {
        return this.direccionRepository.existsById(id);
    }

    
    public boolean delete(Long id) {
        if (this.direccionRepository.existsById(id)) {
            this.direccionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
    public Direccion save(Direccion direccion) {
        if (direccion.getEsActual() != null && direccion.getEsActual()) {
           
            List<Direccion> actuales = this.direccionRepository.findByEsActualTrue();
            for (Direccion d : actuales) {
                
                if (d.getId() != null && (direccion.getId() == null || !d.getId().equals(direccion.getId()))) {
                    d.setEsActual(false);
                    this.direccionRepository.save(d);
                }
            }
        }
        return this.direccionRepository.save(direccion);
    }

    
    public Direccion update(Direccion direccion) {
        Direccion d = this.direccionRepository.findById(direccion.getId()).get();

        d.setDireccion(direccion.getDireccion());
        d.setMunicipio(direccion.getMunicipio());
        d.setProvincia(direccion.getProvincia());
        d.setCp(direccion.getCp());
        d.setEsComprador(direccion.getEsComprador());

        if (direccion.getEsActual() != null && direccion.getEsActual()) {
            
            List<Direccion> actuales = this.direccionRepository.findByEsActualTrue();
            for (Direccion addr : actuales) {
                if (!addr.getId().equals(d.getId())) {
                    addr.setEsActual(false);
                    this.direccionRepository.save(addr);
                }
            }
            d.setEsActual(true);
        } else {
            d.setEsActual(false);
        }

        return this.direccionRepository.save(d);
    }
	

}
