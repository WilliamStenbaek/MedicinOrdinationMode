package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DagligFast extends Ordination{
    private Dosis[] doser = new Dosis[4];

    public DagligFast(LocalDate startDen,
                      LocalDate slutDen, Laegemiddel laegemiddel,
                      double morgenAntal, double middagAntal, double aftenAntal,
                      double natAntal){
        super(startDen, slutDen, laegemiddel);

        doser[0] = new Dosis(LocalTime.of(8,0), morgenAntal);
        doser[1] = new Dosis(LocalTime.of(12,0), middagAntal);
        doser[2] = new Dosis(LocalTime.of(16,0), aftenAntal);
        doser[3] = new Dosis(LocalTime.of(20,0), natAntal);

    }

    @Override
    public double samletDosis() {
        double result = 0;
        for (Dosis d : doser){
            result += d.getAntal();
        }
        return result * antalDage();
    }

    @Override
    public double doegnDosis() {
        return samletDosis() / antalDage();
    }

    @Override
    public String getType() {
        return "Fast";
    }
}
