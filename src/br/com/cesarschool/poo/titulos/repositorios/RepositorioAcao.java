package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RepositorioAcao extends RepositorioGeral<Acao>{
	public RepositorioAcao() {
		super(Acao.class);
	}

	public Class<?> getClasseEntidade() {
		return Acao.class;
	}

	public boolean incluir(Acao acao) {
		acao.setDataHoraInclusao(LocalDateTime.now());
		return super.incluir(acao);
	}

	public boolean alterar(Acao acao) {
		acao.setDataHoraUltimaAlteracao(LocalDateTime.now());
		return super.alterar(acao);
	}
	public boolean excluir(int identificador) {
		return super.excluir(String.valueOf(identificador));
	}
	public Acao buscar(int identificador) {
		return super.buscar(String.valueOf(identificador));
	}
}
