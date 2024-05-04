package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Student;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniUNastavi;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.KorisnikImpl;

@SpringBootApplication
public class SoftverZaRezervacijuSalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftverZaRezervacijuSalaApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(KorisnikImpl korisnikImpl) {

        return runner -> {

            //vratiKorisnika(korisnikImpl);
            vratiKorisnikeZNorZNN(korisnikImpl);
            // dodajKorisnika(korisnikImpl);
            // azurirajKorisnika(korisnikImpl);
              //vratiPotvrdjeneRezervacije(rezervacijaImpl);
               //sacuvajRezervaciju(rezervacijaImpl);
               //zauzetostSala(rezervacijaImpl);
        };
            
    }   
            

    private void vratiKorisnika(KorisnikImpl korisnikImpl) {

//        ZaposleniUNastavi zaposleniUNastavi=new ZaposleniUNastavi("docent", "si", "stefi",
//                "segi", "stefi@gmail.com", "prodekan");
        ZaposleniUNastavi zaposleniUNastavi = new ZaposleniUNastavi("", "", "", "", "stefi@gmail.com", "");

        //Student s=new Student("dule", "savic", "dule@gmail.com", "organizacija");
        Korisnik k = new Korisnik();
//        k.setId(2);
        k.setSifra("dulesavic");
        k.setOsoba(zaposleniUNastavi);

        System.out.println(k);

        Korisnik korisnik = korisnikImpl.vratiKorisnika(k);
        System.out.println(korisnik);

    }

    private void vratiKorisnikeZNorZNN(KorisnikImpl korisnikImpl) {

        List<Korisnik> korisnici = korisnikImpl.vratiKorisnikeZN();
        for (Korisnik korisnik : korisnici) {
            System.out.println(korisnik);
        }
    }

    private void dodajKorisnika(KorisnikImpl korisnikImpl) {

        Student s = new Student("marko", "sreji", "sreji@gmail.com", "turnir");

        Korisnik k = new Korisnik();
        k.setSifra("sreji01");
        k.setOsoba(s);

        korisnikImpl.save(k);
        System.out.println("SUCCESS");

    }

    private void azurirajKorisnika(KorisnikImpl korisnikImpl) {

        Student s = new Student("bobi", "bobic", "bobi@gmail.com", "turnir");

        Korisnik k = new Korisnik();
        //k.setId(3);
        k.setSifra("bobitodic");
        k.setOsoba(s);

        korisnikImpl.update(k);
        System.out.println("SUCCESS");

    }



}
