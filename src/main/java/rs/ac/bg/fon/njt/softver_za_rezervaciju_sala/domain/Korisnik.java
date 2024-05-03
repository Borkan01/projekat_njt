package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="sifra")
    private String sifra;

    @OneToOne(cascade ={ CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "osoba_id",referencedColumnName = "id")
    private Osoba osoba;

    public Korisnik() {
    }

    public Korisnik(String sifra, Osoba osoba) {
        this.sifra = sifra;
        this.osoba = osoba;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }


    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", sifra='" + sifra + '\'' +
                ", osoba=" + osoba +
                '}';
    }
}
