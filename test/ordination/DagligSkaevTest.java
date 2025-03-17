package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
class DagligSkaevTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void constructorTest(){
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 1);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        assertNotNull(dagligSkaev.getLaegemiddel());
        assertEquals(dagligSkaev.antalDage(), 1);
    }

    @Test
    void opretDosisTC1() {
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 1);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        dagligSkaev.opretDosis(LocalTime.of(12,0), 1);

        assertEquals(1, dagligSkaev.getDoser().size());
        assertNotNull(dagligSkaev.getDoser().get(0));
    }

    @Test
    void samletDosisTC1() {
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 1);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        dagligSkaev.opretDosis(LocalTime.of(1,0), 1);
        dagligSkaev.opretDosis(LocalTime.of(2,0), 2);
        dagligSkaev.opretDosis(LocalTime.of(3,0), 3);

        assertEquals(dagligSkaev.samletDosis(), 6);
    }

    @Test
    void samletDosisTC2() {
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 7);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        dagligSkaev.opretDosis(LocalTime.of(1,0), 1);
        dagligSkaev.opretDosis(LocalTime.of(2,0), 2);
        dagligSkaev.opretDosis(LocalTime.of(3,0), 3);

        assertEquals(dagligSkaev.samletDosis(), 42);
    }

    @Test
    void doegnDosisTC1() {
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 1);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        dagligSkaev.opretDosis(LocalTime.of(1,0), 1);
        dagligSkaev.opretDosis(LocalTime.of(2,0), 2);
        dagligSkaev.opretDosis(LocalTime.of(3,0), 3);

        assertEquals(dagligSkaev.doegnDosis(), 6);
    }

    @Test
    void doegnDosisTC2() {
        LocalDate startDato = LocalDate.of(2000, 1, 1);
        LocalDate slutDato = LocalDate.of(2000, 1, 7);
        Laegemiddel laegemiddel = new Laegemiddel("Acetylsalicylsyre", 1, 2, 3, "Styk");
        DagligSkaev dagligSkaev = new DagligSkaev(startDato, slutDato, laegemiddel);

        dagligSkaev.opretDosis(LocalTime.of(1,0), 1);
        dagligSkaev.opretDosis(LocalTime.of(2,0), 2);
        dagligSkaev.opretDosis(LocalTime.of(3,0), 3);

        assertEquals(dagligSkaev.doegnDosis(), 6);
    }
}