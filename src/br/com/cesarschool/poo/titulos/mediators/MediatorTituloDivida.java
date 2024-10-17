package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

import java.time.LocalDate;

public class MediatorTituloDivida {
    private RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();
    private static MediatorTituloDivida instance;

    private MediatorTituloDivida() {

    }
    public static MediatorTituloDivida getInstance() {
        if (instance == null) {
            instance = new MediatorTituloDivida();
        }
        return instance;
    }
    private String validar(TituloDivida titulo){
        boolean ret = false;

        if (titulo.getIdentificador() <= 0 || titulo.getIdentificador() >= 100000){
            return "O identificador deve ser entre 1 e 99999";
        }
        if (titulo.getNome() == null ||titulo.getNome().trim().isEmpty()){
            return "Nome deve ser preenchido";
        }
        if (titulo.getNome().length() < 10 || titulo.getNome().length() > 100){
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (titulo.getDataValidade().isBefore(LocalDate.now().plusDays(180))) {
            return "Data de validade deve ser pelo menos 180 dias a partir de hoje.";
        }
        if (titulo.getTaxaJuros() < 0) {
            return "Taxa de juros deve ser maior ou igual zero.";
        }
        return null;
    }

    public String incluir(TituloDivida titulo){
        boolean incluir = false;

        if (validar(titulo) == null){
            incluir = repositorioTituloDivida.incluir(titulo);
            if (incluir){
                return null;
            }
            else{
                return "Título já existente";
            }
        }
        else{
            return validar(titulo);
        }
    }
    public String alterar(TituloDivida titulo){
        boolean alterar = false;
        if (validar(titulo) == null){
            alterar = repositorioTituloDivida.alterar(titulo);
            if (alterar){
                return null;
            }
            else{
                return "Título inexistente";
            }
        }
        else{
            return alterar(titulo);
        }
    }
    public String excluir(int identificador){
        if (identificador <= 0 || identificador >= 100000) {
            return "Título inexistente";
        }
        boolean excluir = repositorioTituloDivida.excluir(identificador);
        if (excluir){
            return null;
        }
        else{
            return "Título inexistente";
        }
    }

    public TituloDivida buscar(int identificador){
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioTituloDivida.buscar(identificador);
    }

}
