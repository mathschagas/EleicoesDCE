package br.uece.eleicoes.model;

public class DAOFactory {
	
	private static DAOFactory instancia = null;
	
	private DAOFactory() {}
	
	public static DAOFactory getFabrica() {
		if(instancia == null) {
			instancia = new DAOFactory();
		}
		return instancia;
	}
	
	public AlunoDAO getAlunoDAO() {
		return new AlunoDAO();
	}
	
	public ChapaDAO getChapaDAO() {
		return new ChapaDAO();
	}
}
