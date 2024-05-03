/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class SalaImpl {
     public SalaImpl() {

    }
    private SalaRepository salaRepository;

    @Autowired
    public SalaImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
    
    
    
}
