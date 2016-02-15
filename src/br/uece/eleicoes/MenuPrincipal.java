package br.uece.eleicoes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuPrincipal extends Application implements Initializable {

	private static Stage stage;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private Button btCadastrarChapa;
	
	@FXML
	private Button btRealizarVoto;
	
	@FXML
	private Button btVisualizarVotos;
	
	@FXML
	private Button btLogout;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				logout();
			}
		});
		
		btCadastrarChapa.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroChapa().start(new Stage());
					MenuPrincipal.getStage().close();
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btRealizarVoto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new VotoChapa().start(new Stage());
					MenuPrincipal.getStage().close();
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btVisualizarVotos.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new VisualizacaoVotos().start(new Stage());
					MenuPrincipal.getStage().close();
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void logout() {
		Sessao.destruirSessao();
		try {
			new Login().start(new Stage());
			MenuPrincipal.getStage().close();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		MenuPrincipal.stage = stage;
		MenuPrincipal.stage.setTitle("Eleições DCE - Menu");
		MenuPrincipal.stage.setResizable(false);
		showMenuScreen();
	}
	
	private void showMenuScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MenuPrincipal.class.getResource("view/main.fxml"));
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
}
