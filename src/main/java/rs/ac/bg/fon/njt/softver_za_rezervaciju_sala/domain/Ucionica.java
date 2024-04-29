/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="ucionica")
@DiscriminatorValue("U")
public class Ucionica extends Sala{

    public Ucionica() {
    }

    public Ucionica(String naziv, String napomena, int brojMesta,boolean status) {
        super(naziv, napomena, brojMesta,status);
    }

    @Override
    public String toString() {
        return "Ucionica: "+super.toString();
    }
    
    
    
}
