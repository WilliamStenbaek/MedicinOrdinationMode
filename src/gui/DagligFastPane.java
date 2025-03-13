package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DagligFastPane extends GridPane {
    private TextField txtMorgen = new TextField();
    private TextField txtMiddag = new TextField();
    private TextField txtAften = new TextField();
    private TextField txtNat = new TextField();

    public DagligFastPane(int maxWidth) {
        setHgap(20);
        setVgap(10);
        setGridLinesVisible(false);
        setMaxWidth(maxWidth);

        add(new Label("Morgen"), 0, 0);
        add(new Label("Middag"), 1, 0);
        add(new Label("Aften"), 2, 0);
        add(new Label("Nat"), 3, 0);
        add(txtMorgen, 0, 1);
        add(txtMiddag, 1, 1);
        add(txtAften, 2, 1);
        add(txtNat, 3, 1);
    }

    public void setMorgen(String morgen) {
        txtMorgen.setText(morgen);
    }

    public void setMiddag(String middag) {
        txtMiddag.setText(middag);
    }

    public void setAften(String aften) {
        txtAften.setText(aften);
    }

    public void setNat(String nat) {
        txtNat.setText(nat);
    }

    public Double getMorgen() {
        return parseTextField(txtMorgen);
    }

    public Double getMiddag() {
        return parseTextField(txtMiddag);
    }

    public Double getAften() {
        return parseTextField(txtAften);
    }

    public Double getNat() {
        return parseTextField(txtNat);
    }

    private Double parseTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(textField.getText());
        }
    }
    
    public void makeReadOnly() {
        txtMorgen.setEditable(false);
        txtMiddag.setEditable(false);
        txtAften.setEditable(false);
        txtNat.setEditable(false);
    }
}
