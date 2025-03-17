package ordination;

import java.time.LocalDate;

public class PNTest {

    private Laegemiddel laegemiddel1 = new Laegemiddel("Paracetamol", 0.2, 0.4, 0.8, "2");
    private Patient patient1 = new Patient("123456-7890", "Grejbob Sanoj", 80.1);
    private PN pn = new PN(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 10),
            laegemiddel1, 2
    );
}
