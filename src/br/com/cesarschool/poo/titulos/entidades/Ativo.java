package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

public class Ativo {
    private final int identificador;
    private String nome;
    private LocalDate dataValidade;

    public Ativo(int identificador, String nome, LocalDate dataValidade){
        this.identificador = identificador;
        this.nome = nome;
        this.dataValidade = dataValidade;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    public void setDataValidade(LocalDate dataValidade){
        this.dataValidade = dataValidade;
    }
    public LocalDate getDataValidade(){
        return dataValidade;
    }
    public int getIdentificador(){
        return identificador;
    }
}

