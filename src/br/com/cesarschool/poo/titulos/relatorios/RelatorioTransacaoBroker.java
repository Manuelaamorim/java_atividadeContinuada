package br.com.cesarschool.poo.titulos.relatorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Comparavel;
import br.com.cesarschool.poo.titulos.utils.Ordenador;

import java.util.Arrays;
import java.util.Comparator;

public class RelatorioTransacaoBroker {
    private final RepositorioTransacao repositorio;

    public RelatorioTransacaoBroker() {
        this.repositorio = new RepositorioTransacao();
    }

    public Transacao[] relatorioTransacaoPorDataHora() {
        RepositorioTransacao repositorio = new RepositorioTransacao();
        Transacao[] transacoes = repositorio.buscarTodas();

        // Ordenar por dataHoraOperacao
        Arrays.sort(transacoes, Comparator.comparing(Transacao::getDataHoraOperacao).reversed());
        return transacoes;
    }

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        RepositorioTransacao repositorio = new RepositorioTransacao();
        Transacao[] transacoes = repositorio.buscarTodas();

        // Ordenar por nome da entidade credora
        Arrays.sort(transacoes, Comparator.comparing(t -> t.getEntidadeCredito().getNome()));
        return transacoes;
    }
}