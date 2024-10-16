package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

import java.time.LocalDate;

public class MediatorAcao {
    private static MediatorAcao instanciaUnica = new MediatorAcao();
    private static RepositorioAcao repositorioAcao = new RepositorioAcao();

    private MediatorAcao() {
    }

    public static MediatorAcao getInstanciaUnica() {
        return instanciaUnica;
    }

    private String validar(Acao acao){
        boolean ret = false;

        if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000){
           return "O identificador deve ser entre 0 e 99999";
        }
        if (acao.getNome() == null ||acao.getNome().trim().isEmpty()){
            return "Nome deve ser preenchido";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100){
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (acao.getDataValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ser pelo menos 30 dias a partir de hoje.";
        }
        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }
        return null;
    }

    public String incluir(Acao acao){
        boolean incluir = false;

        if (validar(acao) == null){
            incluir = repositorioAcao.incluir(acao);
            if (incluir){
                return null;
            }
            else{
                return "Ação já existente";
            }
        }
        else{
            return validar(acao);
        }
    }

    public String alterar(Acao acao){
        boolean alterar = false;

        if (validar(acao) == null){
            alterar = repositorioAcao.alterar(acao);
            if (alterar){
                return null;
            }
            else{
                return "Ação inexistente";
            }
        }
        else{
            return alterar(acao);
        }
    }

    public String excluir(int identificador){
        if (identificador <= 0 || identificador >= 100000) {
            return "Ação inexistente";
        }
        boolean excluir = repositorioAcao.excluir(identificador);
        if (excluir){
            return null;
        }
        else{
            return "Ação inexistente";
        }
    }

    public Acao buscar(int identificador){
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }
}
