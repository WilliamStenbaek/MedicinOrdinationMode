package ordination;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                    LocalDate.of(2025, 1, 1),
                    laegemiddel1, -1
            );
        });
    }
}
