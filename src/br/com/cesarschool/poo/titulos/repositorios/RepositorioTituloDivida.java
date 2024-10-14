package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import java.io.*;
import java.time.LocalDate;

/*
 * Deve gravar em e ler de um arquivo texto chamado TituloDivida.txt os dados dos objetos do tipo
 * TituloDivida. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, taxaJuros).
 *
    1;BRASIL;2024-12-12;10.5
    2;EUA;2026-01-01;1.5
    3;FRANCA;2027-11-11;2.5 
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
public class RepositorioTituloDivida {
	private static final String FILE_NAME = "TituloDivida.txt";
	public boolean incluir(TituloDivida tituloDivida) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);
				if (id == tituloDivida.getIdentificador()) {
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
			String linhaGravar = tituloDivida.getIdentificador() + ";" +
					tituloDivida.getNome() + ";" +
					tituloDivida.getDataValidade() + ";" +
					tituloDivida.getTaxaJuros();

			writer.write(linhaGravar);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Inclusão realizada com sucesso.");
		return true;
	}
	public boolean alterar(TituloDivida tituloDivida) {

		File arquivo = new File(FILE_NAME);
		File arquivoTemp = new File("TituloDivida_temp.txt");

		boolean achou = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == tituloDivida.getIdentificador()) {
					String novaLinha = tituloDivida.getIdentificador() + ";" +
							tituloDivida.getNome() + ";" +
							tituloDivida.getDataValidade() + ";" +
							tituloDivida.getTaxaJuros();
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
			System.out.println("Alteração falhou: identificador não encontrado.");
		}
		else{
			System.out.println("Alteração realizada com sucesso.");
		}
		return achou;
	}
	public boolean excluir(int identificador) {
		File arquivo = new File(FILE_NAME);
		File arquivoTemp = new File("TituloDivida_temp.txt");

		boolean achou = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == identificador) {
					achou = true;
					System.out.println("Exclusão realizada com sucesso.");
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

		return achou;
	}
	public TituloDivida buscar(int identificador) {

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == identificador) {
					System.out.println("Busca realizada com sucesso: Título de Dívida encontrado.");
					return new TituloDivida(
							Integer.parseInt(campos[0]),
							campos[1],
							LocalDate.parse(campos[2]),
							Double.parseDouble(campos[3])
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

