package br.com.cesarschool.poo.titulos.repositorios;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class RepositorioEntidadeOperadora extends RepositorioGeral<EntidadeOperadora>{
    public RepositorioEntidadeOperadora() {
        super(EntidadeOperadora.class);
    }

    public boolean incluir(EntidadeOperadora entidade) {
        if (buscar(String.valueOf(entidade.getIdentificador())) != null) {
            System.out.println("Inclusão falhou: identificador duplicado.");
            return false;
        }
        return super.incluir(entidade);
    }

    public boolean alterar(EntidadeOperadora entidade) {
        if (buscar(String.valueOf(entidade.getIdentificador())) == null) {
            System.out.println("Alteração falhou: identificador não encontrado.");
            return false;
        }
        return super.alterar(entidade);
    }

    public boolean excluir(long identificador) {
        String id = String.valueOf(identificador);
        if (buscar(id) == null) {
            System.out.println("Exclusão falhou: identificador não encontrado.");
            return false;
        }
        return super.excluir(id);
    }

    public EntidadeOperadora buscar(long identificador) {
        return (EntidadeOperadora) super.buscar(String.valueOf(identificador));
    }

    public List<EntidadeOperadora> listarTodos() {
        return super.listarTodos();
    }
    public Class<?> getClasseEntidade() {
        return EntidadeOperadora.class;
    }
}
