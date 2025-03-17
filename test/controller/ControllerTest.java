package controller;

import ordination.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Storage storage;
    private Patient patient;
    private Laegemiddel laegemiddel;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        Controller.setStorage(storage);

        patient = new Patient("123456-7890", "Simon Kidde", 70.0);
        laegemiddel = new Laegemiddel("Paracetamol", 0.1, 0.1, 0.1, "Styk");

        storage.addPatient(patient);
        storage.addLaegemiddel(laegemiddel);
    }

    /*** 🧪 TC1: Normal oprettelse af PN-ordination og forbindelse til patient ***/
    @Test
    void testOpretPNOrdination_Gyldig() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);
        double antal = 2.0;

        PN ordination = Controller.opretPNOrdination(start, slut, patient, laegemiddel, antal);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(start, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(slut, ordination.getSlutDen(), "Slutdato stemmer ikke");
        assertEquals(antal, ordination.getAntalEnheder(), "Antal enheder stemmer ikke");

        // Kontrollér at patienten har fået ordinationen tilføjet
        assertTrue(patient.getOrdinationer().contains(ordination), "Patienten har ikke fået tilføjet ordinationen");
    }

    /*** ✅ TC2: Start- og slutdato er den samme ***/
    @Test
    void testOpretPNOrdination_StartSlutSamme() {
        LocalDate dato = LocalDate.of(2025, 3, 1);
        double antal = 2.0;
        PN ordination = Controller.opretPNOrdination(dato, dato, patient, laegemiddel, antal);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(dato, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(dato, ordination.getSlutDen(), "Slutdato stemmer ikke");
        assertEquals(antal, ordination.getAntalEnheder(), "Antal enheder stemmer ikke");
    }

    /*** ❌ TC3: Startdato efter slutdato (ugyldigt input) ***/
    @Test
    void testOpretPNOrdination_StartEfterSlut() {
        LocalDate start = LocalDate.of(2025, 3, 2);
        LocalDate slut = LocalDate.of(2025, 3, 1);
        double antal = 2.0;

        // Test om IllegalArgumentException kastes
        assertThrows(IllegalArgumentException.class, () ->
                Controller.opretPNOrdination(start, slut, patient, laegemiddel, antal));
    }
}