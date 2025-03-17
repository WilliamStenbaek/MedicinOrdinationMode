package ordination;

import ordination.DagligFast;
import ordination.Dosis;
import ordination.Laegemiddel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Arrays;

class DagligFastTest {
    private LocalDate startDate = LocalDate.of(2000,1,1);
    private LocalDate slutDate = LocalDate.of(2000, 1, 1);
    private int morgenTal = 0;
    private int middagsTal = 1;
    private int aftenTal = 2;
    private int natTal = 3;
    private Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
    DagligFast dagligFast = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

    @BeforeEach
    void setUp() {
    }

    @Test
    void constructorTest(){
        assertNotNull(dagligFast.getDoser()[0]);
        assertNotNull(dagligFast.getDoser()[1]);
        assertNotNull(dagligFast.getDoser()[2]);
        assertNotNull(dagligFast.getDoser()[3]);

        assertNotNull(dagligFast.getLaegemiddel());

        assertEquals(dagligFast.antalDage(), 1);
    }

    @Test
    void samletDosisTC1() {
        //alle tal er lig nul
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 1);
        int morgenTal = 0;
        int middagsTal = 0;
        int aftenTal = 0;
        int natTal = 0;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.samletDosis(), 0);
    }
    @Test
    void samletDosisTC2() {
        //ændret dato
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 8);
        int morgenTal = 0;
        int middagsTal = 0;
        int aftenTal = 0;
        int natTal = 0;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.samletDosis(), 0);
    }

    @Test
    void samletDosisTC3() {
        //alle tal er basis
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 1);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.samletDosis(), 6);
    }

    @Test
    void samletDosisTC4() {
        //ændret dato
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 7);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.samletDosis(), 42);
    }

    @Test
    void doegnDosisTC1() {
        //alle tal er lig nul
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 1);
        int morgenTal = 0;
        int middagsTal = 0;
        int aftenTal = 0;
        int natTal = 0;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.samletDosis(), 0);
    }

    @Test
    void doegnDosisTC2() {
        //dato ændret
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 8);
        int morgenTal = 0;
        int middagsTal = 0;
        int aftenTal = 0;
        int natTal = 0;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.doegnDosis(), 0);
    }

    @Test
    void doegnDosisTC3() {
        //basistal
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 8);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.doegnDosis(), 6);
    }

    @Test
    void doegnDosisTC4() {
        //dato ændret til 7 dage
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 7);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.doegnDosis(), 6);
    }

    @Test
    void getDoserTC1() {
        //tester at doserne kan tilgås
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 1);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        assertEquals(dagligFast2.getDoser()[0].getAntal(), 0);
        assertEquals(dagligFast2.getDoser()[1].getAntal(), 1);
        assertEquals(dagligFast2.getDoser()[2].getAntal(), 2);
        assertEquals(dagligFast2.getDoser()[3].getAntal(), 3);
    }

    @Test
    void getDoserTC2() {
        //tester getdoser returnerer en kopi
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate slutDate = LocalDate.of(2000, 1, 1);
        int morgenTal = 0;
        int middagsTal = 1;
        int aftenTal = 2;
        int natTal = 3;
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligFast dagligFast2 = new DagligFast(startDate, slutDate, laegemiddel, morgenTal, middagsTal, aftenTal, natTal);

        Arrays.fill(dagligFast2.getDoser(), null);

        assertEquals(dagligFast2.getDoser()[0].getAntal(), 0);
        assertEquals(dagligFast2.getDoser()[1].getAntal(), 1);
        assertEquals(dagligFast2.getDoser()[2].getAntal(), 2);
        assertEquals(dagligFast2.getDoser()[3].getAntal(), 3);
    }
}

