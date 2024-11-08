package br.com.cesarschool.poo.titulos.daogenerico;

/*
 * Esta classe representa um DAO genérico, que inclui, exclui, altera, busca por identificador
 * único e busca todos, qualquer objeto(s) cujo tipo é subtipo de Entidade.
 *
 * Sugerimos o uso da API de serialização do JAVA, que grava e lê objetos em arquvos.
 * Lembrar sempre de fechar (em qualquer circunstância) streams JAVA abertas.
 *
 * As nuances mais detalhadas do funcionamento desta classe está especificada na classe de testes
 * automatizados br.gov.cesarschool.poo.testes.TestesDAOSerializador.
 *
 * A classe deve ter a estrutura (métodos e construtores) dada abaixo.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {
    private String nomeDiretorio;

    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = "." + File.separator + tipoEntidade.getSimpleName();
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public boolean incluir(Entidade entidade) {
        File arquivo = new File(nomeDiretorio + File.separator + entidade.getIdUnico());
        if (arquivo.exists()) {
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(Entidade entidade) {
        File arquivo = new File(nomeDiretorio + File.separator + entidade.getIdUnico());
        if (!arquivo.exists()) {
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean excluir(String idUnico) {
        File arquivo = new File(nomeDiretorio + File.separator + idUnico);
        return arquivo.exists() && arquivo.delete();
    }

    public Entidade buscar(String idUnico) {
        File arquivo = new File(nomeDiretorio + File.separator + idUnico);
        if (!arquivo.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Entidade) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entidade[] buscarTodos() {
        List<Entidade> entidades = new ArrayList<>();
        File diretorio = new File(nomeDiretorio);
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    Entidade entidade = (Entidade) ois.readObject();
                    entidades.add(entidade);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return entidades.toArray(new Entidade[0]);
    }
}
