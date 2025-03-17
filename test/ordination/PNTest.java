package ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PNTest {

    private Laegemiddel laegemiddel1 = new Laegemiddel("Paracetamol", 0.2, 0.4, 0.8, "2");
    private PN pn1 = new PN(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 10),
            laegemiddel1, 2
    );
    private PN pn2 = new PN(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 1),
            laegemiddel1, 2
    );
    private PN pn4 = new PN(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 4),
            laegemiddel1, 1
    );


    //CONSTRUCTOR TESTS
    @Test
    void pnOprettelse()    {
        assertEquals(pn1.getStartDen(), LocalDate.of(2025, 1, 1));
        assertEquals(pn1.getSlutDen(), LocalDate.of(2025, 1, 10));
        assertEquals(pn1.getLaegemiddel(), laegemiddel1);
        assertEquals(pn1.getAntalEnheder(), 2);
    }

    @Test
    void pnOprettelseSammeDato()    {
        assertEquals(pn2.getStartDen(), LocalDate.of(2025, 1, 1));
        assertEquals(pn2.getSlutDen(), LocalDate.of(2025, 1, 1));
        assertEquals(pn2.getLaegemiddel(), laegemiddel1);
        assertEquals(pn2.getAntalEnheder(), 2);
    }

    @Test
    void pnEnhederUnderNul()    {
        Exception exception = assertThrows(RuntimeException.class,() ->{
            PN pn3 = new PN(
                    LocalDate.of(2025, 1, 1),
                    LocalDate.of(2025, 1, 10),
                    laegemiddel1, -1
            );
        });
    }

    //GIVDOSIS TESTS
    @Test
    void MindreEndStartDen()    {
        assertFalse(pn1.givDosis(LocalDate.of(2024, 1, 1)));
    }
    @Test
    void LigmedStartDen()    {
        assertEquals(0, pn1.getAntalGangeGivet());
        assertTrue(pn1.givDosis(LocalDate.of(2025, 1, 1)));
        assertEquals(1, pn1.getAntalGangeGivet());
    }
    @Test
    void StørreEndStartDen()    {
        assertEquals(0, pn1.getAntalGangeGivet());
        assertTrue(pn1.givDosis(LocalDate.of(2025, 1, 2)));
        assertEquals(1, pn1.getAntalGangeGivet());
    }
    @Test
    void MindreEndSlutDen()    {
        assertEquals(0, pn1.getAntalGangeGivet());
        assertTrue(pn1.givDosis(LocalDate.of(2025, 1, 10)));
        assertEquals(1, pn1.getAntalGangeGivet());
    }
    @Test
    void StørreEndSlutDen()    {
        assertFalse(pn1.givDosis(LocalDate.of(2025, 1, 11)));
    }

    //DOEGNDOSIS TESTS
    @Test
    void ingenGivninger()    {
        assertEquals(pn4.doegnDosis(), 0);
    }
    @Test
    void toGivninger()    {
        pn4.givDosis(LocalDate.of(2025, 1, 1));
        pn4.givDosis(LocalDate.of(2025, 1, 4));
        assertEquals(2, pn4.samletDosis());
        assertEquals(0.5, pn4.doegnDosis());
    }
    @Test
    void treGivninger()    {
        pn4.givDosis(LocalDate.of(2025, 1, 1));
        pn4.givDosis(LocalDate.of(2025, 1, 1));
        pn4.givDosis(LocalDate.of(2025, 1, 2));
        assertEquals(3, pn4.samletDosis());
        assertEquals(1.5, pn4.doegnDosis());
    }
}
