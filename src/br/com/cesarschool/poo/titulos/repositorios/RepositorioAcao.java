package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import java.io.*;
import java.time.LocalDate;

public class RepositorioAcao {
	private static final String FILE_NAME = "Acao.txt";

	public boolean incluir(Acao acao) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
			String linha;

			while((linha = reader.readLine()) != null){
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);
				if (id == acao.getIdentificador()){
					System.out.println("Inclusão falhou: identificador duplicado.");
					return false;
				}
			}
		} catch (FileNotFoundException e){
			System.out.println("Arquivo não encontrado. Será criado um novo arquivo.");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))){
			String linhaGravar = acao.getIdentificador() + ";" +
					acao.getNome() + ";" +
					acao.getDataValidade() + ";" +
					acao.getValorUnitario();

			writer.write(linhaGravar);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Inclusão realizada com sucesso.");
		return true;
	}

	public boolean alterar(Acao acao) {
		File arquivo = new File(FILE_NAME);
		File arquivoTemp = new File("Acao_temp.txt");

		boolean achou = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
			BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
			String linha;

			while((linha = reader.readLine()) != null){
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == acao.getIdentificador()){
					String novaLinha = acao.getIdentificador() + ";" +
										acao.getNome() + ";" +
										acao.getDataValidade() + ";" +
										acao.getValorUnitario();
					writer.write(novaLinha);
					writer.newLine();
					achou = true;
				}
				else{
					writer.write(linha);
					writer.newLine();
				}
			}

		}	catch (IOException e){
			e.printStackTrace();
			return false;
		}

		if (!arquivo.delete() || !arquivoTemp.renameTo(arquivo)){
			System.out.println("Erro ao substituir o arquivo.");
			return false;
		}
		if(achou){
			System.out.println("Arquivo alterado com sucesso.");
		}
		else{
			System.out.println("Alteração falhou: identificador não encontrado.");
		}
		return achou;
	}

	public boolean excluir(int identificador) {
		File arquivo = new File(FILE_NAME);
		File arquivoTemp = new File("Acao_temp.txt");

		boolean achou = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == identificador) {
					achou = true;

				}
				else {
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
	public Acao buscar(int identificador) {

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == identificador) {
					System.out.println("Busca realizada com sucesso: Ação encontrada.");
					return new Acao(
							Integer.parseInt(campos[0]),
							campos[1],
							LocalDate.parse(campos[2]),
							Double.parseDouble(campos[3])
					);
				}
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Busca falhou: identificador não encontrado.");
		return null;
	}
}
