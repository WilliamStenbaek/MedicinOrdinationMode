package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import ordination.Laegemiddel;
import ordination.Patient;

public class OpretOrdinationPane extends GridPane {

	private ListView<Patient> lstPatient = new ListView<>();
	private ListView<Laegemiddel> lstLaegemiddel = new ListView<>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	private RadioButton rbPN = new RadioButton("PN");
	private RadioButton rbFast = new RadioButton("Daglig fast");
	private RadioButton rbSkaev = new RadioButton("Daglig skæv");
	private Button btnOpret = new Button("Opret ordination");
	private Label lblError;



	public OpretOrdinationPane() {


		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);

		lblError = new Label();
		lblError.setTextFill(Color.RED);

		rbPN.setUserData(TypeOrdination.PN);
		rbSkaev.setUserData(TypeOrdination.SKAEV);
		rbFast.setUserData(TypeOrdination.FAST);
		toggleGroup.getToggles().add(rbPN);
		toggleGroup.getToggles().add(rbSkaev);
		toggleGroup.getToggles().add(rbFast);

		this.add(new Label("Vælg patient"), 0, 0);
		this.add(lstPatient, 0, 1, 1, 2);
		lstPatient.getItems().setAll(Controller.getAllPatienter());

		this.add(new Label("Vælg lægemiddel"), 1, 0);
		this.add(lstLaegemiddel, 1, 1, 1, 2);
		lstLaegemiddel.getItems().setAll(Controller.getAllLaegemidler());

		this.add(new Label("Vælg ordination"), 2, 0);
		GridPane innerPane = new GridPane();
		innerPane.setVgap(10);
		innerPane.add(rbPN, 0, 0);
		innerPane.add(rbSkaev, 0, 1);
		innerPane.add(rbFast, 0, 2);

		this.add(innerPane, 2, 1);

		this.add(lblError, 0, 3, 1, 2);
		this.add(btnOpret, 2, 2);
		btnOpret.setOnAction(event -> actionOpret());

		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();
		row3.setValignment(VPos.BOTTOM);
		getRowConstraints().addAll(row1, row2, row3);
	}

	public void actionOpret() {
		lblError.setText("");
		if (lstPatient.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("Du skal vælge en patient.");
		} else if (lstLaegemiddel.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("Du skal vælge et lægemiddel.");
		} else if (toggleGroup.getSelectedToggle() == null) {
			lblError.setText("Du skal vælge en ordinationstype.");
		} else {
			OpretOrdinationDialog dia = new OpretOrdinationDialog(lstPatient
					.getSelectionModel().getSelectedItem(), lstLaegemiddel
					.getSelectionModel().getSelectedItem(),
					(TypeOrdination) toggleGroup.getSelectedToggle()
					.getUserData());
			dia.showAndWait();
		}
	}
	
	public void updateControls() {
		lstPatient.getSelectionModel().clearSelection();
		lstLaegemiddel.getSelectionModel().clearSelection();
		toggleGroup.selectToggle(null);
	}
}
