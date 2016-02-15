package br.uece.eleicoes;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.uece.eleicoes.model.Aluno;
import br.uece.eleicoes.model.AlunoDAO;
import br.uece.eleicoes.model.Chapa;
import br.uece.eleicoes.model.ChapaDAO;
import br.uece.eleicoes.model.DAOFactory;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VotoChapa extends Application implements Initializable {

	private static Stage stage;
	private ObservableList<String> nomesChapas = FXCollections.observableArrayList();
	DAOFactory fabrica = DAOFactory.getFabrica();

	@FXML
	private AnchorPane pane;

	@FXML
	private ChoiceBox<String> cbChapas = new ChoiceBox<String>();

	@FXML
	private Label lbNomePresidente;
	@FXML
	private Label lbCursoPresidente;
	@FXML
	private Label lbMatriculaPresidente;

	@FXML
	private Label lbNomeSecretario;
	@FXML
	private Label lbCursoSecretario;
	@FXML
	private Label lbMatriculaSecretario;

	@FXML
	private Label lbNomeTesoureiro;
	@FXML
	private Label lbCursoTesoureiro;
	@FXML
	private Label lbMatriculaTesoureiro;

	@FXML
	private Button btVoltar;
	@FXML
	private Button btVotar;

	@Override
	public void start(Stage stage) throws Exception {
		VotoChapa.stage = stage;
		VotoChapa.stage.setTitle("Eleições DCE - Votar Chapa");
		VotoChapa.stage.setResizable(false);
		showVotoScreen();
	}

	private void showVotoScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VotoChapa.class.getResource("view/votoChapa.fxml"));
			pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ChapaDAO cdao = fabrica.getChapaDAO();
		nomesChapas.addAll(cdao.getListaNomesChapas());
		cbChapas.setItems(nomesChapas);

		cbChapas.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				try {
					Chapa chapaSelecionada = cdao.getChapaPorNome(nomesChapas.get(newValue.intValue()));
					AlunoDAO adao = fabrica.getAlunoDAO();
					Aluno presidente = adao.getAluno(chapaSelecionada.getMatPresidente());
					Aluno secretario = adao.getAluno(chapaSelecionada.getMatSecretario());
					Aluno tesoureiro = adao.getAluno(chapaSelecionada.getMatTesoureiro());

					lbNomePresidente.setText(presidente.getNome());
					lbCursoPresidente.setText(presidente.getCurso());
					lbMatriculaPresidente.setText(presidente.getMatricula().toString());
					lbNomeSecretario.setText(secretario.getNome());
					lbCursoSecretario.setText(secretario.getCurso());
					lbMatriculaSecretario.setText(secretario.getMatricula().toString());
					lbNomeTesoureiro.setText(tesoureiro.getNome());
					lbCursoTesoureiro.setText(tesoureiro.getCurso());
					lbMatriculaTesoureiro.setText(tesoureiro.getMatricula().toString());

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new MenuPrincipal().start(new Stage());
					VotoChapa.getStage().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btVotar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Integer itemSelecionado = cbChapas.getSelectionModel().getSelectedIndex();
				if (itemSelecionado != -1) {
					if (Sessao.getIdChapaVotada().equals(0)) {
						try {
							AlunoDAO adao = fabrica.getAlunoDAO();
							Aluno alunoEleitor = adao.getAluno(Sessao.getMatricula());
							ChapaDAO cdao = fabrica.getChapaDAO();
							Chapa chapaVotada = cdao
									.getChapaPorNome(nomesChapas.get(cbChapas.getSelectionModel().getSelectedIndex()));
							alunoEleitor.setIdChapaVotada(chapaVotada.getId());
							adao.update(alunoEleitor.getMatricula(), alunoEleitor);
							Integer numVotosDaChapa = chapaVotada.getNumVotos();
							if (numVotosDaChapa.equals(null))
								numVotosDaChapa = 0;
							chapaVotada.setNumVotos(numVotosDaChapa + 1);
							cdao.update(chapaVotada.getId(), chapaVotada);
							Sessao.setIdChapaVotada(chapaVotada.getId());
							String msg = "Voto computado com sucesso para a chapa '" + chapaVotada.getNome() + "'!";
							JOptionPane.showMessageDialog(null, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						ChapaDAO cdao = fabrica.getChapaDAO();
						Chapa chapaVotada = cdao.getChapaPorId(Sessao.getIdChapaVotada());
						String msg = "Você já fez seu voto para a chapa '" + chapaVotada.getNome() + "'.";
						JOptionPane.showMessageDialog(null, msg, "Erro - Voto Já Feito", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Você deve selecionar alguma das chapas.",
							"Erro - Nenhuma Chapa Escolhida", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

}
