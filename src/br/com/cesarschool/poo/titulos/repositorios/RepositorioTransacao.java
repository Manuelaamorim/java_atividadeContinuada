package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepositorioTransacao {
	private static final String FILE_NAME = "Transacao.txt";
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public void incluir(Transacao transacao) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			String linhaGravar = transacao.getEntidadeCredito().getIdentificador() + ";" +
					transacao.getEntidadeCredito().getNome() + ";" +
					transacao.getEntidadeCredito().getAutorizadoAcao() + ";" +
					transacao.getEntidadeCredito().getSaldoAcao() + ";" +
					transacao.getEntidadeCredito().getSaldoTituloDivida() + ";" +
					transacao.getEntidadeDebito().getIdentificador() + ";" +
					transacao.getEntidadeDebito().getNome() + ";" +
					transacao.getEntidadeDebito().getAutorizadoAcao() + ";" +
					transacao.getEntidadeDebito().getSaldoAcao() + ";" +
					transacao.getEntidadeDebito().getSaldoTituloDivida() + ";" +
					(transacao.getAcao() != null ? transacao.getAcao().getIdentificador() + ";" +
							transacao.getAcao().getNome() + ";" +
							transacao.getAcao().getDataValidade() + ";" +
							transacao.getAcao().getValorUnitario() : "null") + ";" +
					(transacao.getTituloDivida() != null ? transacao.getTituloDivida().getIdentificador() + ";" +
							transacao.getTituloDivida().getNome() + ";" +
							transacao.getTituloDivida().getDataValidade() + ";" +
							transacao.getTituloDivida().getTaxaJuros() : "null") + ";" +
					transacao.getValorOperacao() + ";" +
					transacao.getDataHoraOperacao().format(dateTimeFormatter);

			writer.write(linhaGravar);
			writer.newLine();
			System.out.println("Inclusão de transação realizada com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao incluir transação.");
		}
	}
	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		int contadorTransacoes = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				// Remover todos os caracteres invisíveis
				linha = linha.replaceAll("[\\p{C}]", "").trim();

				// Ignorar linhas vazias
				if (linha.isEmpty()) {
					System.out.println("Linha vazia ignorada.");
					continue;
				}

				String[] campos = linha.split(";");

				// Remove espaços em branco ao redor de cada campo e imprime cada campo
				for (int i = 0; i < campos.length; i++) {
					campos[i] = campos[i].trim();
					System.out.println("Campo [" + i + "]: '" + campos[i] + "'");
				}

				// Verifica o número de campos na linha
				System.out.println("Linha lida: " + linha);
				System.out.println("Número de campos: " + campos.length);

				if (campos.length != 17) {
					System.out.println("Erro: Linha com número incorreto de campos: " + linha);
					continue;  // Ignora a linha se o número de campos for incorreto
				}

				int idEntidadeCredito = Integer.parseInt(campos[0]);
				if (idEntidadeCredito == identificadorEntidadeCredito) {
					contadorTransacoes++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (contadorTransacoes == 0) {
			System.out.println("Nenhuma transação encontrada para a entidade credora com o identificador: " + identificadorEntidadeCredito);
			return null;
		}

		Transacao[] transacoes = new Transacao[contadorTransacoes];
		int indice = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				// Remover todos os caracteres invisíveis
				linha = linha.replaceAll("[\\p{C}]", "").trim();

				// Ignorar linhas vazias
				if (linha.isEmpty()) {
					System.out.println("Linha vazia ignorada.");
					continue;
				}

				String[] campos = linha.split(";");

				// Remove espaços em branco ao redor de cada campo e imprime cada campo
				for (int i = 0; i < campos.length; i++) {
					campos[i] = campos[i].trim();
					System.out.println("Campo [" + i + "]: '" + campos[i] + "'");
				}

				// Verifica o número de campos novamente
				if (campos.length != 17) {
					System.out.println("Erro: Linha com número incorreto de campos: " + linha);
					continue;  // Ignora a linha se o número de campos for incorreto
				}

				int idEntidadeCredito = Integer.parseInt(campos[0]);

				if (idEntidadeCredito == identificadorEntidadeCredito) {

					// Cria a entidade de crédito
					EntidadeOperadora entidadeCredito = new EntidadeOperadora(
							idEntidadeCredito,
							campos[1],
							Boolean.parseBoolean(campos[2])
					);
					entidadeCredito.creditarSaldoAcao(Double.parseDouble(campos[3]));
					entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(campos[4]));

					// Cria a entidade de débito
					EntidadeOperadora entidadeDebito = new EntidadeOperadora(
							Long.parseLong(campos[5]),
							campos[6],
							Boolean.parseBoolean(campos[7])
					);
					entidadeDebito.creditarSaldoAcao(Double.parseDouble(campos[8]));
					entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(campos[9]));

					// Verifica se existe uma Acao, tratando o "null"
					Acao acao = null;
					if (!campos[10].equals("null")) {
						acao = new Acao(
								Integer.parseInt(campos[10]),
								campos[11],
								LocalDate.parse(campos[12]),
								Double.parseDouble(campos[13])
						);
					}

					// Verifica se existe um TituloDivida, tratando o "null"
					TituloDivida tituloDivida = null;
					if (!campos[14].equals("null")) {
						tituloDivida = new TituloDivida(
								Integer.parseInt(campos[14]),
								campos[15],
								LocalDate.parse(campos[16]),
								Double.parseDouble(campos[17])
						);
					}

					// Verifica se o índice 18 existe antes de tentar acessar o valor
					double valorOperacao = 0.0;
					if (!campos[18].equals("null")) {
						valorOperacao = Double.parseDouble(campos[18]);
					}

					LocalDateTime dataHoraOperacao = null;
					if (!campos[19].equals("null")) {
						dataHoraOperacao = LocalDateTime.parse(campos[19], dateTimeFormatter);
					}

					// Cria a transação
					Transacao transacao = new Transacao(
							entidadeCredito,
							entidadeDebito,
							acao,
							tituloDivida,
							valorOperacao,
							dataHoraOperacao
					);

					transacoes[indice] = transacao;
					indice++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transacoes;
	}

	public Transacao[] buscarPorEntidadeDevedora(int identificadorEntidadeDebito) {
		int contadorTransacoes = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				// Remover todos os caracteres invisíveis
				linha = linha.replaceAll("[\\p{C}]", "").trim();

				// Ignorar linhas vazias
				if (linha.isEmpty()) {
					System.out.println("Linha vazia ignorada.");
					continue;
				}

				String[] campos = linha.split(";");

				// Remove espaços em branco ao redor de cada campo e imprime cada campo
				for (int i = 0; i < campos.length; i++) {
					campos[i] = campos[i].trim();
					System.out.println("Campo [" + i + "]: '" + campos[i] + "'");
				}

				// Verifica o número de campos na linha
				System.out.println("Linha lida: " + linha);
				System.out.println("Número de campos: " + campos.length);

				if (campos.length != 17) {
					System.out.println("Erro: Linha com número incorreto de campos: " + linha);
					continue;  // Ignora a linha se o número de campos for incorreto
				}

				int idEntidadeDebito = Integer.parseInt(campos[5]);
				if (idEntidadeDebito == identificadorEntidadeDebito) {
					contadorTransacoes++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (contadorTransacoes == 0) {
			System.out.println("Nenhuma transação encontrada para a entidade devedora com o identificador: " + identificadorEntidadeDebito);
			return null;
		}

		Transacao[] transacoes = new Transacao[contadorTransacoes];
		int indice = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				// Remover todos os caracteres invisíveis
				linha = linha.replaceAll("[\\p{C}]", "").trim();

				// Ignorar linhas vazias
				if (linha.isEmpty()) {
					System.out.println("Linha vazia ignorada.");
					continue;
				}

				String[] campos = linha.split(";");

				// Remove espaços em branco ao redor de cada campo e imprime cada campo
				for (int i = 0; i < campos.length; i++) {
					campos[i] = campos[i].trim();
					System.out.println("Campo [" + i + "]: '" + campos[i] + "'");
				}

				// Verifica o número de campos novamente
				if (campos.length != 17) {
					System.out.println("Erro: Linha com número incorreto de campos: " + linha);
					continue;  // Ignora a linha se o número de campos for incorreto
				}

				int idEntidadeDebito = Integer.parseInt(campos[5]);

				if (idEntidadeDebito == identificadorEntidadeDebito) {

					// Cria a entidade de crédito
					EntidadeOperadora entidadeCredito = new EntidadeOperadora(
							Long.parseLong(campos[0]),
							campos[1],
							Boolean.parseBoolean(campos[2])
					);
					entidadeCredito.creditarSaldoAcao(Double.parseDouble(campos[3]));
					entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(campos[4]));

					// Cria a entidade de débito
					EntidadeOperadora entidadeDebito = new EntidadeOperadora(
							idEntidadeDebito,
							campos[6],
							Boolean.parseBoolean(campos[7])
					);
					entidadeDebito.creditarSaldoAcao(Double.parseDouble(campos[8]));
					entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(campos[9]));

					// Verifica se existe uma Acao, tratando o "null"
					Acao acao = null;
					if (!campos[10].equals("null")) {
						acao = new Acao(
								Integer.parseInt(campos[10]),
								campos[11],
								LocalDate.parse(campos[12]),
								Double.parseDouble(campos[13])
						);
					}

					// Verifica se existe um TituloDivida, tratando o "null"
					TituloDivida tituloDivida = null;
					if (!campos[14].equals("null")) {
						tituloDivida = new TituloDivida(
								Integer.parseInt(campos[14]),
								campos[15],
								LocalDate.parse(campos[16]),
								Double.parseDouble(campos[17])
						);
					}

					// Verifica se o índice 18 existe antes de tentar acessar o valor
					double valorOperacao = 0.0;
					if (!campos[18].equals("null")) {
						valorOperacao = Double.parseDouble(campos[18]);
					}

					LocalDateTime dataHoraOperacao = null;
					if (!campos[19].equals("null")) {
						dataHoraOperacao = LocalDateTime.parse(campos[19], dateTimeFormatter);
					}

					// Cria a transação
					Transacao transacao = new Transacao(
							entidadeCredito,
							entidadeDebito,
							acao,
							tituloDivida,
							valorOperacao,
							dataHoraOperacao
					);

					transacoes[indice] = transacao;
					indice++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transacoes;
	}




}
