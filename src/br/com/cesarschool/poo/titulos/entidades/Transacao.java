package br.com.cesarschool.poo.titulos.entidades;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transacao extends Entidade {

    private final EntidadeOperadora entidadeCredito;
    private final EntidadeOperadora entidadeDebito;
    private final Acao acao;
    private final TituloDivida tituloDivida;
    private final double valorOperacao;
    private final LocalDateTime dataHoraOperacao;

    public Transacao(EntidadeOperadora entidadeCredito, EntidadeOperadora entidadeDebito, Acao acao,
                     TituloDivida tituloDivida, double valorOperacao, LocalDateTime dataHoraOperacao) {
        this.entidadeCredito = entidadeCredito;
        this.entidadeDebito = entidadeDebito;
        this.acao = acao;
        this.tituloDivida = tituloDivida;
        this.valorOperacao = valorOperacao;
        this.dataHoraOperacao = dataHoraOperacao;
    }

    public EntidadeOperadora getEntidadeCredito() {
        return entidadeCredito;
    }

    public EntidadeOperadora getEntidadeDebito() {
        return entidadeDebito;
    }

    public Acao getAcao() {
        return acao;
    }

    public TituloDivida getTituloDivida() {
        return tituloDivida;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }

    @Override
    public String getIdUnico() {
        return entidadeCredito.getIdentificador() + "-" +
                entidadeDebito.getIdentificador() + "-" +
                dataHoraOperacao.toString().replaceAll("[:.]", "-");
    }

    // Sobrescrita do método equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Mesma referência
        if (obj == null || getClass() != obj.getClass()) return false; // Classes diferentes
        Transacao transacao = (Transacao) obj; // Casting seguro
        return Objects.equals(getIdUnico(), transacao.getIdUnico()); // Comparação baseada no ID único
    }

    // Sobrescrita do método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(getIdUnico()); // Gera um hash baseado no ID único
    }
}
