package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{
    private ArrayList<Dosis> doser = new ArrayList<>();


    public void opretDosis(LocalTime tid, double antal) {
        doser.add(new Dosis(tid, antal));
    }


    @Override
    public double samletDosis() {
        double result = 0;
        for (Dosis d : doser){
            result += d.getAntal();
        }
        return result;
    }

    @Override
    public double doegnDosis() {
        return samletDosis() / ChronoUnit.DAYS.between(getStartDen(), getSlutDen());
    }

    @Override
    public String getType() {
        return "Skæv";
    }
}
