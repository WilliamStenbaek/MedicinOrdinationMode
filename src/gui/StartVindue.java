package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartVindue extends Application {
	private Controller controller;

	

	@Override
	public void init() {

	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Medicinordination");
		BorderPane pane = new BorderPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setHeight(500);
		stage.setWidth(800);
		stage.show();
	}

	private void initContent(BorderPane pane) {
		TabPane tabPane = new TabPane();
		this.initTabPane(tabPane);
		pane.setCenter(tabPane);
	}

	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tabOpret = new Tab("Opret ordinationer");
		Tab tabVis = new Tab("Vis ordinationer");
		Tab tabStatistik = new Tab("Vis statistik");

		OpretOrdinationPane opretOrdinationsPane = new OpretOrdinationPane();
		tabOpret.setContent(opretOrdinationsPane);
		VisOrdinationPane visOrdinationPane = new VisOrdinationPane();
		tabVis.setContent(visOrdinationPane);
		StatistikPane statisikPane = new StatistikPane();
		tabStatistik.setContent(statisikPane);

		tabPane.getTabs().add(tabOpret);
		tabPane.getTabs().add(tabVis);
		tabPane.getTabs().add(tabStatistik);

		tabVis.setOnSelectionChanged(event -> visOrdinationPane
				.updateControls());
		tabStatistik.setOnSelectionChanged(event -> statisikPane
				.updateControls());
		tabOpret.setOnSelectionChanged(event -> opretOrdinationsPane.updateControls());

	}

}
