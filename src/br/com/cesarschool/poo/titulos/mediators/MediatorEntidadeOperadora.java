package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;


public class MediatorEntidadeOperadora {
    private RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();
    private static MediatorEntidadeOperadora instance;

    private MediatorEntidadeOperadora() {

    }
    public static MediatorEntidadeOperadora getInstance() {
        if (instance == null) {
            instance = new MediatorEntidadeOperadora();
        }
        return instance;
    }

    private String validar(EntidadeOperadora entidade) {
        boolean ret = false;

        if (entidade.getIdentificador() <= 0 || entidade.getIdentificador() >= 100000){
            return "O identificador deve ser entre 1 e 99999";
        }
        if (entidade.getNome() == null ||entidade.getNome().trim().isEmpty()){
            return "Nome deve ser preenchido";
        }
        if (entidade.getNome().length() < 5 || entidade.getNome().length() > 60){
            return "Nome deve ter entre 5 e 60 caracteres.";
        }
        return null;
    }
    public String incluir(EntidadeOperadora entidade) {
        boolean incluir = false;

        if (validar(entidade) == null){
            incluir = repositorioEntidadeOperadora.incluir(entidade);
            if (incluir){
                return null;
            }
            else{
                return "Entidade já existente";
            }
        }
        else{
            return validar(entidade);
        }
    }
    public String alterar(EntidadeOperadora entidade) {
        boolean alterar = false;

        if (validar(entidade) == null){
            alterar = repositorioEntidadeOperadora.alterar(entidade);
            if (alterar){
                return null;
            }
            else{
                return "Entidade inexistente";
            }
        }
        else{
            return alterar(entidade);
        }
    }
    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Entidade inexistente";
        }
        boolean excluir = repositorioEntidadeOperadora.excluir(identificador);
        if (excluir){
            return null;
        }
        else{
            return "Entidade inexistente";
        }
    }
    public EntidadeOperadora buscar(int identificador){
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }



}
