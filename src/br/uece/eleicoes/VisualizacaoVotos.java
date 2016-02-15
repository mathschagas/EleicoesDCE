package br.uece.eleicoes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.uece.eleicoes.model.Chapa;
import br.uece.eleicoes.model.ChapaDAO;
import br.uece.eleicoes.model.DAOFactory;
import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VisualizacaoVotos extends Application implements Initializable {

	private static Stage stage;
	private AnchorPane pane;
	private ObservableList<Chapa> chapasData = FXCollections.observableArrayList();
	DAOFactory fabrica = DAOFactory.getFabrica();
	@FXML
	private BarChart<String, Float> bcGraficoVotos;
	@FXML
	private TableView<Chapa> chapaTable;
	@FXML
	private TableColumn<Chapa, String> nomeChapaColumn;
	@FXML
	private TableColumn<Chapa, Integer> numVotosColumn;
	@FXML
	private TableColumn<Chapa, String> porcentagemVotosColumn;
	@FXML
	private Button btVoltar;
	@FXML
	private CategoryAxis eixoX;
	@FXML
	private NumberAxis eixoY;

	public VisualizacaoVotos() {
		ChapaDAO cdao = fabrica.getChapaDAO();
		ArrayList<Chapa> chapas = cdao.getChapas();
		chapasData.addAll(chapas);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ChapaDAO cdao = fabrica.getChapaDAO();

		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new MenuPrincipal().start(new Stage());
					VisualizacaoVotos.getStage().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		nomeChapaColumn.setCellValueFactory(new PropertyValueFactory<Chapa, String>("nome"));
		numVotosColumn.setCellValueFactory(new PropertyValueFactory<Chapa, Integer>("numVotos"));

		Integer total = cdao.getTotalDeVotos();

		porcentagemVotosColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Chapa, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Chapa, String> p) {
						Float porcentagem = (p.getValue().getNumVotos().floatValue() / total) * 100;
						SimpleStringProperty teste = new SimpleStringProperty(porcentagem.toString() + "%");
						return teste;
					}
				});
		chapaTable.setItems(chapasData);

		XYChart.Series<String, Float> series = new XYChart.Series<>();
		for (int i = 0; i < chapasData.size(); i++) {
			SimpleFloatProperty porcentagem = new SimpleFloatProperty(
					chapasData.get(i).getNumVotos().floatValue() * 100 / total);
			series.getData().add(new XYChart.Data<>(chapasData.get(i).getNome(), porcentagem.get()));
		}
		series.setName("% de Votos");
		bcGraficoVotos.getData().add(series);

	}

	@Override
	public void start(Stage stage) throws Exception {
		VisualizacaoVotos.stage = stage;
		VisualizacaoVotos.stage.setTitle("Eleições DCE - Visualização de Votos");
		VisualizacaoVotos.stage.setResizable(false);
		ShowVisualizaçãoDeVotosScreen();
	}

	private void ShowVisualizaçãoDeVotosScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VisualizacaoVotos.class.getResource("view/visualizacaoVotos.fxml"));
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

	public ObservableList<Chapa> getChapasData() {
		return chapasData;
	}

}
