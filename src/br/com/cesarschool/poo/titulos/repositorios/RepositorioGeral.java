package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.daogenerico.DAOSerializadorObjetos;
import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioGeral<T> {
    private final DAOSerializadorObjetos dao;

    public RepositorioGeral(Class<?> classeEntidade) {
        this.dao = new DAOSerializadorObjetos(classeEntidade);
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(Entidade entidade) {
        return dao.incluir(entidade);
    }

    public boolean alterar(Entidade entidade) {
        return dao.alterar(entidade);
    }

    public boolean excluir(String idUnico) {
        return dao.excluir(idUnico);
    }

    public T buscar(String idUnico) {
        return (T) dao.buscar(idUnico);
    }

    public List<T> listarTodos() {
        Entidade[] entidades = dao.buscarTodos();
        System.out.println("Entidades carregadas:");
        for (Entidade entidade : entidades) {
            System.out.println(entidade.getIdUnico());
        }
        List<T> lista = new ArrayList<>();
        for (Entidade entidade : entidades) {
            lista.add((T) entidade);
        }
        return lista;
    }
    public abstract Class<?> getClasseEntidade();

}
