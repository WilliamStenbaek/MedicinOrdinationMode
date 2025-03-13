package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.time.DateTimeException;
import java.time.LocalTime;

public class DagligSkaevPane extends GridPane {
    private TextField txtTimeMinut = new TextField();
    private TextField txtMaengde = new TextField();
    private Button btnOpret = new Button("Opret dosis");
    private ListView<String> listDosis = new ListView<>();
    private Label lblError = new Label();

    public DagligSkaevPane() {
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);

        txtTimeMinut.setPromptText("TT:MM");
        txtMaengde.setPromptText("Mængde");

        HBox hbox = new HBox(8);
        hbox.getChildren().add(txtTimeMinut);
        hbox.getChildren().add(txtMaengde);
        hbox.getChildren().add(btnOpret);
        add(hbox, 0, 0);

        listDosis.setMaxHeight(100);
        add(listDosis, 0, 1);

        btnOpret.setOnAction(event -> opretDosis());
        
		lblError.setTextFill(Color.RED);
		add(lblError, 0, 2, 2, 1);

    }

    private void opretDosis() {
		lblError.setText("");
		String kl = txtTimeMinut.getText();
		String mgd = txtMaengde.getText();
		try {
			double mdgdouble = Double.parseDouble(mgd);
			if (mdgdouble <= 0) {
				txtMaengde.clear();
				lblError.setText("Mængde  eller klokkeslet er ikke korrekt format");
				return;
			}
			int hour = Integer.parseInt(kl.substring(0, 2));
			int minute = Integer.parseInt(kl.substring(3));
			LocalTime.of(hour, minute);
			String dosis = kl + " " + mgd;
			listDosis.getItems().add(dosis);
		} catch (NumberFormatException e) {
			txtMaengde.clear();
			txtTimeMinut.clear();
			lblError.setText("Mængde  eller klokkeslet er ikke korrekt format");
		} catch (DateTimeException e) {
			txtTimeMinut.clear();
			lblError.setText("Klokkeslet er ikke korrekt format");

		}

    }

    public String[] getDosisArray() {
        ObservableList<String> items = listDosis.getItems();
        return items.toArray(new String[items.size()]);
    }
}
