/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Korisnik
 */
public class SalePoDanu {
    private Date datum;
    private Sala sala;
    private List<Integer> sati;
    private List<Boolean> zauzetost;
    private List<SvrhaRezervacije> svrhaRezervacije;

    public SalePoDanu(Date datum, Sala sala, List<Integer> sati, List<Boolean> zauzetost,List<SvrhaRezervacije> svrhaRezervacije) {
        this.datum = datum;
        this.sala = sala;
        this.sati = sati;
        this.zauzetost = zauzetost;
        this.svrhaRezervacije = svrhaRezervacije;
    }

    public List<SvrhaRezervacije> getSvrhaRezervacije() {
        return svrhaRezervacije;
    }

    public void setSvrhaRezervacije(List<SvrhaRezervacije> svrhaRezervacije) {
        this.svrhaRezervacije = svrhaRezervacije;
    }

   

    
    public SalePoDanu() {
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    
   

    public List<Integer> getSati() {
        return sati;
    }

    public void setSati(List<Integer> sati) {
        this.sati = sati;
    }

    public List<Boolean> getZauzetost() {
        return zauzetost;
    }

    public void setZauzetost(List<Boolean> zauzetost) {
        this.zauzetost = zauzetost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.datum);
        hash = 73 * hash + Objects.hashCode(this.sala);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalePoDanu other = (SalePoDanu) obj;
        if (!Objects.equals(this.datum, other.datum)) {
            return false;
        }
        return Objects.equals(this.sala, other.sala);
    }

    @Override
    public String toString() {
        return "SalePoDanu{" + "datum=" + datum + ", sala=" + sala + '}';
    }

    

   



    
    
    
}
