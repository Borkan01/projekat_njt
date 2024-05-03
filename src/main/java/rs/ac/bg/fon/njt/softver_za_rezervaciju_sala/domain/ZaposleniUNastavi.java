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
@Table(name="zaposleni_u_nastavi")
@DiscriminatorValue("ZN")
public class ZaposleniUNastavi extends Zaposleni{
    
    @Column(name="zvanje")
    private String zvanje;
    
    @Column(name="katedra")
    private String katedra;

    public ZaposleniUNastavi() {
        System.out.println("konstruktor zaposleniUNastavi");
    }

    public ZaposleniUNastavi(String zvanje, String katedra, String ime, String prezime, String email, String titula) {
        super(ime, prezime, email, titula);
        this.zvanje = zvanje;
        this.katedra = katedra;
    }

    public String getZvanje() {
        return zvanje;
    }

    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }

    public String getKatedra() {
        return katedra;
    }

    public void setKatedra(String katedra) {
        this.katedra = katedra;
    }

    @Override
    public String toString() {
        return super.toString() + "zvanje=" + zvanje + ", katedra=" + katedra ;
    }
    
    
    
}
