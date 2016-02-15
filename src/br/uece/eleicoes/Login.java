package br.uece.eleicoes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.uece.eleicoes.model.Aluno;
import br.uece.eleicoes.model.AlunoDAO;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Login extends Application implements Initializable {

	private static Stage primaryStage;

	@FXML
	private AnchorPane pane;
	@FXML
	private Button btnEntrar;
	@FXML
	private Button btnSair;
	@FXML
	private Button btnCadastrar;
	@FXML
	private TextField txMatricula;
	@FXML
	private PasswordField txSenha;

	@Override
	public void start(Stage primaryStage) {
		Login.primaryStage = primaryStage;
		Login.primaryStage.setTitle("Eleições DCE - Login");
		Login.primaryStage.setResizable(false);
		showLoginScreen();
	}

	private void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Login.class.getResource("view/login.fxml"));
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnSair.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sair();
			}
		});

		btnEntrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boolean loginValidado = validarLogin();
				if (loginValidado) {
					try {
						new MenuPrincipal().start(new Stage());
						Login.getStage().close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Matrícula e/ou senha inválidas", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnCadastrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroUsuario().start(new Stage());
					Login.getStage().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		txMatricula.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		            txMatricula.setText(oldValue);
		            txMatricula.positionCaret(txMatricula.getLength());
		        }
		    }

		});
	}

	private void sair() {
		System.exit(0);
	}
	
	private Boolean validarLogin() {
		// TODO Auto-generated method stub
		if (txMatricula.getText().equals("") || txSenha.getText().equals("")) {
			return false;
		} else {
			Aluno aluno = new Aluno();
			aluno.setNome("");
			aluno.setMatricula(Long.parseLong(txMatricula.getText()));
			aluno.setCurso("");
			aluno.setSenha(txSenha.getText());

			AlunoDAO dao = new AlunoDAO();
			try {
				Aluno alunoEncontrado = dao.getAluno(aluno.getMatricula());
				if (!alunoEncontrado.equals(null)) {
					Sessao.abrirSessao(alunoEncontrado.getMatricula(), alunoEncontrado.getIdChapaVotada());
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "Aluno não cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
