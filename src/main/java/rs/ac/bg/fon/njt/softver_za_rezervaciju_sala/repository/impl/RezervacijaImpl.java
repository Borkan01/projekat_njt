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
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;
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
        System.out.println("Broj zauzetih termina je :" + brojZauzetihTermina);
        
         if(rezervacija.getVremePocetka().before(new Date()) || rezervacija.getVremeZavrsetka().before(new Date())){
             
            throw new RuntimeException("Nije moguce rezervisati termin u proslosti, pokusajte ponovo");
        }
        
         
          if(rezervacija.getVremeZavrsetka().before(rezervacija.getVremePocetka())){
            throw new RuntimeException("Vreme zavrsetka ne moze biti pre vremena pocetka!!!");
        }
        
        if(rezervacija.getRezervacijaID().getId()!=0){
            System.out.println("Izmena rezervacije");
            rezervacijaRepository.save(rezervacija);
            return;
        }
       
       
        if(brojZauzetihTermina==null || brojZauzetihTermina==0){
            rezervacijaRepository.save(rezervacija);
        }
        else{
            throw new RuntimeException("Ne moze se rezervisati sala u ovom terminu zato sto je zauzeta, molimo Vas pokusajte rezervaciju nekog drugog termina");
        }
    }

    
    @Transactional
    public List<SalePoDanu> vratiZauzetostiZaDan(Date datum){
        List<SalePoDanu> lista = new ArrayList<>();
        List<Sala> sveSale = salaRepository.vratiSaleKojeSuAktivne();
        for (Sala sala : sveSale) {
            SalePoDanu salePoDanu = new SalePoDanu();
            salePoDanu.setDatum(datum);
            salePoDanu.setSala(sala);
            List<Integer> sati = new ArrayList<>();
            List<Boolean> zauzetost = new ArrayList<>();
            List<SvrhaRezervacije> svrhaRezervacija = new ArrayList<>();
            for(int i=8;i<=24;i++){
              boolean b  = rezervacijaRepository.vratiSalePoSatu(sala.getId(), datum, i);
                SvrhaRezervacije svrhaRezervacije = rezervacijaRepository.vratiSvhruRezervacije(sala.getId(), datum, i);
                svrhaRezervacija.add(svrhaRezervacije);
              sati.add(i);
              zauzetost.add(b);
            }
            salePoDanu.setSvrhaRezervacije(svrhaRezervacija);
            salePoDanu.setSati(sati);
            salePoDanu.setZauzetost(zauzetost);
            lista.add(salePoDanu);
        }
           
        return lista;
        
    }
    
    @Transactional
    public Rezervacija findRezervacijaById(RezervacijaID rezervacijaID){
        return rezervacijaRepository.findRezervacijaById(rezervacijaID);
    }
        
    
    @Transactional
    public void deleteRezervacijaById(RezervacijaID rezervacijaID){
         rezervacijaRepository.deleteById(rezervacijaID);
    }
    
    @Transactional
    public List<Rezervacija> findAllRezervacije(){
        return rezervacijaRepository.findAllRezervacija();
    }
    
    @Transactional
    public List<Rezervacija> findRezervacijaByUser(Integer id){
        return rezervacijaRepository.findRezervacijeByUser(id);
    }

}
