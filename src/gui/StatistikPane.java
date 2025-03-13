package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ordination.Laegemiddel;

public class StatistikPane extends GridPane {
	private TextField ordinationerPerVægtPerLægemiddel = new TextField();
	private TextField txfVægtFra = new TextField();
	private TextField txfVægtTil = new TextField();
	private ComboBox<Laegemiddel> lstLægemidler = new ComboBox<Laegemiddel>();


	public StatistikPane() {

		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
		initContent();
	}

	private void initContent() {
		GridPane pane1 = new GridPane();
		pane1.setHgap(20);
		pane1.setVgap(10);
		pane1.setPadding(new Insets(10));

		GridPane pane2 = new GridPane();
		pane2.setHgap(20);
		pane2.setVgap(10);
		pane2.setPadding(new Insets(10));

		pane1.setStyle("-fx-border-color: grey;");
		pane2.setStyle("-fx-border-color: grey;");

		Label label = new Label("Antal ordinationer");
		label.setFont(new Font(25));
		this.add(label, 0, 0, 2, 1);

		txfVægtFra.setMaxWidth(40);
		txfVægtTil.setMaxWidth(40);
		pane1.add(new Label("Vægt fra: "), 0, 0);
		pane1.add(txfVægtFra, 1, 0);

		pane1.add(new Label("Vægt til: "), 0, 1);
		pane1.add(txfVægtTil, 1, 1);

		lstLægemidler.getItems().setAll(Controller.getAllLaegemidler());
		pane1.add(new Label("Lægemiddel: "), 0, 2);
		pane1.add(lstLægemidler, 1, 2);
		this.add(pane1, 0, 1);

		pane2.add(new Label("Antal: "), 0, 0);
		ordinationerPerVægtPerLægemiddel.setEditable(false);
		pane2.add(ordinationerPerVægtPerLægemiddel, 1, 0);
		this.add(pane2, 0, 2);

		// Adding listeners
		txfVægtFra.setOnKeyReleased(event -> updateDetails());
		txfVægtTil.setOnKeyReleased(event -> updateDetails());
		lstLægemidler.setOnAction(event -> updateDetails());

		updateDetails();
	}

	public void updateDetails() {
		try {
			int vFra = Integer.valueOf(txfVægtFra.getText());
			int vTil = Integer.valueOf(txfVægtTil.getText());
			Laegemiddel lægemiddel = lstLægemidler.getSelectionModel()
					.getSelectedItem();
			int antal = Controller.antalOrdinationerPrVægtPrLægemiddel(vFra, vTil,
					lægemiddel);
			ordinationerPerVægtPerLægemiddel.setText(antal + "");
		} catch (NumberFormatException e) {
			ordinationerPerVægtPerLægemiddel.setText("");
		}
	}
	
	public void updateControls() {
		ordinationerPerVægtPerLægemiddel.clear();
		txfVægtFra.clear();
		txfVægtTil.clear();
		lstLægemidler.getSelectionModel().clearSelection();
		
	}
}
