/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;

import jakarta.mail.MessagingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaDTO;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.EmailService;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.KorisnikImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.RezervacijaImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.SalaImpl;

/**
 *
 * @author Korisnik
 */
@Controller
@RequestMapping("/reservation")
public class RezervacijaController {

    private RezervacijaImpl rezervacijaImpl;
    private KorisnikImpl korisnikImpl;
    private SalaImpl salaImpl;
    private EmailService emailService;

    public RezervacijaController() {
    }

    @Autowired
    public RezervacijaController(RezervacijaImpl rezervacijaImpl, SalaImpl salaImpl, KorisnikImpl korisnikImpl, EmailService emailService) {
        this.rezervacijaImpl = rezervacijaImpl;
        this.salaImpl = salaImpl;
        this.korisnikImpl = korisnikImpl;
        this.emailService = emailService;
    }

    @GetMapping("/chooseDate")
    public String chooseDate() {
        return "rezervacija/chooseDate";
    }

    @GetMapping("/hallsPerDay")
    public String listSalePoDanu(@RequestParam("datum") String datumStr, Model model) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date datum = null;
            try {
                datum = sdf.parse(datumStr);
            } catch (ParseException e) {
                e.printStackTrace();
                // handle error
            }

            List<SalePoDanu> rezervacije = rezervacijaImpl.vratiZauzetostiZaDan(datum);

            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/hallsPerDay";

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/createReservation")
    public String showCreateRezervacijaForm(Model model) {
        try {
            List<Sala> sale = salaImpl.vratiAktivneSale();
            model.addAttribute("sale", sale);
            model.addAttribute("rezervacijaDTO", new RezervacijaDTO());
            return "rezervacija/createReservation";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @PostMapping("/createReservation")
    public String createRezervacija(@ModelAttribute("rezervacijaDTO") RezervacijaDTO rezervacijaDTO, @RequestParam("vremePocetka") String vremePocetkaHour,
            @RequestParam("vremeZavrsetka") String vremeZavrsetkaHour, @AuthenticationPrincipal User user, Model model) {
        try {
            Rezervacija rezervacija = new Rezervacija();
            Sala sala = salaImpl.findSalaById(Long.valueOf(rezervacijaDTO.getSalaId()));
            rezervacija.setSala(sala);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date vremePocetka = (Date) formatter.parse(vremePocetkaHour);
            Date vremeZavrsetka = (Date) formatter.parse(vremeZavrsetkaHour);
            Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
            // Postavljanje vremena početka i završetka u rezervacijuDTO
            rezervacija.setVremePocetka(vremePocetka);
            rezervacija.setVremeZavrsetka(vremeZavrsetka);
            rezervacija.setStatusRezervacije("U CEKANJU");
            RezervacijaID rezervacijaID = new RezervacijaID();
            rezervacijaID.setSala_id(rezervacija.getSala().getId());
            rezervacijaID.setKorisnik_id(korisnik.getId());
            rezervacija.setRezervacijaID(rezervacijaID);
            SvrhaRezervacije svrhaRezervacije = rezervacijaDTO.getSvrhaRezervacije();
            rezervacija.setSvrhaRezervacije(svrhaRezervacije);

            // Sačuvaj rezervaciju
            rezervacijaImpl.sacuvajRezervaciju(rezervacija);
            return "redirect:/reservation/myReservations";

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/deleteReservation")
    public String deleteReservation(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);

            Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(rezervacijaID);
            emailService.cancelReservation(rezervacija);

            rezervacijaImpl.deleteRezervacijaById(rezervacijaID);
            return "redirect:/reservation/allReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/deleteMyReservation")
    public String delete(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);
            rezervacijaImpl.deleteRezervacijaById(rezervacijaID);
            return "redirect:/reservation/myReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/myReservations")
    public String myReservations(@AuthenticationPrincipal User user, Model model) {
        try {
            Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
            List<Rezervacija> rezervacije = rezervacijaImpl.findRezervacijaByUser(korisnik.getId());
            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/myReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    private Date getDateWithHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @GetMapping("/allReservations")
    public String listRezervacije(Model model) {
        try {
            List<Rezervacija> rezervacije = rezervacijaImpl.findAllRezervacije();
            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/allReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/updateReservation")
    public String showFormForUpdate(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);

            System.out.println(rezervacijaID.toString().isBlank());
            Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(rezervacijaID);
            RezervacijaDTO rezervacijaDTO = new RezervacijaDTO();
            rezervacijaDTO.setId(id);
            rezervacijaDTO.setKorisnikId(korisnikId);
            rezervacijaDTO.setSalaId(rezervacija.getSala().getId());
            rezervacijaDTO.setStatusRezervacije(rezervacija.getStatusRezervacije());
            rezervacijaDTO.setSvrhaRezervacije(rezervacija.getSvrhaRezervacije());
            System.out.println(rezervacija.getSvrhaRezervacije());
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            rezervacijaDTO.setVremePocetka(formatter.format(rezervacija.getVremePocetka()));
            rezervacijaDTO.setVremeZavrsetka(formatter.format(rezervacija.getVremeZavrsetka()));
            model.addAttribute("rezervacijaDTO", rezervacijaDTO);

            // Dodavanje potrebnih podataka za prikazivanje opcija u formi
            return "rezervacija/updateReservation";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @PostMapping("/updateReservation")
    public String saveRezervacija(@ModelAttribute("rezervacijaDTO") RezervacijaDTO rezervacijaDTO, Model model) {
        try {

            Rezervacija r = rezervacijaImpl.findRezervacijaById(new RezervacijaID(rezervacijaDTO.getId(), rezervacijaDTO.getSalaId(), rezervacijaDTO.getKorisnikId()));
            Rezervacija backup = new Rezervacija();
            backup.setStatusRezervacije(r.getStatusRezervacije());
            SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije();
            svrhaRezervacije.setOpis(r.getSvrhaRezervacije().getOpis());
            svrhaRezervacije.setOrganizator(r.getSvrhaRezervacije().getOrganizator());
            svrhaRezervacije.setSvrha(r.getSvrhaRezervacije().getSvrha());
            backup.setSvrhaRezervacije(svrhaRezervacije);
            backup.setVremePocetka(r.getVremePocetka());
            backup.setVremeZavrsetka(r.getVremeZavrsetka());
            r.setSvrhaRezervacije(rezervacijaDTO.getSvrhaRezervacije());
            r.setStatusRezervacije(rezervacijaDTO.getStatusRezervacije());
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            r.setVremePocetka(formatter.parse(rezervacijaDTO.getVremePocetka()));
            r.setVremeZavrsetka(formatter.parse(rezervacijaDTO.getVremeZavrsetka()));
            System.out.println(r.getSvrhaRezervacije());
            System.out.println(r.getSvrhaRezervacije());
            rezervacijaImpl.sacuvajRezervaciju(r);

            if (r.getStatusRezervacije().equals(backup.getStatusRezervacije())
                    && r.getSvrhaRezervacije().equals(backup.getSvrhaRezervacije())
                    && r.getVremePocetka().equals(backup.getVremePocetka())
                    && r.getVremeZavrsetka().equals(backup.getVremeZavrsetka())) {

                return "redirect:/reservation/allReservations";

            } else if (!r.getStatusRezervacije().equals(backup.getStatusRezervacije())
                    && r.getSvrhaRezervacije().equals(backup.getSvrhaRezervacije())
                    && r.getVremePocetka().equals(backup.getVremePocetka())
                    && r.getVremeZavrsetka().equals(backup.getVremeZavrsetka())) {
                emailService.conformationReservation(r);
                return "redirect:/reservation/allReservations";

            } else {
                emailService.changeDataReservation(r, backup);
                return "redirect:/reservation/allReservations";
            }

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }
    
    
    
    

}
