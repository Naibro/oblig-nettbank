package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjonerLoggetInn(){
        // arrange
        List<Transaksjon> test = new ArrayList<>();
        Konto tilfeldigKonto = new Konto("01010110523","105010123456",619.50,"Lønnskonto","NOK",test);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner("105010123456","2002-04-02","2023-01-01")).thenReturn(tilfeldigKonto);

        // act
        Konto resultat = bankController.hentTransaksjoner("105010123456","2002-04-02","2023-01-01");

        // assert
        assertEquals(tilfeldigKonto, resultat);
    }

    @Test
    public void hentTransaksjonerIkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("105010123456","2002-04-02","2023-01-01");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn(){
        // arrange
        List<Konto> konto = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konto.add(konto1);
        konto.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(anyString())).thenReturn(konto);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konto, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_loggetInn(){
        // arrange
        Transaksjon tilfeldigTransaksjon = new Transaksjon(3, "105010123456", 36,
                "2020-05-05", "Pizza", "1", "123123123123");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(tilfeldigTransaksjon);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_loggetInn_Feil(){
        // arrange
        Transaksjon tilfeldigTransaksjon = new Transaksjon(3, "105010123456", 36,
                "2020-05-05", "Pizza", "1", "123123123123");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Feil");

        // act
        String resultat = bankController.registrerBetaling(tilfeldigTransaksjon);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        // arrange
        Transaksjon tilfeldigTransaksjon = new Transaksjon(3, "105010123456", 36,
                "2020-05-05", "Pizza", "1", "123123123123");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(tilfeldigTransaksjon);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_loggetInn(){
        // arrange
        List<Transaksjon> test = new ArrayList<>();
        Transaksjon tilfeldigTransaksjon = new Transaksjon(1, "20102012345", 20,
                "2020-05-05", "Iskrem", "1", "105010123456");
        Transaksjon tilfeldigTransaksjon2 = new Transaksjon(2, "20102012345", 50,
                "2020-05-06", "Smash", "1", "105010123456");
        test.add(tilfeldigTransaksjon);
        test.add(tilfeldigTransaksjon2);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(anyString())).thenReturn(test);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(test, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_loggetInn(){
        // arrange
        List<Transaksjon> test = new ArrayList<>();
        Transaksjon tilfeldigTransaksjon = new Transaksjon(1, "20102012345", 20,
                "2020-05-05", "Iskrem", "1", "105010123456");
        test.add(tilfeldigTransaksjon);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(anyString())).thenReturn(test);
        when(repository.utforBetaling(anyInt())).thenReturn("OK");

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertEquals(test, resultat);
    }

    @Test
    public void utforBetaling_loggetInn_Feil(){
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(anyInt())).thenReturn("Feil");

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
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
        String resultat = bankController.endre(endretKunde);

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
        String resultat = bankController.endre(endretKunde_nyttPoststed);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_Feil(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn_PoststedFeil(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "4033",
                "Stavanger", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

