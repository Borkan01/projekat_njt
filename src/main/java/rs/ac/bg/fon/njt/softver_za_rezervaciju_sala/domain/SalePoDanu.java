/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Korisnik
 */
public class SalePoDanu {
    private Date datum;
    private Sala sala;
    private int sat;
    private boolean zauzetost;

    public SalePoDanu(Date datum, Sala sala, int sat, boolean zauzetost) {
        this.datum = datum;
        this.sala = sala;
        this.sat = sat;
        this.zauzetost = zauzetost;
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

    
    public Sala getSalaId() {
        return sala;
    }

    public void setSalaId(Sala sala) {
        this.sala = sala;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public boolean isZauzetost() {
        return zauzetost;
    }

    public void setZauzetost(boolean zauzetost) {
        this.zauzetost = zauzetost;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.datum);
        hash = 83 * hash + Objects.hashCode(this.sala);
        hash = 83 * hash + this.sat;
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
        if (this.sat != other.sat) {
            return false;
        }
        if (!Objects.equals(this.datum, other.datum)) {
            return false;
        }
        return Objects.equals(this.sala, other.sala);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        String datumKaoString = format.format(datum);
        return "SalePoDanu{" + "datum=" + datumKaoString + ",sat=" + sat + " sala=" + sala + ", , zauzetost=" + zauzetost + '}';
    }

    
    
    
}
