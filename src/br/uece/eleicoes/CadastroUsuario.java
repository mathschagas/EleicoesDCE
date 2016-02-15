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

public class CadastroUsuario extends Application implements Initializable {

	private static Stage primaryStage;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private TextField txMatricula;
	
	@FXML
	private TextField txNome;
	
	@FXML
	private TextField txCurso;
	
	@FXML
	private PasswordField txSenha;
	
	@FXML
	private PasswordField txConfirmaSenha;
	
	@FXML
	private Button btConcluir;
	
	@FXML
	private Button btCancelar;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		CadastroUsuario.primaryStage = primaryStage;
		CadastroUsuario.primaryStage.setTitle("Cadastro de Aluno");
		CadastroUsuario.primaryStage.setResizable(false);
		showCadastroUsuarioScreen();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void showCadastroUsuarioScreen() {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CadastroUsuario.class.getResource("view/cadastroUsuario.fxml"));
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage() {
		return primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				voltarParaTelaLogin();
			}
		});
		
		btConcluir.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				efetuarCadastroUsuario();
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
	
	

	private void efetuarCadastroUsuario() {
		String nome = txNome.getText();
		String curso = txCurso.getText();
		String senha = null;
		Long matricula = null;
		if (!txMatricula.getText().equals("")) {
			matricula = Long.parseLong(txMatricula.getText());
		}
		if (txSenha.getText().equals(txConfirmaSenha.getText())) {
			if (!txSenha.getText().equals("")){
				senha = txSenha.getText();
			}
			
		}
		
		if (nome != "" && curso != "" && senha != null && matricula != null) {
			Aluno novoAluno = new Aluno();
			novoAluno.setNome(nome);
			novoAluno.setCurso(curso);
			novoAluno.setMatricula(matricula);
			novoAluno.setSenha(senha);
			
			AlunoDAO ad = new AlunoDAO();
			try {
				ad.adiciona(novoAluno);
				JOptionPane.showMessageDialog(null, "Aluno cadastrado.", "Sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
				voltarParaTelaLogin();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Matrícula já cadastrada.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			if (senha == null) {
				JOptionPane.showMessageDialog(null, "Campos 'Senha' e 'Confirme Senha' estão diferentes ou não preenchidos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Você deve preencher todos os campos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void voltarParaTelaLogin() {
		try {
			new Login().start(new Stage());
			CadastroUsuario.getStage().close();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

}
