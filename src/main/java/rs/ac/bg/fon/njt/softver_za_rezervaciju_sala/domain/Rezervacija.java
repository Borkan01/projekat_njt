/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import java.util.Date;

import jakarta.persistence.*;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="rezervacija")
public class Rezervacija {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="sala_id",referencedColumnName = "id")
    private Sala sala;


    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="korisnik_id",referencedColumnName = "korisnik_id")
    private Korisnik korisnik;
    @Column(name="vreme_pocetka")
    private Date vremePocetka;

    @Column(name="vreme_zavrsetka")
    private Date vremeZavrsetka;

    @Column(name="status_rezervacije")
    private String statusRezervacije;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "svrha_id",referencedColumnName = "id")
    private SvrhaRezervacije svrhaRezervacije;




    public Rezervacija() {
    }

    public Rezervacija(Date vremePocetka, Date vremeZavrsetka, String statusRezervacije, Sala sala, Korisnik korisnik,SvrhaRezervacije svrhaRezervacije) {
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
        this.statusRezervacije = statusRezervacije;
        this.sala = sala;
        this.korisnik = korisnik;
        this.svrhaRezervacije=svrhaRezervacije;
    }

    public Date getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Date vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public Date getVremeZavrsetka() {
        return vremeZavrsetka;
    }

    public void setVremeZavrsetka(Date vremeZavrsetka) {
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public String getStatusRezervacije() {
        return statusRezervacije;
    }

    public void setStatusRezervacije(String statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SvrhaRezervacije getSvrhaRezervacije() {
        return svrhaRezervacije;
    }

    public void setSvrhaRezervacije(SvrhaRezervacije svrhaRezervacije) {
        this.svrhaRezervacije = svrhaRezervacije;
    }

    @Override
    public String toString() {
        return "Rezervacija{" + "vremePocetka=" + vremePocetka + ", vremeZavrsetka=" + vremeZavrsetka + ", statusRezervacije=" + statusRezervacije + ", sala=" + sala + ", korisnik=" + korisnik + '}';
    }
    
    
    
    
    
}