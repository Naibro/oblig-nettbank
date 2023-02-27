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
import static org.mockito.ArgumentMatchers.anyString;
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

    //test av hentTransaksjoner
    @Test
    public void test_hentTransaksjonerOK(){
        // arrange
        List<Transaksjon> test = new ArrayList<>();
        Konto randomKonto = new Konto("01010110523","105010123456",619.50,"Lønnskonto","NOK",test);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner("105010123456","2002-04-02","2023-01-01")).thenReturn(randomKonto);

        // act
        Konto resultat = bankController.hentTransaksjoner("105010123456","2002-04-02","2023-01-01");

        // assert
        assertEquals(randomKonto, resultat);
    }

    //test av hentTransaksjoner
    @Test
    public void test_hentTransaksjonerFeil(){
        // arrange
        List<Transaksjon> test = new ArrayList<>();
        Konto randomKonto = new Konto("01010110523","105010123456",619.50,"Lønnskonto","NOK",test);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner("105010123456","2002-04-02","2023-01-01")).thenReturn(randomKonto);

        // act (f.eks feil kontonummer)
        Konto resultat = bankController.hentTransaksjoner("10501012345","2002-04-02","2023-01-01");

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
}

