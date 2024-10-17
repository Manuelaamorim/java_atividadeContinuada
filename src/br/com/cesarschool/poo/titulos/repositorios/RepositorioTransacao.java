package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Deve gravar em e ler de um arquivo texto chamado Transacao.txt os dados dos objetos do tipo
 * Transacao. Seguem abaixo exemplos de linhas 
 * De entidadeCredito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De entidadeDebito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De acao: identificador, nome, dataValidade, valorUnitario OU null
 * De tituloDivida: identificador, nome, dataValidade, taxaJuros OU null. 
 * valorOperacao, dataHoraOperacao
 * 
 *   002192;BCB;true;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;1;PETROBRAS;2024-12-12;30.33;null;100000.0;2024-01-01 12:22:21 
 *   002192;BCB;false;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;null;3;FRANCA;2027-11-11;2.5;100000.0;2024-01-01 12:22:21
 *
 * A inclus�o deve adicionar uma nova linha ao arquivo. 
 * 
 * A busca deve retornar um array de transa��es cuja entidadeCredito tenha identificador igual ao
 * recebido como par�metro.  
 */
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
				String[] campos = linha.split(";");
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
				String[] campos = linha.split(";");
				int idEntidadeCredito = Integer.parseInt(campos[0]);

				if (idEntidadeCredito == identificadorEntidadeCredito) {

					EntidadeOperadora entidadeCredito = new EntidadeOperadora(
							idEntidadeCredito,
							campos[1],
							Double.parseDouble(campos[2])
					);
					entidadeCredito.creditarSaldoAcao(Double.parseDouble(campos[3]));
					entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(campos[4]));

					EntidadeOperadora entidadeDebito = new EntidadeOperadora(
							Long.parseLong(campos[5]),
							campos[6],
							Double.parseDouble(campos[7])
					);
					entidadeDebito.creditarSaldoAcao(Double.parseDouble(campos[8]));
					entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(campos[9]));

					Acao acao = null;
					if (!campos[10].equals("null")) {
						acao = new Acao(
								Integer.parseInt(campos[10]),
								campos[11],
								LocalDate.parse(campos[12]),
								Double.parseDouble(campos[13])
						);
					}

					TituloDivida tituloDivida = null;
					if (!campos[14].equals("null")) {
						tituloDivida = new TituloDivida(
								Integer.parseInt(campos[14]),
								campos[15],
								LocalDate.parse(campos[16]),
								Double.parseDouble(campos[17])
						);
					}

					Transacao transacao = new Transacao(
							entidadeCredito,
							entidadeDebito,
							acao,
							tituloDivida,
							Double.parseDouble(campos[18]),
							LocalDateTime.parse(campos[19], dateTimeFormatter)
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
				String[] campos = linha.split(";");
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
				String[] campos = linha.split(";");
				int idEntidadeDebito = Integer.parseInt(campos[5]);

				if (idEntidadeDebito == identificadorEntidadeDebito) {

					EntidadeOperadora entidadeCredito = new EntidadeOperadora(
							Long.parseLong(campos[0]),
							campos[1],
							Double.parseDouble(campos[2])
					);
					entidadeCredito.creditarSaldoAcao(Double.parseDouble(campos[3]));
					entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(campos[4]));

					EntidadeOperadora entidadeDebito = new EntidadeOperadora(
							idEntidadeDebito,
							campos[6],
							Double.parseDouble(campos[7])
					);
					entidadeDebito.creditarSaldoAcao(Double.parseDouble(campos[8]));
					entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(campos[9]));

					Acao acao = null;
					if (!campos[10].equals("null")) {
						acao = new Acao(
								Integer.parseInt(campos[10]),
								campos[11],
								LocalDate.parse(campos[12]),
								Double.parseDouble(campos[13])
						);
					}

					TituloDivida tituloDivida = null;
					if (!campos[14].equals("null")) {
						tituloDivida = new TituloDivida(
								Integer.parseInt(campos[14]),
								campos[15],
								LocalDate.parse(campos[16]),
								Double.parseDouble(campos[17])
						);
					}

					Transacao transacao = new Transacao(
							entidadeCredito,
							entidadeDebito,
							acao,
							tituloDivida,
							Double.parseDouble(campos[18]),
							LocalDateTime.parse(campos[19], dateTimeFormatter)
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
