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
    private PN ordination;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        Controller.setStorage(storage);

        patient = new Patient("123456-7890", "Simon Kidde", 70.0);
        laegemiddel = new Laegemiddel("Paracetamol", 1, 2, 3, "Styk");

        storage.addPatient(patient);
        storage.addLaegemiddel(laegemiddel);

        ordination = Controller.opretPNOrdination(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 11),
                patient,
                laegemiddel,
                2.0
        );
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
        /*** ✅ TC1: Normal anvendelse af dosis ***/
        @Test
        void testOrdinationPNAnvendt_Normal() {
            LocalDate dato = LocalDate.of(2025, 3, 5);

            assertDoesNotThrow(() -> Controller.ordinationPNAnvendt(ordination, dato));
        }

        /*** ✅ TC2: Dosis gives på startdato ***/
        @Test
        void testOrdinationPNAnvendt_PaaStartDato() {
            LocalDate dato = LocalDate.of(2025, 3, 1);

            assertDoesNotThrow(() -> Controller.ordinationPNAnvendt(ordination, dato));
            assertEquals(1, ordination.getAntalGangeGivet(), "Dosis blev ikke korrekt registreret");
        }

        /*** ✅ TC3: Dosis gives på slutdato ***/
        @Test
        void testOrdinationPNAnvendt_PaaSlutDato() {
            LocalDate dato = LocalDate.of(2025, 3, 11);

            assertDoesNotThrow(() -> Controller.ordinationPNAnvendt(ordination, dato));
            assertEquals(1, ordination.getAntalGangeGivet(), "Dosis blev ikke korrekt registreret");
        }

        /*** ❌ TC4: Dato er før startdato ***/
        @Test
        void testOrdinationPNAnvendt_FoerStartDato() {
            LocalDate dato = LocalDate.of(2025, 2, 1);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    Controller.ordinationPNAnvendt(ordination, dato));

            assertEquals("du prøver at give en dosis udenfor rammerne", exception.getMessage());
        }

        /*** ❌ TC5: Dato er efter slutdato ***/
        @Test
        void testOrdinationPNAnvendt_EfterSlutDato() {
            LocalDate dato = LocalDate.of(2025, 3, 12);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    Controller.ordinationPNAnvendt(ordination, dato));

            assertEquals("du prøver at give en dosis udenfor rammerne", exception.getMessage());
        }



    //Test af metode anbefaletDosisPrDoegn


        @Test
        void testAnbefaletDosis_LavVaegt() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 20.0);
            assertEquals(20, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


        @Test
        void testAnbefaletDosis_Graense25() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 25.0);
            assertEquals(50, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


        @Test
        void testAnbefaletDosis_Mellemvægt() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 80.0);
            assertEquals(160, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


        @Test
        void testAnbefaletDosis_Graense120() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 120.0);
            assertEquals(240, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


        @Test
        void testAnbefaletDosis_TungVaegt() {
            Patient patient = Controller.opretPatient("1234567890", "Simon Baby", 140.0);
            assertEquals(420, Controller.anbefaletDosisPrDoegn(patient, laegemiddel));
        }


    //Test af metode antalOrdinationerPrVægtPrLægemiddel
    void setup2()   {
        patient = Controller.opretPatient("123", "Patient A", 50);
        Controller.opretPNOrdination(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), patient, laegemiddel, 1);
        var patient2 = Controller.opretPatient("123", "Patient B", 80);
        Controller.opretPNOrdination(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), patient2, laegemiddel, 1);
        Controller.opretPNOrdination(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), patient2, laegemiddel, 1);
        var patient3 = Controller.opretPatient("123", "Patient C", 130);
        Controller.opretPNOrdination(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1), patient3, laegemiddel, 1);
    }

    @Test
    void testAntalOrdinationerPrVægtPrLægemiddel_NormalCase() {
        setup2();

        assertEquals(4, Controller.antalOrdinationerPrVægtPrLægemiddel(40, 100, laegemiddel));
    }

    @Test
    void testAntalOrdinationerPrVægtPrLægemiddel_IngenPatienterIIntervallet() {
        setup2();

        assertEquals(0, Controller.antalOrdinationerPrVægtPrLægemiddel(10, 20, laegemiddel));
    }

    @Test
    void testAntalOrdinationerPrVægtPrLægemiddel_EnEnkeltPatient() {
        setup2();

        assertEquals(1, Controller.antalOrdinationerPrVægtPrLægemiddel(40, 60, laegemiddel));
    }

    @Test
    void testAntalOrdinationerPrVægtPrLægemiddel_FlerePatienterIngenLægemiddel() {
        setup2();

        var laegemiddel2 = new Laegemiddel(
                "Ibuprogen",
                0.1, 0.2, 0.4,
                "styk"
        );

        assertEquals(0, Controller.antalOrdinationerPrVægtPrLægemiddel(40, 100, laegemiddel2));
    }
}