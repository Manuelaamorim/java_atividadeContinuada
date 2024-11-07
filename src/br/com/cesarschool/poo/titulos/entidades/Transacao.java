package br.com.cesarschool.poo.titulos.entidades;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

import java.time.LocalDateTime;

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
    public Acao getAcao(){
        return acao;
    }
    public TituloDivida getTituloDivida(){
        return tituloDivida;
    }
    public double getValorOperacao(){
        return valorOperacao;
    }
    public LocalDateTime getDataHoraOperacao(){
        return dataHoraOperacao;
    }

    @Override
    public Object getIdUnico() {
        return null;
    }
}
