/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniUNastavi;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniVanNastave;

/**
 *
 * @author Stefan
 */
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{
    
    @Query("select k from Korisnik k where k.sifra=:#{#korisnik.sifra} and k.osoba.email=:#{#korisnik.getOsoba().getEmail()}")
    public Korisnik vratiKorisnika(@Param("korisnik") Korisnik korisnik);
    
    @Query("select k from Korisnik k where TYPE(k.osoba) IN (:ZaposleniUNastavi,:ZaposleniVanNastave)")
    public List<Korisnik> vratiKorisnikeZNorZNN(@Param("ZaposleniUNastavi") Class<ZaposleniUNastavi> zaposleni1,
            @Param("ZaposleniVanNastave") Class<ZaposleniVanNastave> zaposleni2);
    
    @Query("select k from Korisnik k where k.osoba.id=:id")
    public Korisnik vratiKorisnikaPoOsobaId(@Param("id")int id);
    

    
}
