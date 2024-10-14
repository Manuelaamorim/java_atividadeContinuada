package br.com.cesarschool.poo.titulos.repositorios;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.time.LocalDate;
/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas.
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclus�o deve adicionar uma nova linha ao arquivo. N�o � permitido incluir 
 * identificador repetido. Neste caso, o m�todo deve retornar false. Inclus�o com 
 * sucesso, retorno true.
 * 
 * A altera��o deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Altera��o com sucesso, retorno true.  
 *   
 * A exclus�o deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Exclus�o com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador n�o seja encontrado no arquivo, retornar null.   
 */
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
                            Double.parseDouble(campos[2])
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
