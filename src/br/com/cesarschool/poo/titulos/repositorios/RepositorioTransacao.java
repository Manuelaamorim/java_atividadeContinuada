package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioTransacao extends RepositorioGeral<Transacao>{
	public RepositorioTransacao() {
		super(Transacao.class);
	}

	public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
		return buscarPorEntidade(identificadorEntidadeCredito, true);
	}

	public Transacao[] buscarPorEntidadeDevedora(long identificadorEntidadeDebito) {
		return buscarPorEntidade(identificadorEntidadeDebito, false);
	}

	private Transacao[] buscarPorEntidade(long identificadorEntidade, boolean isCredora) {
		List<Transacao> transacoesFiltradas = new ArrayList<>();
		Entidade[] todasEntidades = getDao().buscarTodos();
		System.out.println("Verificando transações carregadas...");

		for (Entidade entidade : todasEntidades) {
			if (entidade instanceof Transacao) {
				Transacao transacao = (Transacao) entidade;
				System.out.println("Transação carregada: " + transacao.getIdUnico());

				if (isCredora && transacao.getEntidadeCredito().getIdentificador() == identificadorEntidade) {
					transacoesFiltradas.add(transacao);
				} else if (!isCredora && transacao.getEntidadeDebito().getIdentificador() == identificadorEntidade) {
					transacoesFiltradas.add(transacao);
				}
			}
		}

		transacoesFiltradas.sort(Comparator.comparing(Transacao::getIdUnico));
		System.out.println("Filtrando transações para identificador: " + identificadorEntidade);
		for (Transacao transacao : transacoesFiltradas) {
			System.out.println("Transação correspondente: " + transacao.getIdUnico());
		}

		return transacoesFiltradas.toArray(new Transacao[0]);
	}

	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}
}
