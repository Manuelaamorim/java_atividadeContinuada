package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MediatorOperacao {
    private MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    private MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
    private MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
    private RepositorioTransacao repositorioTransacao = new RepositorioTransacao();
    private static MediatorOperacao instance;

    private MediatorOperacao() {

    }

    public static MediatorOperacao getInstance() {
        if (instance == null) {
            instance = new MediatorOperacao();

        }
        return instance;
    }
    public String realizarOperacao(boolean ehAcao, int idEntidadeCredito,
                        int idEntidadeDebito, int idAcaoOuTitulo, double valor){
        if (valor <= 0){
            return "Valor invalido";
        }
        EntidadeOperadora entidadeCredito = mediatorEntidadeOperadora.buscar(idEntidadeCredito);
        if (entidadeCredito == null){
            return "Entidade crédito inexistente";
        }
        EntidadeOperadora entidadeDebito = mediatorEntidadeOperadora.buscar(idEntidadeDebito);
        if (entidadeDebito == null){
            return "Entidade débito inexistente";
        }
        if (ehAcao){
            if (!entidadeCredito.getAutorizadoAcao()){
                return "Entidade de crédito não autorizada para ação";
            }
            if (!entidadeDebito.getAutorizadoAcao()){
                return "Entidade de débito não autorizada para ação";
            }
            Acao acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação não encontrada";
            }
            if (entidadeDebito.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente para a operação de ação";
            }
            if (acao.getValorUnitario() > valor) {
                return "Valor da operação menor que o valor unitário da ação";
            }

            entidadeCredito.creditarSaldoAcao(valor);
            entidadeDebito.creditarSaldoAcao(valor);
        }

        else {
            TituloDivida tituloDivida = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (tituloDivida == null) {
                return "Título de dívida não encontrado";
            }
            if (entidadeDebito.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente para a operação de título de divida";
            }
            double valorOperacao = tituloDivida.calcularPrecoTransacao(valor);

            entidadeCredito.creditarSaldoTituloDivida(valorOperacao);
            entidadeDebito.creditarSaldoTituloDivida(valorOperacao);
        }

        String retornoCredito = mediatorEntidadeOperadora.alterar(entidadeCredito);
        if (retornoCredito != null){
            return retornoCredito;
        }
        String retornoDebito = mediatorEntidadeOperadora.alterar(entidadeDebito);
        if (retornoDebito != null){
            return retornoDebito;
        }

        Acao objAcao = null;
        TituloDivida objTituloDivida = null;
        double valorOperacao;

        if (ehAcao){
            objAcao = mediatorAcao.buscar(idAcaoOuTitulo);
            valorOperacao = valor;
        }
        else{
           objTituloDivida = mediatorTituloDivida.buscar(idAcaoOuTitulo);
           valorOperacao = objTituloDivida.calcularPrecoTransacao(valor);
        }


        Transacao transacao = new Transacao(
                entidadeCredito,
                entidadeDebito,
                objAcao,
                objTituloDivida,
                valorOperacao,
                LocalDateTime.now()
        );

        repositorioTransacao.incluir(transacao);
        return "Operação realizada com sucesso!";
    }

    public Transacao[] gerarExtrato(int entidade) {
        Transacao[] transacoesCredoras = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao[] transacoesDevedoras = repositorioTransacao.buscarPorEntidadeDevedora(entidade);

        if(transacoesCredoras == null){
            transacoesCredoras = new Transacao[0];
        }

        if(transacoesDevedoras == null){
            transacoesDevedoras = new Transacao[0];
        }

        Transacao[] todasTransacoes = new Transacao[transacoesCredoras.length + transacoesDevedoras.length];
        System.arraycopy(transacoesCredoras, 0, todasTransacoes, 0, transacoesCredoras.length);
        System.arraycopy(transacoesDevedoras, 0, todasTransacoes, transacoesCredoras.length, transacoesDevedoras.length);

        Arrays.sort(todasTransacoes, (t1, t2) -> t2.getDataHoraOperacao().compareTo(t1.getDataHoraOperacao()));
        return todasTransacoes;
    }
}
