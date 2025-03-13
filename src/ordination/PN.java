package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PN extends Ordination {
    private double antalEnheder;
    private List<LocalDate> givninger = new ArrayList<>();

    public PN(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel, double antalEnheder) {
        super(startDen, slutDen, laegemiddel);
        if (antalEnheder <= 0) {
            throw new IllegalArgumentException("Antal enheder skal være større end 0.");
        }
        this.antalEnheder = antalEnheder;
        this.givninger = new ArrayList<>();
    }


    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     *
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        if (givesDen.isBefore(getStartDen()) || givesDen.isAfter(getSlutDen())){
            return false;
        }
        givninger.add(givesDen);
        return true;

    }

    public double doegnDosis() {
        if (givninger.isEmpty())
            return 0.0;
        LocalDate førsteGivning = givninger.get(0);
        LocalDate sidsteGivning = givninger.get(givninger.size() - 1);
        int antalDage = (int) ChronoUnit.DAYS.between(førsteGivning, sidsteGivning) + 1;
        return samletDosis() / antalDage;
    }

    @Override
    public String getType() {
        return "PN";
    }


    public double samletDosis() {
        return getAntalGangeGivet() * getAntalEnheder();
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        return givninger.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
