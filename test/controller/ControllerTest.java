package controller;

import ordination.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;

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
        laegemiddel = new Laegemiddel("Paracetamol", 1, 2, 3, "Styk");

        storage.addPatient(patient);
        storage.addLaegemiddel(laegemiddel);
    }

    //Test til metode OpretPNOrdination
    /*** ✅ TC1: Normal oprettelse af PN-ordination og forbindelse til patient ***/
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

    //Test af metode opretDagligFastOrdination
    /*** ✅ TC1: Normal oprettelse og forbindelse til patient ***/
    @Test
    void testOpretDagligFastOrdination_Normal() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);

        DagligFast ordination = Controller.opretDagligFastOrdination(start, slut, patient, laegemiddel, 2.0, 2.0, 2.0, 2.0);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(start, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(slut, ordination.getSlutDen(), "Slutdato stemmer ikke");
        assertArrayEquals(new double[]{2.0, 2.0, 2.0, 2.0}, getDoserAsArray(ordination), "Doser stemmer ikke");

        // ✅ Kontrollér at patienten har fået ordinationen tilføjet
        assertTrue(patient.getOrdinationer().contains(ordination), "Patienten har ikke fået tilføjet ordinationen");
    }

    /*** ✅ TC2: Start- og slutdato samme ***/
    @Test
    void testOpretDagligFastOrdination_StartSlutSamme() {
        LocalDate dato = LocalDate.of(2025, 3, 1);

        DagligFast ordination = Controller.opretDagligFastOrdination(dato, dato, patient, laegemiddel, 2.0, 2.0, 2.0, 2.0);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(dato, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(dato, ordination.getSlutDen(), "Slutdato stemmer ikke");
    }

    /*** ✅ TC3: Minimumsdosis (alle doser = 0) ***/
    @Test
    void testOpretDagligFastOrdination_MinimumsDosis() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);

        DagligFast ordination = Controller.opretDagligFastOrdination(start, slut, patient, laegemiddel, 0.0, 0.0, 0.0, 0.0);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertArrayEquals(new double[]{0.0, 0.0, 0.0, 0.0}, getDoserAsArray(ordination), "Doser stemmer ikke");
    }

    /*** ✅ TC4: Forskellige doser ***/
    @Test
    void testOpretDagligFastOrdination_ForskelligeDoser() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);

        DagligFast ordination = Controller.opretDagligFastOrdination(start, slut, patient, laegemiddel, 2.0, 1.5, 0.5, 2.0);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertArrayEquals(new double[]{2.0, 1.5, 0.5, 2.0}, getDoserAsArray(ordination), "Doser stemmer ikke");
    }

    /*** ❌ TC5: Startdato efter slutdato (ugyldigt input) ***/
    @Test
    void testOpretDagligFastOrdination_StartEfterSlut() {
        LocalDate start = LocalDate.of(2025, 3, 2);
        LocalDate slut = LocalDate.of(2025, 3, 1);

        assertThrows(IllegalArgumentException.class, () ->
                        Controller.opretDagligFastOrdination(start, slut, patient, laegemiddel, 2.0, 2.0, 2.0, 2.0),
                "Forventet IllegalArgumentException, men den blev ikke kastet");
    }

    private double[] getDoserAsArray(DagligFast ordination) {
        return new double[]{
                ordination.getDoser()[0].getAntal(),
                ordination.getDoser()[1].getAntal(),
                ordination.getDoser()[2].getAntal(),
                ordination.getDoser()[3].getAntal()
        };
    }


    //Test af metode opretDagligSkaevOrdination
    /*** ✅ TC1: Normal oprettelse og forbindelse til patient ***/
    @Test
    void testOpretDagligSkaevOrdination_Normal() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);
        LocalTime[] tider = {LocalTime.of(8, 0), LocalTime.of(12, 0), LocalTime.of(18, 0)};
        double[] doser = {2.0, 1.5, 3.0};

        DagligSkaev ordination = Controller.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tider, doser);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(start, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(slut, ordination.getSlutDen(), "Slutdato stemmer ikke");
        assertEquals(3, ordination.getDoser().size(), "Antal doser er forkert");

        // ✅ Kontrollér at patienten har fået ordinationen tilføjet
        assertTrue(patient.getOrdinationer().contains(ordination), "Patienten har ikke fået tilføjet ordinationen");
    }

    /*** ✅ TC2: Start- og slutdato samme ***/
    @Test
    void testOpretDagligSkaevOrdination_StartSlutSamme() {
        LocalDate dato = LocalDate.of(2025, 3, 1);
        LocalTime[] tider = {LocalTime.of(8, 0)};
        double[] doser = {2.0};

        DagligSkaev ordination = Controller.opretDagligSkaevOrdination(dato, dato, patient, laegemiddel, tider, doser);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(dato, ordination.getStartDen(), "Startdato stemmer ikke");
        assertEquals(dato, ordination.getSlutDen(), "Slutdato stemmer ikke");
    }

    /*** ✅ TC3: Minimumsdosis (kun en dosis) ***/
    @Test
    void testOpretDagligSkaevOrdination_MinimumsDosis() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);
        LocalTime[] tider = {LocalTime.of(8, 0)};
        double[] doser = {1.0};

        DagligSkaev ordination = Controller.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tider, doser);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(1, ordination.getDoser().size(), "Antal doser er forkert");
    }

    /*** ✅ TC4: Forskellige doser ***/
    @Test
    void testOpretDagligSkaevOrdination_ForskelligeDoser() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);
        LocalTime[] tider = {LocalTime.of(8, 0), LocalTime.of(14, 0), LocalTime.of(20, 0)};
        double[] doser = {1.0, 2.0, 0.5};

        DagligSkaev ordination = Controller.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tider, doser);

        assertNotNull(ordination, "Ordinationen skulle være oprettet");
        assertEquals(3, ordination.getDoser().size(), "Antal doser er forkert");
    }

    /*** ❌ TC5: Startdato efter slutdato (ugyldigt input) ***/
    @Test
    void testOpretDagligSkaevOrdination_StartEfterSlut() {
        LocalDate start = LocalDate.of(2025, 3, 2);
        LocalDate slut = LocalDate.of(2025, 3, 1);
        LocalTime[] tider = {LocalTime.of(8, 0)};
        double[] doser = {2.0};

        assertThrows(IllegalArgumentException.class, () ->
                        Controller.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tider, doser),
                "Forventet IllegalArgumentException, men den blev ikke kastet");
    }

    /*** ❌ TC6: Forskelligt antal elementer i klokkeSlet og antalEnheder ***/
    @Test
    void testOpretDagligSkaevOrdination_UgyldigDosisLængde() {
        LocalDate start = LocalDate.of(2025, 3, 1);
        LocalDate slut = LocalDate.of(2025, 3, 11);
        LocalTime[] tider = {LocalTime.of(8, 0), LocalTime.of(12, 0)};
        double[] doser = {1.0};

        assertThrows(IllegalArgumentException.class, () ->
                        Controller.opretDagligSkaevOrdination(start, slut, patient, laegemiddel, tider, doser),
                "Forventet IllegalArgumentException, men den blev ikke kastet");
    }

    //Test af metode ordinationPNAnvendt
    //Test af metode anbefaletDosisPrDoegn


        @Test
        void testAnbefaletDosis_LavVaegt() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 20.0);
            assertEquals(20, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


        @Test
        void testAnbefaletDosis_Graense25() {
            // TODO: Implement test
        }


        @Test
        void testAnbefaletDosis_Mellemvægt() {
            // TODO: Implement test
        }


        @Test
        void testAnbefaletDosis_Graense120() {
            // TODO: Implement test
        }


        @Test
        void testAnbefaletDosis_TungVaegt() {
            // TODO: Implement test
        }


    //Test af metode antalOrdinationerPrVægtPrLægemiddel

}