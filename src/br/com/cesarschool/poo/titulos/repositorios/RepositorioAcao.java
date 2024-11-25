package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

import java.time.LocalDateTime;

public class RepositorioAcao extends RepositorioGeral<Acao> {

	public RepositorioAcao() {
		super(Acao.class);
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Acao.class;
	}

	public boolean incluir(Acao acao) {
		// Validar se o identificador já existe antes de incluir
		if (buscar(acao.getIdentificador()) != null) {
			return false; // Não incluir se já existir
		}
		acao.setDataHoraInclusao(LocalDateTime.now());
		return super.incluir(acao);
	}

	public boolean alterar(Acao acao) {
		// Validar se o identificador existe antes de tentar alterar
		if (buscar(acao.getIdentificador()) == null) {
			return false; // Não alterar se não existir
		}
		acao.setDataHoraUltimaAlteracao(LocalDateTime.now());
		return super.alterar(acao);
	}

	public boolean excluir(int identificador) {
		// Converter o identificador em String, como esperado pelo DAO
		return super.excluir(String.valueOf(identificador));
	}

	public Acao buscar(int identificador) {
		// Converter o identificador em String, como esperado pelo DAO
		return super.buscar(String.valueOf(identificador));
	}
}
