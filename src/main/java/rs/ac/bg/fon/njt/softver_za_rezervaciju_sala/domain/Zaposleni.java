/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Stefan
 */


@Entity
@Table(name = "zaposleni")
@Component
@DiscriminatorColumn(name="vrsta_zaposlenog")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorValue(value = "Z")
//@MappedSuperclass

public abstract class Zaposleni extends Osoba{
    
    
     

    @Column(name="titula")
    protected String titula;

    public Zaposleni() {
        System.out.println("konstruktor Zaposleni");
        
    }

    
    
    
    public Zaposleni(String ime, String prezime, String email, String titula) {
        super(ime,prezime,email);
        this.titula=titula;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    @Override
    public String toString() {
        return super.toString()+" titula: "+titula;
    }
}
