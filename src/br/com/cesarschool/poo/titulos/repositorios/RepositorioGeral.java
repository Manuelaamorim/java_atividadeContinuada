package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.daogenerico.DAOSerializadorObjetos;
import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioGeral<T extends Entidade> {
    private final DAOSerializadorObjetos dao;

    public RepositorioGeral(Class<?> classeEntidade) {
        this.dao = new DAOSerializadorObjetos(classeEntidade);
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(T entidade) {
        if (buscar(entidade.getIdUnico()) != null) {
            System.out.println("Inclusão falhou: identificador duplicado.");
            return false;
        }
        return dao.incluir(entidade);
    }

    public boolean alterar(T entidade) {
        if (buscar(entidade.getIdUnico()) == null) {
            System.out.println("Alteração falhou: identificador não encontrado.");
            return false;
        }
        return dao.alterar(entidade);
    }

    public boolean excluir(String idUnico) {
        if (buscar(idUnico) == null) {
            System.out.println("Exclusão falhou: identificador não encontrado.");
            return false;
        }
        return dao.excluir(idUnico);
    }

    public T buscar(String idUnico) {
        Entidade entidade = dao.buscar(idUnico);
        return entidade != null ? (T) entidade : null;
    }

    public List<T> listarTodos() {
        Entidade[] entidades = dao.buscarTodos();
        List<T> lista = new ArrayList<>();
        for (Entidade entidade : entidades) {
            lista.add((T) entidade);
        }
        return lista;
    }
    public abstract Class<?> getClasseEntidade();
}


