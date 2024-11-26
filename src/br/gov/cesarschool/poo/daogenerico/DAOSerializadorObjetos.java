package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {
    private String nomeDiretorio;


    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = "." + File.separator + tipoEntidade.getSimpleName();
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new RuntimeException("Não foi possível criar o diretório: " + nomeDiretorio);
        }
    }

    private String sanitizeFileName(Object idUnico) {
        return idUnico.toString().replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    public boolean incluir(Entidade entidade) {
        File arquivo = new File(nomeDiretorio + File.separator + sanitizeFileName(entidade.getIdUnico()));

        if (arquivo.exists()) {
            return false;
        }
        entidade.setDataHoraInclusao(LocalDateTime.now());
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
        entidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean excluir(String idUnico) {
        File arquivo = new File(nomeDiretorio + File.separator + sanitizeFileName(idUnico));
        return arquivo.exists() && arquivo.delete();
    }

    public Entidade buscar(String idUnico) {
        File arquivo = new File(nomeDiretorio + File.separator + sanitizeFileName(idUnico));
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
            System.out.println("Arquivos encontrados no diretório " + nomeDiretorio + ":");
            for (File arquivo : arquivos) {
                System.out.println(" - " + arquivo.getName());
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    Entidade entidade = (Entidade) ois.readObject();
                    entidades.add(entidade);
                    System.out.println("Entidade carregada: " + entidade.getIdUnico());
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Erro ao processar arquivo: " + arquivo.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado no diretório: " + nomeDiretorio);
        }

        return entidades.toArray(new Entidade[0]);
    }

}
