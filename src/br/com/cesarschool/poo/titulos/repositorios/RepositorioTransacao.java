package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioTransacao extends RepositorioGeral<Transacao> {

	public RepositorioTransacao() {
		super(Transacao.class);
	}

	public boolean incluir(Transacao transacao) {
		if (buscar(transacao.getIdUnico()) != null) {
			System.out.println("Inclusão falhou: identificador duplicado.");
			return false;
		}
		transacao.setDataHoraInclusao(LocalDateTime.now());
		return super.incluir(transacao);
	}

	public boolean alterar(Transacao transacao) {
		if (buscar(transacao.getIdUnico()) == null) {
			System.out.println("Alteração falhou: identificador não encontrado.");
			return false;
		}
		transacao.setDataHoraUltimaAlteracao(LocalDateTime.now());
		return super.alterar(transacao);
	}

	@Override
	public boolean excluir(String identificador) {
		if (buscar(identificador) == null) {
			System.out.println("Exclusão falhou: identificador não encontrado.");
			return false;
		}
		return super.excluir(identificador);
	}

	public Transacao buscar(String identificador) {
		return super.buscar(identificador);
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

		// Ordena as transações por ID único
		transacoesFiltradas.sort(Comparator.comparing(Transacao::getIdUnico));
		System.out.println("Filtrando transações para identificador: " + identificadorEntidade);

		return transacoesFiltradas.toArray(new Transacao[0]);
	}

	public Transacao[] buscarTodas() {
		Entidade[] todasEntidades = getDao().buscarTodos();
		List<Transacao> transacoes = new ArrayList<>();

		for (Entidade entidade : todasEntidades) {
			if (entidade instanceof Transacao) {
				transacoes.add((Transacao) entidade);
			}
		}

		return transacoes.toArray(new Transacao[0]);
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}
}
