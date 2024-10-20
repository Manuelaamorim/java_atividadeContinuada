package br.com.cesarschool.poo.titulos.repositorios;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.time.LocalDate;

public class RepositorioEntidadeOperadora {
    private static final String FILE_NAME = "EntidadeOperadora.txt";

    public boolean incluir(EntidadeOperadora entidade) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                long id = Long.parseLong(campos[0]);
                if (id == entidade.getIdentificador()) {
                    System.out.println("Inclusão falhou: identificador duplicado.");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Será criado um novo arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String linhaGravar = entidade.getIdentificador() + ";" +
                    entidade.getNome() + ";" +
                    entidade.getAutorizadoAcao() + ";" +
                    entidade.getSaldoAcao() + ";" +
                    entidade.getSaldoTituloDivida();

            writer.write(linhaGravar);
            writer.newLine();
            System.out.println("Inclusão realizada com sucesso.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Falha ao acessar o arquivo.");
            return false;
        }
    }

    public boolean alterar(EntidadeOperadora entidade) {
        File arquivo = new File(FILE_NAME);
        File arquivoTemp = new File("EntidadeOperadora_temp.txt");

        boolean achou = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                long id = Long.parseLong(campos[0]);

                if (id == entidade.getIdentificador()) {
                    String novaLinha = entidade.getIdentificador() + ";" +
                            entidade.getNome() + ";" +
                            entidade.getAutorizadoAcao() + ";" +
                            entidade.getSaldoAcao() + ";" +
                            entidade.getSaldoTituloDivida();
                    writer.write(novaLinha);
                    writer.newLine();
                    achou = true;
                } else {
                    writer.write(linha);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!arquivo.delete() || !arquivoTemp.renameTo(arquivo)) {
            System.out.println("Erro ao substituir o arquivo.");
            return false;
        }
        if (!achou) {
            System.out.println("Exclusão falhou: identificador não encontrado.");
        }
        else{
            System.out.println("Exclusão realizada com sucesso.");
        }
        return achou;
    }

    public boolean excluir(long identificador) {
        File arquivo = new File(FILE_NAME);
        File arquivoTemp = new File("EntidadeOperadora_temp.txt");

        boolean achou = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                long id = Long.parseLong(campos[0]);

                if (id == identificador) {
                    achou = true;
                } else {
                    writer.write(linha);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!arquivo.delete() || !arquivoTemp.renameTo(arquivo)) {
            System.out.println("Erro ao substituir o arquivo.");
            return false;
        }
        if (!achou) {
            System.out.println("Exclusão falhou: identificador não encontrado.");
        }
        else{
            System.out.println("Exclusão realizada com sucesso.");
        }
        return achou;
    }
    public EntidadeOperadora buscar(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                long id = Long.parseLong(campos[0]);

                if (id == identificador) {
                    System.out.println("Busca realizada com sucesso: Ação encontrada.");
                    return new EntidadeOperadora(
                            Long.parseLong(campos[0]),
                            campos[1],
                            Boolean.parseBoolean(campos[2])
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Busca falhou: identificador não encontrado.");
        return null;
    }


}
