package gui;

import controller.Controller;
import javafx.application.Application;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;

public class App {

	public static void main(String[] args) {

		Storage storage = new Storage();
		Controller.setStorage(storage);

		initStorage();

		Application.launch(StartVindue.class);
	}

	public static void initStorage() {
		Controller.opretPatient("121256-0512", "Jane Jensen", 63.4);
		Controller.opretPatient("070985-1153", "Finn Madsen", 83.2);
		Controller.opretPatient("050972-1233", "Hans Jørgensen", 89.4);
		Controller.opretPatient("011064-1522", "Ulla Nielsen", 59.9);
		Controller.opretPatient("090149-2529", "Ib Hansen", 87.7);

		Controller.opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
		Controller.opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
		Controller.opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
		Controller.opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");

		Controller.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12), Controller.getAllPatienter().get(0),
				Controller.getAllLaegemidler().get(1), 123);

		Controller.opretPNOrdination(LocalDate.of(2021, 2, 12), LocalDate.of(2021, 2, 14), Controller.getAllPatienter().get(0),
				Controller.getAllLaegemidler().get(0), 3);

		Controller.opretPNOrdination(LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 25), Controller.getAllPatienter().get(3),
				Controller.getAllLaegemidler().get(2), 5);

		Controller.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12), Controller.getAllPatienter().get(0),
				Controller.getAllLaegemidler().get(1), 123);

		Controller.opretDagligFastOrdination(LocalDate.of(2021, 1, 10), LocalDate.of(2021, 1, 12),
				Controller.getAllPatienter().get(1), Controller.getAllLaegemidler().get(1), 2, 0, 1, 0);

		LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40), LocalTime.of(16, 0), LocalTime.of(18, 45) };
		double[] an = { 0.5, 1, 2.5, 3 };

		Controller.opretDagligSkaevOrdination(LocalDate.of(2021, 1, 23), LocalDate.of(2021, 1, 24),
				Controller.getAllPatienter().get(1), Controller.getAllLaegemidler().get(2), kl, an);
	}

}
