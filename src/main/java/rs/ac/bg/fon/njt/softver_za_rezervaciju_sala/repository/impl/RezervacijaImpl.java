/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.RezervacijaRepository;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;

/**
 *
 * @author BaNbO01
 */
@Service
public class RezervacijaImpl {

    public RezervacijaImpl() {

    }
    private SalaRepository salaRepository;
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    public RezervacijaImpl(RezervacijaRepository rezervacijaRepository,SalaRepository salaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public List<Rezervacija> vratiSvePotvrdjeneRezervacije(){
        return rezervacijaRepository.vratiPotvrdjeneRezervacije();
    }
    
    @Transactional
    public void sacuvajRezervaciju(Rezervacija rezervacija){
     
        Integer brojZauzetihTermina = rezervacijaRepository.brojRezervacija(rezervacija);
        if(brojZauzetihTermina==null || brojZauzetihTermina==0){
            rezervacijaRepository.save(rezervacija);
        }
        else{
            throw new RuntimeException("Ne moze se rezervisati za ovaj termin");
        }
    }

    
    @Transactional
    public List<SalePoDanu> vratiZauzetostiZaDan(Date datum){
        List<SalePoDanu> lista = new ArrayList<>();
        List<Sala> sveSale = salaRepository.vratiSaleKojeSuAktivne();
        for (Sala sala : sveSale) {
            for(int i=8;i<22;i++){
              boolean b  = rezervacijaRepository.vratiSalePoSatu(sala.getId(), datum, i);
              SalePoDanu salePoDanu = new SalePoDanu(datum,sala, i, b);
              lista.add(salePoDanu);
            }
        }
            lista = lista.stream()
                .sorted(Comparator.comparing(SalePoDanu::getDatum).thenComparingInt(SalePoDanu::getSat))
                .collect(Collectors.toList());
        return lista;
        
    }

}
