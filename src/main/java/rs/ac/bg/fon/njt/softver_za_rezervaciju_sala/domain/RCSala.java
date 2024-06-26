/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="rc_sala")
@DiscriminatorValue("RC")
public class RCSala extends Sala{
    
    @Column(name="broj_racunara")
    private Integer brojRacunara;

    public RCSala() {
    }

    public RCSala(int brojRacunara, String naziv, String napomena, Integer brojMesta,boolean status) {
        super(naziv, napomena, brojMesta,status);
        this.brojRacunara = brojRacunara;
    }

    @Override
    public String toString() {
        return "RCSala: "+super.toString()+" broj racunara: "+brojRacunara;
    }
    
    

}
