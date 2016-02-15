package br.uece.eleicoes;

public class Sessao {
	private static Long matricula = null;
	private static Integer idChapaVotada = null;

	public static void abrirSessao(Long matricula, Integer idChapaVotada) {
		if (Sessao.matricula == null) {
			Sessao.matricula = matricula;
			Sessao.idChapaVotada = idChapaVotada;
		} else {
			System.out.println("Sessão já aberta para aluno da matrícula: " + Sessao.matricula);
		}
	}
	
	public static void destruirSessao() {
		Sessao.matricula = null;
		Sessao.idChapaVotada = null;

	}
	
	public static Integer getIdChapaVotada() {
		return idChapaVotada;
	}
	
	public static void setIdChapaVotada(Integer idChapaVotada) {
		Sessao.idChapaVotada = idChapaVotada;
	}
	
	public static Long getMatricula() {
		return matricula;
	}
	
	public static void setMatricula(Long matricula) {
		Sessao.matricula = matricula;
	}
}
