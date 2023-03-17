package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_LoggetInn(){
        // Arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("01010110523", "105010123455",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("01010110523", "123456789012",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.hentAlleKonti()).thenReturn(konti);

        // Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // Assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_LoggetInn() {
        // Arrange
        Konto tilfeldigKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        //Act
        String resultat = adminKontoController.registrerKonto(tilfeldigKonto);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_LoggetInn_Feil() {
        // Arrange
        Konto tilfeldigKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.registrerKonto(any(Konto.class))).thenReturn("Feil");

        // Act
        String resultat = adminKontoController.registrerKonto(tilfeldigKonto);

        // Assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerKonti_IkkeLoggetInn() {
        // Arrange
        Konto tilfelidigKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.registrerKonto(tilfelidigKonto);

        // ASsert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_LoggetInn() {
        // Arrange
        Konto endretKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        // Act
        String resultat = adminKontoController.endreKonto(endretKonto);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_LoggetInn_Feil() {
        // Arrange
        Konto endretKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.endreKonto(any(Konto.class))).thenReturn("Feil");

        // Act
        String resultat = adminKontoController.endreKonto(endretKonto);

        // Assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKonto_IkkeLoggetInn(){
        // Arrange
        Konto endretKonto = new Konto("01010110523", "105010123455",
                720, "Lonnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.endreKonto(endretKonto);

        // Assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_LoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.slettKonto(anyString())).thenReturn("OK");

        // Act
        String resultat = adminKontoController.slettKonto("105010123456");

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_LoggetInn_Feil() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.slettKonto(anyString())).thenReturn("Feil kontonummer");

        // Act
        String resultat = adminKontoController.slettKonto("105010123456");

        // Assert
        assertEquals("Feil kontonummer", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.slettKonto("105010123456");

        // Assert
        assertEquals("Ikke innlogget", resultat);
    }
}
