package br.uece.eleicoes;

import java.io.IOException;
import java.net.URL;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroChapa extends Application implements Initializable {

	private DAOFactory fabrica = DAOFactory.getFabrica();

	private static Stage stage;

	@FXML
	private AnchorPane pane;

	@FXML
	private TextField txNomeChapa;
	@FXML
	private TextField txMatPresidente;
	@FXML
	private TextField txMatSecretario;
	@FXML
	private TextField txMatTesoureiro;

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
	private Button btCancelar;
	@FXML
	private Button btCadastrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		btCancelar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new MenuPrincipal().start(new Stage());
					CadastroChapa.getStage().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		txMatPresidente.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	txMatPresidente.setText(oldValue);
		        	txMatPresidente.positionCaret(txMatPresidente.getLength());
		        }
		    }

		});
		txMatPresidente.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {

					String matPres = txMatPresidente.getText();
					String matSec = txMatSecretario.getText();
					String matTes = txMatTesoureiro.getText();

					if (matPres.equals("")) {
						lbNomePresidente.setText("-");
						lbCursoPresidente.setText("-");
						lbMatriculaPresidente.setText("-");
					}

					if (!(matPres.equals(matSec) || matPres.equals(matTes) || matPres.equals(""))) {
						AlunoDAO dao = fabrica.getAlunoDAO();
						try {
							Aluno aluno = dao.getAluno(Long.parseLong(matPres));
							if (aluno == null) {
								JOptionPane.showMessageDialog(null, "Matrícula inserida não cadastrada.",
										"Erro - Presidente", JOptionPane.ERROR_MESSAGE);
								txMatPresidente.setText("");
								lbNomePresidente.setText("-");
								lbCursoPresidente.setText("-");
								lbMatriculaPresidente.setText("-");
							} else {
								lbNomePresidente.setText(aluno.getNome());
								lbCursoPresidente.setText(aluno.getCurso());
								lbMatriculaPresidente.setText(aluno.getMatricula().toString());
							}
						} catch (Exception e) {
						}
					} else {
						if (!matPres.equals("")) {
							JOptionPane.showMessageDialog(null, "Matrícula já inserida em outro cargo.",
									"Erro - Presidente", JOptionPane.ERROR_MESSAGE);
							txMatPresidente.setText("");
						}
					}
				}
			}
		});

		txMatSecretario.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	txMatSecretario.setText(oldValue);
		        	txMatSecretario.positionCaret(txMatSecretario.getLength());
		        }
		    }

		});
		txMatSecretario.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					String matPres = txMatPresidente.getText();
					String matSec = txMatSecretario.getText();
					String matTes = txMatTesoureiro.getText();

					if (matSec.equals("")) {
						lbNomeSecretario.setText("-");
						lbCursoSecretario.setText("-");
						lbMatriculaSecretario.setText("-");
					}

					if (!(matSec.equals(matPres) || matSec.equals(matTes) || matSec.equals(""))) {
						AlunoDAO dao = fabrica.getAlunoDAO();
						try {
							Aluno aluno = dao.getAluno(Long.parseLong(matSec));
							if (aluno == null) {
								JOptionPane.showMessageDialog(null, "Matrícula inserida não cadastrada.",
										"Erro - Secretário", JOptionPane.ERROR_MESSAGE);
								txMatSecretario.setText("");
								lbNomeSecretario.setText("-");
								lbCursoSecretario.setText("-");
								lbMatriculaSecretario.setText("-");
							} else {
								lbNomeSecretario.setText(aluno.getNome());
								lbCursoSecretario.setText(aluno.getCurso());
								lbMatriculaSecretario.setText(aluno.getMatricula().toString());
							}
						} catch (Exception e) {
						}
					} else {
						if (!matSec.equals("")) {
							JOptionPane.showMessageDialog(null, "Matrícula já inserida em outro cargo.",
									"Erro - Secretario", JOptionPane.ERROR_MESSAGE);
							txMatSecretario.setText("");
						}
					}
				}
			}
		});

		txMatTesoureiro.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	txMatTesoureiro.setText(oldValue);
		        	txMatTesoureiro.positionCaret(txMatTesoureiro.getLength());
		        }
		    }

		});
		txMatTesoureiro.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					String matPres = txMatPresidente.getText();
					String matSec = txMatSecretario.getText();
					String matTes = txMatTesoureiro.getText();

					if (matTes.equals("")) {
						lbNomeTesoureiro.setText("-");
						lbCursoTesoureiro.setText("-");
						lbMatriculaTesoureiro.setText("-");
					}

					if (!(matTes.equals(matPres) || matTes.equals(matSec) || matTes.equals(""))) {
						AlunoDAO dao = fabrica.getAlunoDAO();
						try {
							Aluno aluno = dao.getAluno(Long.parseLong(matTes));
							if (aluno == null) {
								JOptionPane.showMessageDialog(null, "Matrícula inserida não cadastrada.",
										"Erro - Tesoureiro", JOptionPane.ERROR_MESSAGE);
								txMatTesoureiro.setText("");
								lbNomeTesoureiro.setText("-");
								lbCursoTesoureiro.setText("-");
								lbMatriculaTesoureiro.setText("-");
							} else {
								lbNomeTesoureiro.setText(aluno.getNome());
								lbCursoTesoureiro.setText(aluno.getCurso());
								lbMatriculaTesoureiro.setText(aluno.getMatricula().toString());
							}
						} catch (Exception e) {
						}
					} else {
						if (!matTes.equals("")) {
							JOptionPane.showMessageDialog(null, "Matrícula já inserida em outro cargo.",
									"Erro - Tesoureiro", JOptionPane.ERROR_MESSAGE);
							txMatTesoureiro.setText("");
						}
					}
				}
			}
		});

		btCadastrar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (txNomeChapa.getText().equals("") || txMatPresidente.getText().equals("")
						|| txMatSecretario.getText().equals("") || txMatTesoureiro.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você deve preencher todos os campos.",
							"Erro - Campos Obrigatórios", JOptionPane.ERROR_MESSAGE);
				} else {
					Long matPresidente = Long.parseLong(txMatPresidente.getText());
					Long matSecretario = Long.parseLong(txMatSecretario.getText());
					Long matTesoureiro = Long.parseLong(txMatTesoureiro.getText());

					ChapaDAO dao = fabrica.getChapaDAO();
					Chapa chapa = new Chapa();
					chapa.setNome(txNomeChapa.getText());
					chapa.setMatPresidente(matPresidente);
					chapa.setMatSecretario(matSecretario);
					chapa.setMatTesoureiro(matTesoureiro);

					Boolean matPresUtilizada = dao.pesquisaChapa(matPresidente);
					Boolean matSecUtilizada = dao.pesquisaChapa(matSecretario);
					Boolean matTesUtilizada = dao.pesquisaChapa(matTesoureiro);
					Boolean nomeUtilizado = dao.pesquisaChapa(txNomeChapa.getText());

					String msgErro = "";
					Integer contErros = 0;
					if (matPresUtilizada) {
						contErros++;
						msgErro += contErros + ") Matrícula '" + matPresidente + "' cadastrada em outra chapa.\n";
					}
					if (matSecUtilizada) {
						contErros++;
						msgErro += contErros + ") Matrícula '" + matSecretario + "' cadastrada em outra chapa.\n";
					}
					if (matTesUtilizada) {
						contErros++;
						msgErro += contErros + ") Matrícula '" + matTesoureiro + "' cadastrada em outra chapa.\n";
					}
					if (nomeUtilizado) {
						contErros++;
						msgErro += contErros + ") Nome '" + txNomeChapa.getText() + "' cadastrado em outra chapa.\n";
					}

					if (contErros > 0) {
						JOptionPane.showMessageDialog(null, msgErro, "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);

					} else {
						try {
							dao.adiciona(chapa);
							JOptionPane.showMessageDialog(null, "Chapa cadastrada com sucesso!", "Sucesso",
									JOptionPane.INFORMATION_MESSAGE);
							new MenuPrincipal().start(new Stage());
							CadastroChapa.getStage().close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		});
	}

	protected static Stage getStage() {
		return stage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		CadastroChapa.stage = stage;
		CadastroChapa.stage.setTitle("Cadastro de Chapa");
		CadastroChapa.stage.setResizable(false);
		showCadastroChapaScreen();

	}

	private void showCadastroChapaScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MenuPrincipal.class.getResource("view/cadastroChapa.fxml"));
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

}
