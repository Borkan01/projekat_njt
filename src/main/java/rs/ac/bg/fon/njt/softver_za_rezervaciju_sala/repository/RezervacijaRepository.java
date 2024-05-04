/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;

/**
 *
 * @author BaNbO01
 */
public interface RezervacijaRepository extends JpaRepository<Rezervacija, RezervacijaID>{
    
    @Query("SELECT r FROM Rezervacija r WHERE r.statusRezervacije='POTVRDJENA'")
    public List<Rezervacija> vratiPotvrdjeneRezervacije();
    
    @Query("SELECT COUNT(*) FROM Rezervacija r WHERE (r.vremePocetka BETWEEN :#{#rezervacija.vremePocetka} AND :#{#rezervacija.vremeZavrsetka}) AND(r.vremeZavrsetka BETWEEN :#{#rezervacija.vremePocetka} AND :#{#rezervacija.vremeZavrsetka}) AND (r.sala= :#{#rezervacija.sala})")
    public Integer brojRezervacija(@Param("rezervacija") Rezervacija rezervacija);
    
   // @Query("SELECT r.sala.id, COUNT(r) FROM Rezervacija r WHERE FUNCTION('DATE', r.vremePocetka) = FUNCTION('DATE', :datum) GROUP BY r.sala.id, HOUR(r.vremePocetka) ORDER BY r.sala.id, HOUR(r.vremePocetka)") 
    //List<Object[]> vratiZauzetostSala(@Param("datum") Date datum);
    
    @Query("SELECT (COUNT(r)>0) FROM Rezervacija r WHERE r.sala.id = :sala AND DATE(r.vremePocetka) =:datum  AND  (HOUR(r.vremePocetka) <= :sat) AND (HOUR(r.vremeZavrsetka)>= :sat)")
    public boolean vratiSalePoSatu(@Param("sala") Integer sala,@Param("datum")Date datum,@Param("sat")Integer sat);
    
    
}
