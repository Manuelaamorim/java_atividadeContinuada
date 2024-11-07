package br.com.cesarschool.poo.titulos.entidades;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

public class EntidadeOperadora extends Entidade {
    private final long identificador;
    private String nome;
    private boolean autorizadoAcao;
    private double saldoAcao;
    private double saldoTituloDivida;

    public EntidadeOperadora(long identificador, String nome, boolean autorizadoAcao) {
        this.identificador = identificador;
        this.nome = nome;
        this.autorizadoAcao = autorizadoAcao;
    }

    public long getIdentificador(){
        return identificador;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public boolean getAutorizadoAcao(){
        return autorizadoAcao;
    }
    public void setAutorizadoAcao(boolean autorizadoAcao){
        this.autorizadoAcao = autorizadoAcao;
    }
    public double getSaldoAcao(){
        return saldoAcao;
    }
    public double getSaldoTituloDivida(){
        return saldoTituloDivida;
    }
    public void creditarSaldoAcao(double valor){
        this.saldoAcao += valor;
    }
    public void debitarSaldoAcao(double valor){
        if (this.saldoAcao >= valor){
            this.saldoAcao -= valor;
        }
        else{
            System.out.println("Valor insuficiente");
        }
    }
    public void creditarSaldoTituloDivida(double valor){
        this.saldoTituloDivida += valor;
    }
    public void debitarSaldoTituloDivida(double valor){
        if (this.saldoTituloDivida >= valor){
            this.saldoTituloDivida -= valor;
        }
        else{
            System.out.println("Valor insuficiente");
        }

    }

    @Override
    public Object getIdUnico() {
        return null;
    }
}
