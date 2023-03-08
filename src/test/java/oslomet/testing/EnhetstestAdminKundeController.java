package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKunder_loggetInn() {
        // arrange
        List<Kunde> kundeliste = new ArrayList<>();
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde enKunde2 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        kundeliste.add(enKunde);
        kundeliste.add(enKunde2);

        when(sjekk.loggetInn()).thenReturn("admin");
        when(repository.hentAlleKunder()).thenReturn(kundeliste);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void hentAlleKunder_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerKunde_loggetInn_gammeltPoststed(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lana", "Jensen", "Askerveien 25", "3270",
                "Asker", "22225555", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKunde_loggetInn_nyttPoststed(){
        // arrange
        Kunde enKunde_nyttPoststed = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(enKunde_nyttPoststed)).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(enKunde_nyttPoststed);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKunde_loggetInn_Feil(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lana", "Jensen", "Askerveien 25", "3270",
                "Asker", "22225555", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerKunde_loggetInn_PoststedFeil(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerKunde_IkkeLoggetInn(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_Poststed(){
        // arrange
        Kunde endretKunde = new Kunde("01010110523",
                "Lana", "Jensen", "Askerveien 25", "3270",
                "Asker", "22225555", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(endretKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_nyttPoststed(){
        // arrange
        Kunde endretKunde_nyttPoststed = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(endretKunde_nyttPoststed);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_Feil(){
        // arrange
        Kunde endretKunde = new Kunde("01010110523",
                "Lana", "Jensen", "Askerveien 25", "3270",
                "Asker", "22225555", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.endre(endretKunde);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_PoststedFeil(){
        // arrange
        Kunde endretKunde_nyttPoststed = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.endre(endretKunde_nyttPoststed);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn(){
        // arrange
        Kunde endretKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.endre(endretKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_LoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde("01010110523")).thenReturn("OK");

        //act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("OK",resultat);
    }

    @Test
    public void slettKunde_LoggetInnFeil(){
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde("01010110523")).thenReturn("Feil");

        //act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("Feil",resultat);
    }

    @Test
    public void slettKunde_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("Ikke logget inn",resultat);
    }
}
